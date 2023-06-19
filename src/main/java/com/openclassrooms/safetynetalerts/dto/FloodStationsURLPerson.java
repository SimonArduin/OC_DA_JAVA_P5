package com.openclassrooms.safetynetalerts.dto;

import java.util.List;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;

public class FloodStationsURLPerson {

	/*
	 * Collects the informations about a specific person to be returned at
	 * flood/stations?stations=<a list of station_numbers>
	 */

	private String firstName;
	private String lastName;
	private String phone;
	private int age;
	private List<String> medications;
	private List<String> allergies;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public int getAge() {
		return age;
	}

	public List<String> getMedications() {
		return medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public FloodStationsURLPerson(Person person, MedicalRecord medicalRecord) {
		super();
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.phone = person.getPhone();
		this.age = medicalRecord.calculateAge();
		this.medications = medicalRecord.getMedications();
		this.allergies = medicalRecord.getAllergies();
	}
}