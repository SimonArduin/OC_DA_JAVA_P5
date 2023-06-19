package com.openclassrooms.safetynetalerts.dto;

import com.openclassrooms.safetynetalerts.model.Person;

public class FireStationURLPerson {

	/*
	 * Collects the informations about a specific person to be returned at
	 * fireStation?stationNumber=<station_number>
	 */

	private String firstName;
	private String lastName;
	private String address;
	private String phone;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public FireStationURLPerson(Person person) {
		super();
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.address = person.getAddress();
		this.phone = person.getPhone();
	}
}