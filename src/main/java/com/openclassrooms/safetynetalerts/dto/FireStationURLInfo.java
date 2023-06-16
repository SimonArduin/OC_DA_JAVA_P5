package com.openclassrooms.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;

public class FireStationURLInfo {

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

	public void setPersons(List<FireStationURLPerson> persons) {
		this.persons = persons;
	}

	public void addPerson(FireStationURLPerson person) {
		this.persons.add(person);
	}

	public Integer getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(Integer numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public void addAdult() {
		this.numberOfAdults++;
	}

	public Integer getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(Integer numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public void addChild() {
		this.numberOfChildren++;
	}
}