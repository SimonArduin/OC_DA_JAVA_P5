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
	 * Deletes every fire station with the specified address
	 * 
	 * @param - A String representing an address
	 * 
	 * @return - A boolean that is false if no fire station with the specified
	 * address is found
	 */
	public boolean deleteFireStation(String address) {
		ArrayList<FireStation> fireStations = new ArrayList<FireStation>(fireStationRepository.findByAddress(address));
		if (fireStations.isEmpty())
			return false;
		else {
			for (FireStation fireStation : fireStations)
				fireStationRepository.delete(fireStation);
			return true;
		}
	}

	/*
	 * Deletes every fire station with the specified station number
	 * 
	 * @param - An int representing a station number
	 * 
	 * @return - A boolean that is false if no fire station with the specified
	 * station number is found
	 */
	public boolean deleteFireStation(int stationNumber) {
		ArrayList<FireStation> fireStations = new ArrayList<FireStation>(
				fireStationRepository.findByStationNumber(stationNumber));
		if (fireStations.isEmpty())
			return false;
		else {
			for (FireStation fireStation : fireStations)
				fireStationRepository.delete(fireStation);
			return true;
		}
	}

	/*
	 * Deletes every fire station corresponding to the specified fire station
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - A boolean that is false if the fire station is not found
	 */
	public boolean deleteFireStation(FireStation fireStation) {
		ArrayList<FireStation> fireStations = new ArrayList<FireStation>(fireStationRepository.find(fireStation));
		if (fireStations.isEmpty())
			return false;
		else {
			for (FireStation fireStationInDB : fireStations)
				fireStationRepository.delete(fireStationInDB);
			return true;
		}
	}

	/*
	 * Get every fire station corresponding to the specified address
	 * 
	 * @param - A String representing an adress
	 * 
	 * @return - An Iterable<FireStation>
	 */
	public List<FireStation> getFireStation(String address) {
		return fireStationRepository.findByAddress(address);
	}

	/*
	 * Get every fire station corresponding to the specified station number
	 * 
	 * @param - An int representing a station number
	 * 
	 * @return - An Iterable<FireStation>
	 */
	public List<FireStation> getFireStation(int stationNumber) {
		return fireStationRepository.findByStationNumber(stationNumber);
	}

	/*
	 * Get every fire station corresponding to the specified fire station
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - An Iterable<FireStation>
	 */
	public List<FireStation> getFireStation(FireStation fireStation) {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationRepository.find(fireStation));
		return result;
	}

	/*
	 * Get every fire stations
	 * 
	 * @return - An Iterable<FireStation>
	 */
	public List<FireStation> getAllFireStations() {
		return fireStationRepository.findAll();
	}

	/*
	 * Adds a fire station
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - true if the fire station was correctly saved
	 */
	public boolean postFireStation(FireStation fireStation) {
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
	public boolean putFireStation(FireStation fireStation) {
		boolean isInDB = false;
		for (FireStation fireStationInDB : fireStationRepository.findByAddress(fireStation.getAddress())) {
			if (fireStationInDB.getAddress() == fireStation.getAddress()) {
				isInDB = true;
				fireStationRepository.delete(fireStationInDB);
			}
		}
		if (isInDB)
			return fireStationRepository.save(fireStation);
		else
			return isInDB;
	}
}
