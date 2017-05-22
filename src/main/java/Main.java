
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

/**
 * 
 */


/**
 * @author Sandip.Shengole
 *
 */

@SpringBootApplication(scanBasePackages="day6.cityTempJms")
@EnableJms

public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private static final String JMS_BROKER_URL = "tcp://IPAD-65110:61616";
	
	public Main() {
		LOGGER.info("Inside Default Constructor");
	}
	
	@Bean
	public ConnectionFactory connectionFactory() throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JMS_BROKER_URL);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination1 = session.createQueue("in.queue");
		LOGGER.info("Queue created1: " + destination1);
		
		Destination destination2 = session.createQueue("out.queue");
		LOGGER.info("Queue created2: " + destination2);
		
		return connectionFactory;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
