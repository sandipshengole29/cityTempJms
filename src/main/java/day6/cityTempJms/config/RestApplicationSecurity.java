/**
 * 
 */
package day6.cityTempJms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author Sandip.Shengole
 *
 */
@Configuration
@EnableWebSecurity
public class RestApplicationSecurity extends WebSecurityConfigurerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestApplicationSecurity.class);

	private static String REALM = "SecurityRealm";

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		LOGGER.info("*** inside configureGlobalSecurity @RestApplicationSecurity ***");
		auth.inMemoryAuthentication().withUser("user").password("admin").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		LOGGER.info("*** inside configure1 @RestApplicationSecurity ***");
		http.csrf().disable().authorizeRequests().antMatchers("/get_temp_ajax").hasRole("ADMIN").and().httpBasic()
				.realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint()).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);// We don't need sessions to be created.
	}

	@Bean
	public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
		LOGGER.info("*** inside getBasicAuthEntryPoint @RestApplicationSecurity ***");
		return new CustomBasicAuthenticationEntryPoint();
	}

	/* To allow Pre-flight [OPTIONS] request from browser */
	@Override
	public void configure(WebSecurity web) throws Exception {
		LOGGER.info("*** inside configure2 @RestApplicationSecurity ***");
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
}
