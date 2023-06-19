package com.openclassrooms.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;

public class FireURLDto {

	/*
	 * Collects all the informations to be returned at fire?address=<address>
	 */

	private List<FireURLPerson> persons = new ArrayList<FireURLPerson>();
	private String fireStationNumber;
	
	public List<FireURLPerson> getPersons() {
		return persons;
	}

	public String getFireStationNumber() {
		return fireStationNumber;
	}

	public void addPerson(Person person, MedicalRecord medicalRecord) {
		this.persons.add(new FireURLPerson(person, medicalRecord));
	}

	public void setFireStationNumber(String fireStationNumber) {
		this.fireStationNumber = fireStationNumber;
	}
}