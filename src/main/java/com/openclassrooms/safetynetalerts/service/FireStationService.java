package com.openclassrooms.safetynetalerts.service;

import java.util.ArrayList;

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
		ArrayList<FireStation> fireStations = fireStationRepository.findByAddress(address);
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
		ArrayList<FireStation> fireStations = fireStationRepository.findByStationNumber(stationNumber);
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
		ArrayList<FireStation> fireStations = fireStationRepository.findByAddress(fireStation.getAddress());
		if (fireStations.isEmpty())
			return false;
		else {
			for (FireStation fireStationByAddress : fireStations) {
				if (fireStationByAddress.equals(fireStation))
					fireStationRepository.delete(fireStation);
			}
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
	public Iterable<FireStation> getFireStation(String address) {
		return fireStationRepository.findByAddress(address);
	}

	/*
	 * Get every fire station corresponding to the specified station number
	 * 
	 * @param - An int representing a station number
	 * 
	 * @return - An Iterable<FireStation>
	 */
	public Iterable<FireStation> getFireStation(int stationNumber) {
		return fireStationRepository.findByStationNumber(stationNumber);
	}

	/*
	 * Get every fire station corresponding to the specified fire station
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - An Iterable<FireStation>
	 */
	public Iterable<FireStation> getFireStation(FireStation fireStation) {
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		result = fireStationRepository.findByAddress(fireStation.getAddress());
		for (FireStation fireStationByAddress : result) {
			if (fireStationByAddress.getStationNumber() != fireStation.getStationNumber())
				result.remove(fireStationByAddress);
		}
		return result;
	}

	/*
	 * Get every fire stations
	 * 
	 * @return - An Iterable<FireStation>
	 */
	public Iterable<FireStation> getAllFireStations() {
		return fireStationRepository.findAll();
	}

	/*
	 * Adds a fire station
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - A FireStation
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
	 * @return - A FireStation
	 */
	public FireStation putFireStation(FireStation fireStation) {
		for (FireStation fireStationInDB : fireStationRepository.findByAddress(fireStation.getAddress()))
			fireStationRepository.delete(fireStationInDB);
		return fireStationRepository.save(fireStation);
	}
}
