package com.openclassrooms.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;

public class FireURLInfo {

	/*
	 * Collects all the informations to be returned at fire?address=<address>
	 */

	private List<FireURLPerson> persons = new ArrayList<FireURLPerson>();
	private String fireStationNumber;

	public List<FireURLPerson> getPersons() {
		return persons;
	}

	public void setPersons(List<FireURLPerson> persons) {
		this.persons = persons;
	}

	public void addPerson(Person person, MedicalRecord medicalRecord) {
		this.persons.add(new FireURLPerson(person, medicalRecord));
	}

	public String getFireStationNumber() {
		return fireStationNumber;
	}

	public void setFireStationNumber(String fireStationNumber) {
		this.fireStationNumber = fireStationNumber;
	}
}