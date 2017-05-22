/**
 * 
 */
package day6.cityTempJms.jmsClientService;

import java.util.Map;

import day6.cityTempJms.model.CityTemp;

/**
 * @author Sandip.Shengole
 *
 */
public interface JMSClientService {
	public void addCityTemp(CityTemp cityTemp);
	//public void addToOutQueue(Map<String, Object> responseMap);
}
