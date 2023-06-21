package com.openclassrooms.safetynetalerts.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;

@SpringBootTest(classes = MedicalRecord.class)
public class MedicalRecordTest {

	ArrayList<String> medications;
	ArrayList<String> allergies;
	ArrayList<String> otherMedications;
	ArrayList<String> otherAllergies;
	MedicalRecord medicalRecord;
	MedicalRecord medicalRecordOther;
	MedicalRecord medicalRecordTest;
	String birthdate = "06/06/1966";
	int ageIfError = 999;

	@BeforeEach
	void setUpPerTest() {
		medications = new ArrayList<String>(Arrays.asList("medication 1", "medication 2"));
		allergies = new ArrayList<String>(Arrays.asList("allergy 1", "allergy 2"));
		otherMedications = new ArrayList<String>(Arrays.asList("otherMedication 1", "otherMedication 2"));
		otherAllergies = new ArrayList<String>(Arrays.asList("otherAllergy 1", "otherAllergy 2"));
		medicalRecord = new MedicalRecord("firstName", "lastName", birthdate, medications, allergies);
		medicalRecordOther = new MedicalRecord("otherFirstName", "otherLastName", "otherBirthdate", otherMedications,
				otherAllergies);
		medicalRecordTest = new MedicalRecord();
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class isEmptyTests {

		@Test
		void isEmptyTest() {
			assertFalse(medicalRecord.isEmpty());
		}

		@Test
		void isEmptyTestIfEmpty() {
			assertTrue(medicalRecordTest.isEmpty());
		}

		@Test
		void isEmptyTestIfOnlyBirthdate() {
			medicalRecordTest.setBirthdate(medicalRecord.getBirthdate());
			assertFalse(medicalRecordTest.isEmpty());
		}
	}

	@Nested
	class equalsTests {

		@Test
		void equalsTest() {
			assertTrue(medicalRecord.equals(medicalRecord));
		}

		@Test
		void equalsTestIfNotMedicalRecord() {
			assertFalse(medicalRecord.equals(new Object()));
		}

		@Test
		void equalsTestIfFirstEmpty() {
			assertFalse(medicalRecordTest.equals(medicalRecord));
		}

		@Test
		void equalsTestIfSecondEmpty() {
			assertFalse(medicalRecord.equals(medicalRecordTest));
		}

		@Test
		void equalsTestIfBothEmpty() {
			assertTrue(medicalRecordTest.equals(medicalRecordTest));
		}

		@Test
		void equalsTestIfNull() {
			assertFalse(medicalRecord.equals(null));
		}

		@Test
		void equalsTestIfOtherMedicalRecord() {
			assertFalse(medicalRecord.equals(medicalRecordOther));
		}

		@Test
		void equalsTestIfOnlyName() {
			medicalRecordTest.setFirstName(medicalRecord.getFirstName());
			medicalRecordTest.setLastName(medicalRecord.getLastName());
			assertTrue(medicalRecordTest.equals(medicalRecord));
		}

		@Test
		void equalsTestIfOnlyNameAndOtherMedicalRecord() {
			medicalRecordTest.setFirstName(medicalRecord.getFirstName());
			medicalRecordTest.setLastName(medicalRecord.getLastName());
			assertFalse(medicalRecordTest.equals(medicalRecordOther));
		}
		
		@Test
		void equalsTestIfOnlyFirstName() {
			medicalRecordTest.setFirstName(medicalRecord.getFirstName());
			assertTrue(medicalRecordTest.equals(medicalRecord));
		}

		@Test
		void equalsTestIfOnlyFirstNameAndOtherMedicalRecord() {
			medicalRecordTest.setFirstName(medicalRecord.getFirstName());
			assertFalse(medicalRecordTest.equals(medicalRecordOther));
		}

		@Test
		void equalsTestIfOnlyLastName() {
			medicalRecordTest.setLastName(medicalRecord.getLastName());
			assertTrue(medicalRecordTest.equals(medicalRecord));
		}

		@Test
		void equalsTestIfOnlyLastNameAndOtherMedicalRecord() {
			medicalRecordTest.setLastName(medicalRecord.getLastName());
			assertFalse(medicalRecordTest.equals(medicalRecordOther));
		}

		@Test
		void equalsTestIfOnlyBirthdate() {
			medicalRecordTest.setBirthdate(medicalRecord.getBirthdate());
			assertTrue(medicalRecordTest.equals(medicalRecord));
		}

		@Test
		void equalsTestIfOnlyBirthdateAndOtherMedicalRecord() {
			medicalRecordTest.setBirthdate(medicalRecord.getBirthdate());
			assertFalse(medicalRecordTest.equals(medicalRecordOther));
		}

		@Test
		void equalsTestIfOnlyMedications() {
			medicalRecordTest.setMedications(medicalRecord.getMedications());
			assertTrue(medicalRecordTest.equals(medicalRecord));
		}

		@Test
		void equalsTestIfOnlyMedicationsAndOtherMedicalRecord() {
			medicalRecordTest.setMedications(medicalRecord.getMedications());
			assertFalse(medicalRecordTest.equals(medicalRecordOther));
		}

		@Test
		void equalsTestIfOnlyAllergies() {
			medicalRecordTest.setAllergies(medicalRecord.getAllergies());
			assertTrue(medicalRecordTest.equals(medicalRecord));
		}

		@Test
		void equalsTestIfOnlyAllergiesAndOtherMedicalRecord() {
			medicalRecordTest.setAllergies(medicalRecord.getAllergies());
			assertFalse(medicalRecordTest.equals(medicalRecordOther));
		}

		@Test
		void equalsTestIfBirthdateAndMedications() {
			medicalRecordTest.setBirthdate(medicalRecord.getBirthdate());
			medicalRecordTest.setMedications(medicalRecord.getMedications());
			assertTrue(medicalRecordTest.equals(medicalRecord));
		}

		@Test
		void equalsTestIfBirthdateAndMedicationsAndOtherMedicalRecord() {
			medicalRecordTest.setBirthdate(medicalRecord.getBirthdate());
			medicalRecordTest.setMedications(medicalRecord.getMedications());
			assertFalse(medicalRecordTest.equals(medicalRecordOther));
		}

		@Test
		void equalsTestIfBirthdateAndMedicationsAndOtherMedicalRecordMedications() {
			medicalRecordTest.setBirthdate(medicalRecord.getBirthdate());
			medicalRecordTest.setMedications(medicalRecordOther.getMedications());
			assertFalse(medicalRecordTest.equals(medicalRecordOther));
		}
	}
	
	@Nested
	class updateTests {

		@Test
		void updateTest() {
			medicalRecord.update(medicalRecordOther);
			assertEquals(medicalRecordOther, medicalRecord);
		}

		@Test
		void updateTestIfFirstEmpty() {
			MedicalRecord medicalRecordBefore = medicalRecordTest;
			medicalRecordTest.update(medicalRecord);
			assertEquals(medicalRecordBefore, medicalRecordTest);
		}

		@Test
		void updateTestIfSecondEmpty() {
			MedicalRecord medicalRecordBefore = medicalRecord;
			medicalRecord.update(medicalRecordTest);
			assertEquals(medicalRecordBefore, medicalRecord);
		}
		
		@Test
		void updateTestIfNull() {
			MedicalRecord medicalRecordBefore = medicalRecord;
			medicalRecord.update(null);
			assertEquals(medicalRecordBefore, medicalRecord);
		}

		@Test
		void updateTestIfSame() {
			MedicalRecord medicalRecordBefore = medicalRecord;
			medicalRecordTest = medicalRecord;
			medicalRecord.update(medicalRecordTest);
			assertEquals(medicalRecordBefore, medicalRecord);
		}
	}
	
	@Nested
	class calculateAgeTests {

		@Test
		void updateTest() {
			assertEquals(Period
					.between(LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy")), LocalDate.now())
					.getYears(), medicalRecord.calculateAge());
		}
		
		@Test
		void calculateAgeTestIfBirthdateNull() {
			assertEquals(ageIfError, medicalRecordTest.calculateAge());
		}

		@Test
		void calculateAgeTestIfBirthdateNotCorrectFormat() {
			assertEquals(ageIfError, medicalRecordOther.calculateAge());
		}
	}
}
