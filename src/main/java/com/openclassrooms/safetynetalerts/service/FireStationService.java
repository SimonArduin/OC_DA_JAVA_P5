package com.openclassrooms.safetynetalerts.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.repository.FireStationRepository;

@Service
public class FireStationService {
	
	/*@Autowired
	private FireStationRepository fireStationRepository;
	
	public Optional<FireStation> getFireStation(final Long id) {
		return fireStationRepository.findById(id);
	}
	
	public Iterable<FireStation> getFireStations() {
		return fireStationRepository.findAll();
	}
	
	public FireStation saveFireStation(FireStation fireStation) {
		return fireStationRepository.save(fireStation);
	}*/
		
}
