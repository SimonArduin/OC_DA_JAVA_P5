package com.openclassrooms.safetynetalerts.dto;

import java.util.List;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;

public class FireURLPerson {

	/*
	 * Collects the informations about a specific person to be returned at
	 * fireStation?stationNumber=<station_number>
	 */

	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private Integer age;
	private List<String> medications;
	private List<String> allergies;

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

	public Integer getAge() {
		return age;
	}

	public List<String> getMedications() {
		return medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public FireURLPerson(Person person, MedicalRecord medicalRecord) {
		super();
		if (person != null) {
			this.firstName = person.getFirstName();
			this.lastName = person.getLastName();
			this.address = person.getAddress();
			this.phone = person.getPhone();
		}
		if (medicalRecord != null) {
			this.age = medicalRecord.calculateAge();
			this.medications = medicalRecord.getMedications();
			this.allergies = medicalRecord.getAllergies();
		}
	}
}