/**
 * 
 */
package day6.cityTempJms.service;

import java.util.List;

import day6.cityTempJms.model.CityTemp;


/**
 * @author Sandip.Shengole
 *
 */
public interface CityTempService {
	public List<CityTemp> getListOfCityTemp();
	public String addCityTemp(CityTemp cityTemp);
	public List<CityTemp> getCityTempByCityName(String cityName);
}
