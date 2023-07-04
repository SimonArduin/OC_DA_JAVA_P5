package com.openclassrooms.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.repository.FireStationRepository;

@Service
public class FireStationService {

	private static Logger logger = LoggerFactory.getLogger(FireStationService.class);

	@Autowired
	private FireStationRepository fireStationRepository;

	public List<FireStation> getFireStation(FireStation fireStation) {
		logger.debug(String.format("call of getFireStation, args : %s", fireStation));
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
		logger.debug(String.format("call of putFireStation, args : %s", fireStation));
		if (fireStation == null)
			return null;
		boolean isInDB = false;
		FireStation fireStationToPut = new FireStation();
		if (fireStation.getAddress() != null) {
			List<FireStation> fireStationsInDB = fireStationRepository.get(fireStation);
			logger.debug(String.format("fireStations found for fireStation %s in fireStationURL : %s", fireStation,
					fireStationsInDB));
			if (fireStationsInDB != null && !fireStationsInDB.isEmpty())
				for (FireStation fireStationInDB : fireStationsInDB) {
					if (fireStationInDB.equals(fireStation)) {
						isInDB = true;
						fireStationToPut = fireStationInDB;
						logger.debug(String.format("fireStationToPut is %s", fireStationToPut));
						fireStationRepository.delete(fireStationInDB);
						logger.debug(String.format("deleted fireStation %s", fireStationInDB));
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
		logger.debug(String.format("call of postFireStation, args : %s", fireStation));
		return fireStationRepository.save(fireStation);
	}

	public List<FireStation> deleteFireStation(FireStation fireStation) {
		logger.debug(String.format("call of deleteFireStation, args : %s", fireStation));
		List<FireStation> fireStationsInDB = fireStationRepository.get(fireStation);
		logger.debug(String.format("fireStations found for fireStation %s in fireStationURL : %s", fireStation,
				fireStationsInDB));
		if (fireStationsInDB == null)
			return null;
		ArrayList<FireStation> fireStations = new ArrayList<FireStation>(fireStationsInDB);
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		for (FireStation fireStationInDB : fireStations)
			if (fireStationInDB.equals(fireStationRepository.delete(fireStationInDB))) {
				logger.debug(String.format("deleted fireStation %s", fireStationInDB));
				result.add(fireStationInDB);
				logger.debug(String.format("current result for deleteFireStation : %s", result));
			}
		if (result.isEmpty())
			return null;
		return result;
	}
}
