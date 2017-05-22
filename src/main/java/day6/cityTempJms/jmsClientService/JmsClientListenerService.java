/**
 * 
 */
package day6.cityTempJms.jmsClientService;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import day6.cityTempJms.controller.CityTempRestController;
import day6.cityTempJms.model.CityTemp;
import day6.cityTempJms.service.CityTempService;

/**
 * @author Sandip.Shengole
 *
 */
@Component
public class JmsClientListenerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityTempRestController.class);
	
	@Autowired
	private CityTempService cityTempService;
	
	@JmsListener(destination="in.queue")
	@SendTo("out.queue")
	public CityTemp receiveCityTempMsgAnInQueue(CityTemp cityTemp){
		CityTemp returnCityTemp = null;
		String addCityTempResponse = null;
		try {
			addCityTempResponse = cityTempService.addCityTemp(cityTemp);
			CityTemp currentCityTemp = cityTempService.getCityTempByCityName(cityTemp.getCityName()).get(0);
			if(StringUtils.isNotEmpty(addCityTempResponse) && addCityTempResponse.equalsIgnoreCase("SUCCESS")){
				if(currentCityTemp.getCityName().equalsIgnoreCase(cityTemp.getCityName()) && currentCityTemp.getTemperature() == cityTemp.getTemperature()){
					returnCityTemp = currentCityTemp;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in receiveCityTempMsg @JmsClientListenerService: ", e);
		}
		LOGGER.info("addCityTempResponse: " + addCityTempResponse);
		return returnCityTemp;
	}
	
	@JmsListener(destination="out.queue")
	public void receiveCityTempMsgAnOutQueue(CityTemp cityTemp){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(null != cityTemp){
				map.put("SUCCESS", cityTemp);
			}else{
				map.put("FAILURE", cityTemp);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in receiveCityTempMsg @JmsClientListenerService: ", e);
		}
	}

}
