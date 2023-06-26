package com.openclassrooms.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.repository.FireStationRepository;

@Service
public class FireStationService {

	@Autowired
	private FireStationRepository fireStationRepository;

	public List<FireStation> getFireStation(FireStation fireStation) {
		return fireStationRepository.get(fireStation);
	}

	/*
	 * Updates the station number of the specified fire station
	 * 
	 * If several fire stations have the same address as the specified fire station,
	 * only one of them will remain, and its station number will be updated
	 * 
	 * @param - A FireStation representing containing the new information
	 * 
	 * @return - true if the fire station was correctly saved
	 */

	public FireStation putFireStation(FireStation fireStation) {
		if (fireStation == null)
			return null;
		boolean isInDB = false;
		FireStation fireStationToPut = new FireStation();
		if (fireStation.getAddress() != null) {
			List<FireStation> fireStationsInDB = fireStationRepository.get(fireStation);
			if (fireStationsInDB!=null && !fireStationsInDB.isEmpty())
				for (FireStation fireStationInDB : fireStationRepository.get(fireStation)) {
					if (fireStationInDB.equals(fireStation)) {
						isInDB = true;
						fireStationToPut = fireStationInDB;
						fireStationRepository.delete(fireStationInDB);
					}
				}
		}
		if (isInDB) {
			fireStationToPut.update(fireStation);
			return fireStationRepository.save(fireStationToPut);
		} else
			return null;
	}

	public FireStation postFireStation(FireStation fireStation) {
		return fireStationRepository.save(fireStation);
	}

	public List<FireStation> deleteFireStation(FireStation fireStation) {
		List<FireStation> fireStationsInDB = fireStationRepository.get(fireStation);
		if (fireStationsInDB == null)
			return null;
		ArrayList<FireStation> fireStations = new ArrayList<FireStation>(fireStationsInDB);
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		for (FireStation fireStationInDB : fireStations)
			if (fireStationInDB.equals(fireStationRepository.delete(fireStationInDB)))
				result.add(fireStationInDB);
		if (result.isEmpty())
			return null;
		return result;
	}
}
