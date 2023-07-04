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
	private Integer age;
	private String address;
	private List<String> medications;
	private List<String> allergies;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Integer getAge() {
		return age;
	}

	public String getAddress() {
		return address;
	}

	public List<String> getMedications() {
		return medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public PersonInfoURLPerson(Person person, MedicalRecord medicalRecord) {
		super();
		if (person != null) {
			this.firstName = person.getFirstName();
			this.lastName = person.getLastName();
			this.address = person.getAddress();
		}
		if (medicalRecord != null) {
			this.age = medicalRecord.calculateAge();
			this.medications = medicalRecord.getMedications();
			this.allergies = medicalRecord.getAllergies();
		}
	}
}