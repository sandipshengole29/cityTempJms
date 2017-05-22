/**
 * 
 */
package day6.cityTempJms.config;


import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Sandip.Shengole
 *
 */

@Configuration
@EnableJpaRepositories(basePackages={"day6.cityTempJms.repository"}, entityManagerFactoryRef="localContainerEntityManagerFactoryBean")
@PropertySource(value="classpath:/dataBase.properties")
@EnableTransactionManagement()

public class DatabaseConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);
	
	@Value("${db.driver}")
	private String DATABASE_DRIVER;

	@Value("${db.password}")
	private String DATABASE_PASSWORD;

	@Value("${db.url}")
	private String DATABASE_URL;

	@Value("${db.userName}")
	private String DATABASE_USERNAME;

	@Value("${hibernate.dialect}")
	private String HIBERNATE_DIALECT;

	@Value("${hibernate.show_sql}")
	private String HIBERNATE_SHOW_SQL;

	@Value("${hibernate.hbm2ddl.auto}")
	private String HIBERNATE_HBM2DDL_AUTO;

	@Value("${hibernate.format_sql}")
	private String HIBERNATE_FORMAT_SQL;
	
	@Value("${hibernate.implicit_naming_strategy}")
	private String HIBERNATE_IMPLICIT_NAMING_STRATEGY;
	
	@Value("${hibernate.packagesToScan}")
	private String HIBERNATE_PACKAGES_TO_SCAN;
	
	@Bean
	public DataSource dataSource(Environment environment){
		LOGGER.info("** Inside DatabaseConfig @dataSource **");
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		try{
			driverManagerDataSource.setDriverClassName(DATABASE_DRIVER);
			driverManagerDataSource.setUrl(DATABASE_URL);
			driverManagerDataSource.setUsername(DATABASE_USERNAME);
			driverManagerDataSource.setPassword(DATABASE_PASSWORD);
		}catch(Exception ex){
			LOGGER.error("Exception occured in dataSource @DatabaseConfig: ", ex);
		}
		return driverManagerDataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource dataSource){
		LOGGER.info("** Inside DatabaseConfig @localContainerEntityManagerFactoryBean **");
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		try {
			/* setting data source */
			entityManagerFactoryBean.setDataSource(dataSource);
			
			/* setting Hibernate JPA vendor Adapter */
			entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
			
			/* packages containing @Entity annotation */
			entityManagerFactoryBean.setPackagesToScan(HIBERNATE_PACKAGES_TO_SCAN);
			
			/* properties related to Hibernate JPA vendor */
			Properties jpaProperties = new Properties();
			jpaProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
			
			/* comment this property if you are using the liquibase for database chagnes */
			jpaProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
			
			jpaProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
			jpaProperties.put("hibernate.format_sql", HIBERNATE_FORMAT_SQL);
			jpaProperties.put("hibernate.implicit_naming_strategy", HIBERNATE_IMPLICIT_NAMING_STRATEGY);
			
			/* setting all hibernate jpa properties to entityManageFactoryBean */
			entityManagerFactoryBean.setJpaProperties(jpaProperties);
			LOGGER.info("** In entityManagerFactoryBean **" + entityManagerFactoryBean);
			
		} catch (Exception e) {
			LOGGER.error("Exception occured in localContainerEntityManagerFactoryBean @DatabaseConfig: ", e);
		}
		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactoryBean){
		LOGGER.info("* In Transaction Manager *");
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		try {
			transactionManager.setEntityManagerFactory(entityManagerFactoryBean);
		} catch (Exception e) {
			LOGGER.error("Exception occured in transactionManager @DatabaseConfig: ", e);
		}
		LOGGER.info("* After Transaction Manager *");
		return transactionManager;
	}
}
