package com.openclassrooms.safetynetalerts.repository;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.model.FireStation;

@Repository
public class FireStationRepository {

	private static ArrayList<FireStation> fireStations = new ArrayList<FireStation>();
	
	public FireStationRepository() {
		try {
			this.resetDataBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetDataBase() throws Exception {
		fireStations = new ArrayList<FireStation>();
		Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/data.json"));
		ObjectMapper objectMapper = new ObjectMapper();
		
		/*
		 * get all data in data.json
		 */
		Map<String, List<Object>> data = objectMapper.readValue(reader,
				   new TypeReference<Map<String,  List<Object>>>() { } );
		/*
		 * extract all fire station data
		 */
		ArrayList<Object> fireStationData = new ArrayList<Object>(data.get("firestations"));
		/*
		 * add all fire stations to the list of fire stations
		 */
		for(Object o : fireStationData) {
			FireStation fireStation = objectMapper.convertValue(o, FireStation.class);
			fireStations.add(fireStation);
		}
	}

	public FireStation delete(FireStation fireStation) {
		FireStation result = new FireStation();
		boolean isInDB = false;
		int i = 0;
		while (i < fireStations.size() && !isInDB) {
			FireStation fireStationInDB = fireStations.get(i);
			if (fireStationInDB.equals(fireStation)) {
				isInDB = true;
				fireStations.remove(fireStationInDB);
				result = fireStationInDB;
				break;
			}
			i++;
		}
		return result;
	}

	public List<FireStation> findAll() {
		return fireStations;
	}

	public List<FireStation> find(FireStation fireStation) {
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		for (FireStation fireStationInDB : fireStations) {
			if (fireStation.equals(fireStationInDB))
				result.add(fireStationInDB);
		}
		return result;
	}

	public List<FireStation> findByAddress(String address) {
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		for (FireStation fireStation : fireStations) {
			if (address.equals(fireStation.getAddress()))
				result.add(fireStation);
		}
		return result;
	}

	public List<FireStation> findByStation(int station) {
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		for (FireStation fireStation : fireStations) {
			if (station == fireStation.getStation())
				result.add(fireStation);
		}
		return result;
	}

	public FireStation save(FireStation fireStation) {
		boolean isInDB = false;
		FireStation result = new FireStation();
		int i = 0;
		while (i < fireStations.size() && !isInDB) {
			FireStation fireStationInDB = fireStations.get(i);
			if (fireStationInDB.equals(fireStation)) {
				isInDB = true;
				break;
			}
			i++;
		}
		if (!isInDB) {
			fireStations.add(fireStation);
			result = fireStation;
		}
		return result;
	}

}
