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
		if (fireStation == null)
			return new ArrayList<FireStation>();
		if (fireStation.isEmpty())
			return new ArrayList<FireStation>();
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		for (FireStation fireStationInDB : instance.firestations) {
			if (fireStation.equals(fireStationInDB))
				result.add(fireStationInDB);
		}
		if (result.isEmpty())
			return new ArrayList<FireStation>();
		return result;
	}

	public FireStation addFireStation(FireStation fireStation) {
		if (fireStation == null)
			return new FireStation();
		if (fireStation.isEmpty())
			return new FireStation();
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

	public List<Person> getPersons() {
		return instance.persons;
	}

	public List<Person> getPersons(Person person) {
		if (person == null)
			return new ArrayList<Person>();
		if (person.isEmpty())
			return new ArrayList<Person>();
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person personInDB : instance.persons) {
			if (person.equals(personInDB))
				result.add(personInDB);
		}
		if (result.isEmpty())
			return new ArrayList<Person>();
		return result;
	}

	public Person addPerson(Person person) {
		if (person == null)
			return new Person();
		if (person.isEmpty())
			return new Person();
		if (!instance.persons.contains(person))
			if (instance.persons.add(person))
				return person;
		return new Person();
	}

	public Person removePerson(Person person) {
		if (person == null)
			return new Person();
		if (person.isEmpty())
			return new Person();
		for (Person personInDB : instance.persons)
			if (person.equals(personInDB))
				if (instance.persons.remove(personInDB))
					return personInDB;
		return new Person();
	}

	public List<MedicalRecord> getMedicalRecords() {
		return instance.medicalrecords;
	}

	public MedicalRecord getMedicalRecords(MedicalRecord medicalRecord) {
		if (medicalRecord == null)
			return new MedicalRecord();
		if (medicalRecord.isEmpty())
			return new MedicalRecord();
		for (MedicalRecord medicalRecordInDB : instance.medicalrecords) {
			if (medicalRecord.equals(medicalRecordInDB))
				return medicalRecordInDB;
		}
		return new MedicalRecord();
	}

	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		if (medicalRecord == null)
			return new MedicalRecord();
		if (medicalRecord.isEmpty())
			return new MedicalRecord();
		if (!instance.medicalrecords.contains(medicalRecord))
			if (instance.medicalrecords.add(medicalRecord))
				return medicalRecord;
		return new MedicalRecord();
	}

	public MedicalRecord removeMedicalRecord(MedicalRecord medicalRecord) {
		if (medicalRecord == null)
			return new MedicalRecord();
		if (medicalRecord.isEmpty())
			return new MedicalRecord();
		for (MedicalRecord medicalRecordInDB : instance.medicalrecords)
			if (medicalRecord.equals(medicalRecordInDB))
				if (instance.medicalrecords.remove(medicalRecordInDB))
					return medicalRecordInDB;
		return new MedicalRecord();
	}
}
