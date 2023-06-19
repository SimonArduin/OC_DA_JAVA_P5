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

	/*
	 * Deletes every fire station corresponding to the specified fire station
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - An ArrayList<FireStation> of all fire stations deleted
	 */
	public List<FireStation> deleteFireStation(FireStation fireStation) {
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		ArrayList<FireStation> fireStations = new ArrayList<FireStation>(fireStationRepository.get(fireStation));
		if (fireStations.isEmpty())
			return result;
		else {
			for (FireStation fireStationInDB : fireStations)
				if (fireStationInDB.equals(fireStationRepository.delete(fireStationInDB)))
					result.add(fireStationInDB);
			return result;
		}
	}

	/*
	 * Get every fire station corresponding to the specified fire station
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - An Iterable<FireStation>
	 */
	public List<FireStation> getFireStation(FireStation fireStation) {
		return fireStationRepository.get(fireStation);
	}

	/*
	 * Adds a fire station
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - true if the fire station was correctly saved
	 */
	public FireStation postFireStation(FireStation fireStation) {
		return fireStationRepository.save(fireStation);
	}

	/*
	 * Updates the station number of the specified fire station
	 * 
	 * If several fire stations have the same address as the specified fire station,
	 * only one of them will remain, and its station number will be updated
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - true if the fire station was correctly saved
	 */

	public FireStation putFireStation(FireStation fireStation) {
		if (fireStation == null)
			return new FireStation();
		boolean isInDB = false;
		FireStation fireStationToPut = new FireStation();
		if (fireStation.getAddress() != null)
			for (FireStation fireStationInDB : fireStationRepository.get(fireStation)) {
				if (fireStationInDB.equals(fireStation)) {
					isInDB = true;
					fireStationToPut = fireStationInDB;
					fireStationRepository.delete(fireStationInDB);
				}
			}
		if (isInDB) {
			fireStationToPut.update(fireStation);
			return fireStationRepository.save(fireStationToPut);
		} else
			return new FireStation();
	}
}
