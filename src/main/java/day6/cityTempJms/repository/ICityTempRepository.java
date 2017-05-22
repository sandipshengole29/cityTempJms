/**
 * 
 */
package day6.cityTempJms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import day6.cityTempJms.model.CityTemp;

/**
 * @author Sandip.Shengole
 *
 */
@Repository
public interface ICityTempRepository extends JpaRepository<CityTemp, Long> {
		
	@Query(value="SELECT c FROM CityTemp AS c ORDER BY c.id DESC")
	public List<CityTemp> getListOfEmployees();
	
	@Query(value="SELECT c FROM CityTemp AS c WHERE c.cityName like CONCAT(:cityName)")
	public List<CityTemp> getCityTempByCityName(@Param("cityName") String cityName);
}
