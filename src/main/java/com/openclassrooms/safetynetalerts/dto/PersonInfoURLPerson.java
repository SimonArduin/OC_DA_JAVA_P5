package com.openclassrooms.safetynetalerts.dto;

import java.util.List;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;

public class PersonInfoURLPerson {

	/*
	 * Collects the informations about a specific person to be returned at
	 * personInfo?firstName=<firstName>&lastName=<lastName>
	 */

	private String firstName;
	private String lastName;
	private int age;
	private String address;
	private List<String> medications;
	private List<String> allergies;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getMedications() {
		return medications;
	}

	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

	public PersonInfoURLPerson(Person person, MedicalRecord medicalRecord) {
		super();
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.age = medicalRecord.calculateAge();
		this.address = person.getAddress();
		this.medications = medicalRecord.getMedications();
		this.allergies = medicalRecord.getAllergies();
	}
}