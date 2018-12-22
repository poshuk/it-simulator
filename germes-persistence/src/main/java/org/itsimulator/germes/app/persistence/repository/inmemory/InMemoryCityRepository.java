package org.itsimulator.germes.app.persistence.repository.inmemory;

import org.itsimulator.germes.app.infra.util.CommonUtil;
import org.itsimulator.germes.app.model.entity.geography.City;
import org.itsimulator.germes.app.persistence.repository.CityRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCityRepository implements CityRepository {
    private final List<City> cities;

    private int counter = 0;
    private int stationCounter = 0;

    public InMemoryCityRepository(){
        cities = new ArrayList<>();
    }

    @Override
    public void delete(final int cityId){

    }

    @Override
    public List<City> findAll(){
        return CommonUtil.getSafeList(cities);
    }

    @Override
    public void save(final City city){
        if (!cities.contains(city)){
            city.setId(++counter);
            cities.add(city);
        }
        city.getStations().forEach((station) -> {
            if (station.getId() == 0){
                station.setId(++stationCounter);
            }
        });
    }

    @Override
    public City findById(final int id){
        return cities.stream().filter(city -> city.getId() == id).findFirst().orElse(null);
    }
}
