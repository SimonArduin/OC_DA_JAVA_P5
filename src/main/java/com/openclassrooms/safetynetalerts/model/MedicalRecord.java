package com.openclassrooms.safetynetalerts.model;

import java.util.ArrayList;

public class MedicalRecord {

	private String firstName;
	private String lastName;
	private String birthdate;
	private ArrayList<String> medications;
	private ArrayList<String> allergies;

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

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public ArrayList<String> getMedications() {
		return medications;
	}

	public void setMedications(ArrayList<String> medications) {
		this.medications = medications;
	}

	public ArrayList<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(ArrayList<String> allergies) {
		this.allergies = allergies;
	}

	public MedicalRecord(String firstName, String lastName, String birthdate, ArrayList<String> medications,
			ArrayList<String> allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.medications = medications;
		this.allergies = allergies;
	}

	@Override
	public boolean equals(Object o) {
		try {
			if (this.firstName.equals(((MedicalRecord) o).getFirstName())
					&& this.lastName.equals(((MedicalRecord) o).getLastName())
					&& this.birthdate.equals(((MedicalRecord) o).getBirthdate())
					&& this.medications.equals(((MedicalRecord) o).getMedications())
					&& this.allergies.equals(((MedicalRecord) o).getAllergies()))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
