package org.itsimulator.germes.app.service;

import java.util.List;
import java.util.Optional;

import org.itsimulator.germes.app.model.entity.geography.City;
import org.itsimulator.germes.app.model.entity.geography.Station;
import org.itsimulator.germes.app.model.search.criteria.StationCriteria;
import org.itsimulator.germes.app.model.search.criteria.range.RangeCriteria;

/**
 * Entry point to perform operations
 * over geographic entities
 * @author Morenets
 *
 */
public interface GeographicService {

	/**
	 * Returns list of existing cities
	 * @return
	 */
	List<City> findCities();
	
	/**
	 * Saves specified city instance
	 * @param city
	 */
	void saveCity(City city);

	Optional<City> findCityById(int id);
	List<Station> searchStations(StationCriteria criteria, RangeCriteria rangeCriteria);


	

}
