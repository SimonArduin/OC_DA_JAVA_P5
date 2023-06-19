package com.openclassrooms.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;

public class FloodStationsURLHome {

	/*
	 * Collects the informations about a specific home to be returned at
	 * flood/stations?stations=<a list of station_numbers>
	 */

	private List<FloodStationsURLPerson> persons = new ArrayList<FloodStationsURLPerson>();
	private String fireStationNumber;

	public List<FloodStationsURLPerson> getPersons() {
		return persons;
	}

	public void setPersons(List<FloodStationsURLPerson> persons) {
		this.persons = persons;
	}

	public String getFireStationNumber() {
		return fireStationNumber;
	}

	public void setFireStationNumber(String station) {
		this.fireStationNumber = station;
	}

	public void addResident(Person person, MedicalRecord medicalRecord) {
		this.persons.add(new FloodStationsURLPerson(person, medicalRecord));
	}

}
