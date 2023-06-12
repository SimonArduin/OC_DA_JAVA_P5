package com.openclassrooms.safetynetalerts.model;

import java.lang.reflect.Field;
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

	public MedicalRecord() {
		super();
	}

	public boolean isEmpty() {
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].get(this) != null)
					return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean equals(Object object) {
		if (!object.getClass().equals(MedicalRecord.class))
			return false;
		if (((MedicalRecord) object) != null) {
			try {
				if (this.isEmpty()) {
					if (((MedicalRecord) object).isEmpty())
						return true;
					else
						return false;
				} else {
					if (this.firstName != null && this.lastName != null) {
						if (this.firstName.equals(((MedicalRecord) object).getFirstName())
								&& this.lastName.equals(((MedicalRecord) object).getLastName()))
							return true;
						return false;
					} else {
						Field[] fields = this.getClass().getDeclaredFields();
						for (int i = 0; i < fields.length; i++)
							if (fields[i].get(this) != null
									&& !fields[i].get(this).equals(fields[i].get(((MedicalRecord) object))))
								return false;
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
