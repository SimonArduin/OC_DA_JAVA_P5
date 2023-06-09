package com.openclassrooms.safetynetalerts.model;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jsoniter.annotation.JsonIgnore;
import com.openclassrooms.safetynetalerts.ApplicationConfiguration;

public class MedicalRecord {

	@JsonIgnore
	private static Logger logger = LoggerFactory.getLogger(MedicalRecord.class);

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

	public MedicalRecord(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public MedicalRecord() {
		super();
	}

	public Integer calculateAge() {
		try {
			return Period
					.between(LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy")), LocalDate.now())
					.getYears();
		} catch (Exception e) {
			logger.error("exception in MedicalRecord.calculateAge()");
			return null;
		}
	}

	@JsonIgnore
	public boolean isAdult() {
		Integer age = this.calculateAge();
		if (age != null)
			return (age > ApplicationConfiguration.ageOfMajority);
		return true;
	}

	@JsonIgnore
	public boolean isEmpty() {
		if (firstName == null && lastName == null && birthdate == null && medications == null && allergies == null)
			return true;
		return false;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null) {
			if (!object.getClass().equals(MedicalRecord.class))
				return false;
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
						Field[] fields = MedicalRecord.class.getDeclaredFields();
						for (int i = 0; i < fields.length; i++)
							if (fields[i].get(this) != null
									&& !fields[i].get(this).equals(fields[i].get(((MedicalRecord) object))))
								return false;
						return true;
					}
				}
			} catch (Exception e) {
				logger.error("exception in MedicalRecord.equals()");
				e.printStackTrace();
			}
		}
		return false;
	}

	public void update(MedicalRecord medicalRecord) {
		if (medicalRecord != null) {
			if (medicalRecord.getFirstName() != null)
				this.firstName = medicalRecord.getFirstName();
			if (medicalRecord.getLastName() != null)
				this.lastName = medicalRecord.getLastName();
			if (medicalRecord.getBirthdate() != null)
				this.birthdate = medicalRecord.getBirthdate();
			if (medicalRecord.getMedications() != null)
				this.medications = medicalRecord.getMedications();
			if (medicalRecord.getAllergies() != null)
				this.allergies = medicalRecord.getAllergies();
		}
	}
}
