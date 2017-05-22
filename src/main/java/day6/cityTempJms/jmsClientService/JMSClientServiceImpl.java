/**
 * 
 */
package day6.cityTempJms.jmsClientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import day6.cityTempJms.controller.CityTempRestController;
import day6.cityTempJms.model.CityTemp;

/**
 * @author Sandip.Shengole
 *
 */
@Component
public class JMSClientServiceImpl implements JMSClientService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityTempRestController.class);
	private static final String IN_QUEUE = "in.queue";

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Override
	public void addCityTemp(CityTemp cityTemp) {
		try {
			jmsTemplate.convertAndSend(IN_QUEUE, cityTemp);
		} catch (Exception e) {
			LOGGER.error("Exception occured in addCityTemp @JMSClientServiceImpl: ", e);
		}
	}
}
