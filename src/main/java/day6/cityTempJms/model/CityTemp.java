/**
 * 
 */
package day6.cityTempJms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Sandip.Shengole
 *
 */

@Entity
@Table(name="CityTemp")
public class CityTemp implements Serializable {
	private static final long serialVersionUID = -1900387839007401098L;

	
	public CityTemp() {
		super();
	}
	
	public CityTemp(Long id, int temperature) {
		super();
		this.id = id;
		this.temperature = temperature;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", nullable=false, unique=true)
	private Long id;
	
	@Column(name="CITY_NAME", length=100, nullable=false)
	private String cityName;
	
	@Column(name="TEMPERATURE", length=5, nullable=false)
	private int temperature;


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the temperature
	 */
	public int getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
}
