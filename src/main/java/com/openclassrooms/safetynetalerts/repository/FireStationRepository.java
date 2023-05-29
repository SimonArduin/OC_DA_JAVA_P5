package com.openclassrooms.safetynetalerts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.model.FireStation;

@Repository
public interface FireStationRepository extends CrudRepository<FireStation, Long> {

}
