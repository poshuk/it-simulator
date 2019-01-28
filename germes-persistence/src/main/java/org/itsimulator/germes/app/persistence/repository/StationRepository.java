package org.itsimulator.germes.app.persistence.repository;

import org.itsimulator.germes.app.model.entity.geography.Station;
import org.itsimulator.germes.app.model.search.criteria.StationCriteria;

import java.util.List;

public interface StationRepository {
    List<Station> findAllByCriteria(StationCriteria stationCriteria);
}
