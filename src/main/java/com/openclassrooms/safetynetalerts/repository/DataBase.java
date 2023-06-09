package com.openclassrooms.safetynetalerts.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.jsoniter.JsonIterator;
import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;

public class DataBase {

	private static DataBase instance;

	public List<FireStation> firestations;
	public List<Person> persons;
	public List<MedicalRecord> medicalrecords;

	private DataBase() {
		try {
			Path path = Paths.get("src/main/resources/data.json");

			List<String> lines = Files.readAllLines(path);
			String fullFile = "";
			for (String current : lines)
				fullFile += current;

			FullJson fullData = JsonIterator.deserialize(fullFile, FullJson.class);
			this.firestations = fullData.firestations;
			this.persons = fullData.persons;
			this.medicalrecords = fullData.medicalrecords;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DataBase getDataBase() {
		if (instance == null)
			instance = new DataBase();
		return instance;
	}

	public List<FireStation> getFireStations() {
		return instance.firestations;
	}

	public List<FireStation> getFireStations(FireStation fireStation) {
		ArrayList<FireStation> result = new ArrayList<FireStation>(instance.firestations);
		for (FireStation fireStationInDB : instance.firestations) {
			if ((!fireStation.getAddress().isBlank() && !fireStation.getAddress().equals(fireStationInDB.getAddress()))
					|| (!fireStation.getStation().isBlank()
							&& !fireStation.getStation().equals(fireStationInDB.getStation())))
				result.remove(fireStationInDB);
		}
		return result;
	}

	public FireStation addFireStation(FireStation fireStation) {
		if (!instance.firestations.contains(fireStation))
			if (instance.firestations.add(fireStation))
				return fireStation;
		return new FireStation();
	}

	public FireStation removeFireStation(FireStation fireStation) {
		if (instance.firestations.remove(fireStation))
			return fireStation;
		return new FireStation();
	}
}
