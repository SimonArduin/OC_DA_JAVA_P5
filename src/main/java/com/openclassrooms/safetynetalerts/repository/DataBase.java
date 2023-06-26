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

	private List<FireStation> firestations;
	private List<Person> persons;
	private List<MedicalRecord> medicalrecords;

	private DataBase() {
		try {
			Path path = Paths.get("src/main/resources/data.json");

			List<String> lines = Files.readAllLines(path);
			String fullFile = "";
			for (String current : lines)
				fullFile += current;

			DataBaseStructure fullData = JsonIterator.deserialize(fullFile, DataBaseStructure.class);
			this.firestations = fullData.firestations;
			this.persons = fullData.persons;
			this.medicalrecords = fullData.medicalrecords;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DataBase(ArrayList<FireStation> fireStations, ArrayList<Person> persons,
			ArrayList<MedicalRecord> medicalRecords) {
		this.firestations = fireStations;
		this.persons = persons;
		this.medicalrecords = medicalRecords;
	}

	public static DataBase getDataBase() {
		if (instance == null)
			instance = new DataBase();
		return instance;
	}

	public static DataBase setDataBase(ArrayList<FireStation> fireStations, ArrayList<Person> persons,
			ArrayList<MedicalRecord> medicalRecords) {
		return instance = new DataBase(fireStations, persons, medicalRecords);
	}

	public List<FireStation> getFireStations() {
		return instance.firestations;
	}

	public List<FireStation> getFireStations(FireStation fireStation) {
		if (fireStation == null || fireStation.isEmpty())
			return null;
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		List<FireStation> fireStationsInDB = instance.firestations;
		if (fireStationsInDB != null && !fireStationsInDB.isEmpty())
		for (FireStation fireStationInDB : fireStationsInDB) {
			if (fireStation.equals(fireStationInDB))
				result.add(fireStationInDB);
		}
		if (result.isEmpty())
			return null;
		return result;
	}

	public FireStation addFireStation(FireStation fireStation) {
		if (fireStation == null || fireStation.isEmpty())
			return null;
		if (!instance.firestations.contains(fireStation))
			if (instance.firestations.add(fireStation))
				return fireStation;
		return null;
	}

	public FireStation removeFireStation(FireStation fireStation) {
		if (instance.firestations.remove(fireStation))
			return fireStation;
		return null;
	}

	public List<Person> getPersons() {
		return instance.persons;
	}

	public List<Person> getPersons(Person person) {
		if (person == null || person.isEmpty())
			return null;
		ArrayList<Person> result = new ArrayList<Person>();
		List<Person> personsInDB = instance.persons;
		if (personsInDB != null && !personsInDB.isEmpty())
		for (Person personInDB : personsInDB) {
			if (person.equals(personInDB))
				result.add(personInDB);
		}
		if (result.isEmpty())
			return null;
		return result;
	}

	public Person addPerson(Person person) {
		if (person == null || person.isEmpty())
			return null;
		if (!instance.persons.contains(person))
			if (instance.persons.add(person))
				return person;
		return null;
	}

	public Person removePerson(Person person) {
		if (person == null || person.isEmpty())
			return null;
		for (Person personInDB : instance.persons)
			if (person.equals(personInDB))
				if (instance.persons.remove(personInDB))
					return personInDB;
		return null;
	}

	public List<MedicalRecord> getMedicalRecords() {
		return instance.medicalrecords;
	}

	public List<MedicalRecord> getMedicalRecords(MedicalRecord medicalRecord) {
		if (medicalRecord == null || medicalRecord.isEmpty())
			return null;
		ArrayList<MedicalRecord> result = new ArrayList<MedicalRecord>();
		List<MedicalRecord> medicalRecordsInDB = instance.medicalrecords;
		if (medicalRecordsInDB != null && !medicalRecordsInDB.isEmpty())
		for (MedicalRecord medicalRecordInDB : medicalRecordsInDB) {
			if (medicalRecord.equals(medicalRecordInDB))
				result.add(medicalRecordInDB);
		}
		if (result.isEmpty())
			return null;
		return result;
	}

	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		if (medicalRecord == null || medicalRecord.isEmpty())
			return null;
		if (!instance.medicalrecords.contains(medicalRecord))
			if (instance.medicalrecords.add(medicalRecord))
				return medicalRecord;
		return null;
	}

	public MedicalRecord removeMedicalRecord(MedicalRecord medicalRecord) {
		if (medicalRecord == null || medicalRecord.isEmpty())
			return null;
		for (MedicalRecord medicalRecordInDB : instance.medicalrecords)
			if (medicalRecord.equals(medicalRecordInDB))
				if (instance.medicalrecords.remove(medicalRecordInDB))
					return medicalRecordInDB;
		return null;
	}
}
