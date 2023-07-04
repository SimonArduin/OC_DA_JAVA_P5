package com.openclassrooms.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;

public class FireStationURLDto {

	/*
	 * Collects all the informations to be returned at
	 * fireStation?stationNumber=<station_number>
	 */

	private List<FireStationURLPerson> persons = new ArrayList<FireStationURLPerson>();
	private Integer numberOfAdults = 0;
	private Integer numberOfChildren = 0;

	public List<FireStationURLPerson> getPersons() {
		return persons;
	}

	public Integer getNumberOfAdults() {
		return numberOfAdults;
	}

	public Integer getNumberOfChildren() {
		return numberOfChildren;
	}

	public void addPerson(FireStationURLPerson person) {
		this.persons.add(person);
	}

	public void addAdult() {
		this.numberOfAdults++;
	}

	public void addChild() {
		this.numberOfChildren++;
	}
}