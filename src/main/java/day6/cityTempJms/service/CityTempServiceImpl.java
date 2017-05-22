/**
 * 
 */
package day6.cityTempJms.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import day6.cityTempJms.model.CityTemp;
import day6.cityTempJms.repository.ICityTempRepository;


/**
 * @author Sandip.Shengole
 *
 */

@Service
public class CityTempServiceImpl implements CityTempService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityTempServiceImpl.class);
	
	@Autowired
	private ICityTempRepository iCityTempRepository;

	@Override
	public List<CityTemp> getListOfCityTemp() {
		List<CityTemp> cityTemps = null;
		try {
			cityTemps = iCityTempRepository.getListOfEmployees();
		} catch (Exception e) {
			LOGGER.error("Exception occured in getListOfCityTemp @ Service: ", e);
		}
		return cityTemps;
	}

	@Override
	public String addCityTemp(CityTemp cityTemp) {
		CityTemp temp = null;
		String addResponse = "FAILURE";
		try {
			if(null != cityTemp){
				temp = iCityTempRepository.save(cityTemp);
			}
			if(null != temp){
				addResponse = "SUCCESS";
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in addCityTemp @ Service: ", e);
		}
		return addResponse;
	}

	@Override
	public List<CityTemp> getCityTempByCityName(String cityName) {
		List<CityTemp> cityTemps = null;
		try {
			if(StringUtils.isNotEmpty(cityName)){
				String name="%"+cityName+"%";
				cityTemps = iCityTempRepository.getCityTempByCityName(name);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in getListOfCityTemp @ Service: ", e);
		}
		return cityTemps;
	}

	
}
