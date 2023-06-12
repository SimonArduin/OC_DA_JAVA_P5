package com.openclassrooms.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;

@SpringBootTest(classes = MedicalRecord.class)
public class MedicalRecordTest {
	
	final ArrayList<String> medications = new ArrayList<String>(Arrays.asList("medication 1","medication 2"));
	final ArrayList<String> allergies = new ArrayList<String>(Arrays.asList("allergy 1","allergy 2"));
	final ArrayList<String> otherMedications = new ArrayList<String>(Arrays.asList("otherMedication 1","otherMedication 2"));
	final ArrayList<String> otherAllergies = new ArrayList<String>(Arrays.asList("otherAllergy 1","otherAllergy 2"));
	final MedicalRecord person = new MedicalRecord("firstName", "lastName", "birthdate", medications, allergies);
	final MedicalRecord personOther = new MedicalRecord("otherFirstName", "otherLastName", "otherBirthdate", otherMedications, otherAllergies);
	MedicalRecord personTest;
	
	@BeforeEach
	void setUpPerTest() {
		personTest = new MedicalRecord();
	}
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void isEmpty() {
		assertFalse(person.isEmpty());
	}

	@Test
	void isEmptyIfEmpty() {
		assertTrue(personTest.isEmpty());
	}
	
	@Test
	void isEmptyIfOnlyBirthdate() {
		personTest.setBirthdate(person.getBirthdate());
		assertFalse(personTest.isEmpty());
	}
	
	@Test
	void equalsTest() {
		assertTrue(person.equals(person));
	}
	
	@Test
	void equalsTestIfOtherMedicalRecord() {
		assertFalse(person.equals(personOther));
	}

	@Test
	void equalsTestIfOnlyName() {
		personTest.setFirstName(person.getFirstName());
		personTest.setLastName(person.getLastName());
		assertTrue(personTest.equals(person));
	}

	@Test
	void equalsTestIfOnlyNameAndOtherMedicalRecord() {
		personTest.setFirstName(person.getFirstName());
		personTest.setLastName(person.getLastName());
		assertFalse(personTest.equals(personOther));
	}

	@Test
	void equalsTestIfFirstNull() {
		assertFalse(personTest.equals(person));
	}
	
	@Test
	void equalsTestIfSecondNull() {
		assertFalse(person.equals(personTest));
	}

	@Test
	void equalsTestIfBothNull() {
		assertTrue(personTest.equals(personTest));
	}
	
	@Test
	void equalsTestIfOnlyFirstName() {
		personTest.setFirstName(person.getFirstName());
		assertTrue(personTest.equals(person));
	}

	@Test
	void equalsTestIfOnlyFirstNameAndOtherMedicalRecord() {
		personTest.setFirstName(person.getFirstName());
		assertFalse(personTest.equals(personOther));
	}
	
	@Test
	void equalsTestIfOnlyLastName() {
		personTest.setLastName(person.getLastName());
		assertTrue(personTest.equals(person));
	}

	@Test
	void equalsTestIfOnlyLastNameAndOtherMedicalRecord() {
		personTest.setLastName(person.getLastName());
		assertFalse(personTest.equals(personOther));
	}
	
	@Test
	void equalsTestIfOnlyBirthdate() {
		personTest.setBirthdate(person.getBirthdate());
		assertTrue(personTest.equals(person));
	}

	@Test
	void equalsTestIfOnlyBirthdateAndOtherMedicalRecord() {
		personTest.setBirthdate(person.getBirthdate());
		assertFalse(personTest.equals(personOther));
	}
	
	@Test
	void equalsTestIfOnlyMedications() {
		personTest.setMedications(person.getMedications());
		assertTrue(personTest.equals(person));
	}

	@Test
	void equalsTestIfOnlyMedicationsAndOtherMedicalRecord() {
		personTest.setMedications(person.getMedications());
		assertFalse(personTest.equals(personOther));
	}
	
	@Test
	void equalsTestIfOnlyAllergies() {
		personTest.setAllergies(person.getAllergies());
		assertTrue(personTest.equals(person));
	}

	@Test
	void equalsTestIfOnlyAllergiesAndOtherMedicalRecord() {
		personTest.setAllergies(person.getAllergies());
		assertFalse(personTest.equals(personOther));
	}
	
	@Test
	void equalsTestIfBirthdateAndMedications() {
		personTest.setBirthdate(person.getBirthdate());
		personTest.setMedications(person.getMedications());
		assertTrue(personTest.equals(person));
	}

	@Test
	void equalsTestIfBirthdateAndMedicationsAndOtherMedicalRecord() {
		personTest.setBirthdate(person.getBirthdate());
		personTest.setMedications(person.getMedications());
		assertFalse(personTest.equals(personOther));
	}

	@Test
	void equalsTestIfBirthdateAndMedicationsAndOtherMedicalRecordMedications() {
		personTest.setBirthdate(person.getBirthdate());
		personTest.setMedications(personOther.getMedications());
		assertFalse(personTest.equals(personOther));
	}
}
