package day6.cityTempJms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import day6.cityTempJms.jmsClientService.JMSClientService;
import day6.cityTempJms.model.CityTemp;
import day6.cityTempJms.service.CityTempService;


@RestController
//@RequestMapping(value="/rest")
public class CityTempRestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityTempRestController.class);
	
	@Autowired
	private CityTempService cityTempService;
	
	@Autowired
	private JMSClientService jMSClientService;
	
	@PostMapping(value="/welCome", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String welComeCall(@RequestBody CityTemp cityTemp){
		String returnString = null;
		try {
			returnString = "WelCome to CityTemp: " + cityTemp.getCityName() +" ::: "+ cityTemp.getTemperature();
		} catch (Exception e) {
			LOGGER.error("Exception occured in welComeCall: ", e);
		}
		return returnString;
	}
	
	@GetMapping(value="/get_temp_ajax", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getCityTempList(@RequestParam(name="name_prefix") String name_prefix){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<CityTemp> cityTemps = null;
		try {
			if(StringUtils.isNotEmpty(name_prefix) && name_prefix.length() >= 3){
				cityTemps = cityTempService.getCityTempByCityName(name_prefix);
				responseMap.put("status", "success");
				responseMap.put("data", cityTemps);
			}else{
				responseMap.put("status", "error");
				responseMap.put("errorMessage", "Too few characters");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in getCityTempList: ", e);
		}
		return responseMap;
	}
	
	
	@PostMapping(value="/postCityTempData", produces=MediaType.APPLICATION_JSON_VALUE)
	public String saveCityTempData(@RequestBody CityTemp cityTemp){
		String returnResponse = null;
		try {
			if(null != cityTemp){
				jMSClientService.addCityTemp(cityTemp);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in saveCityTempData: ", e);
		}
		return returnResponse;
	}
}
