package com.openclassrooms.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

	@BeforeEach
	void setUpPerTest() {
		medications = new ArrayList<String>(Arrays.asList("medication 1", "medication 2"));
		allergies = new ArrayList<String>(Arrays.asList("allergy 1", "allergy 2"));
		otherMedications = new ArrayList<String>(Arrays.asList("otherMedication 1", "otherMedication 2"));
		otherAllergies = new ArrayList<String>(Arrays.asList("otherAllergy 1", "otherAllergy 2"));
		medicalRecord = new MedicalRecord("firstName", "lastName", "birthdate", medications, allergies);
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
		void equalsTestIfFirstNull() {
			assertFalse(medicalRecordTest.equals(medicalRecord));
		}

		@Test
		void equalsTestIfSecondNull() {
			assertFalse(medicalRecord.equals(medicalRecordTest));
		}

		@Test
		void equalsTestIfBothNull() {
			assertTrue(medicalRecordTest.equals(medicalRecordTest));
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
			medicalRecordTest.setFirstName(medicalRecord.getFirstName());
			medicalRecordTest.setLastName(medicalRecord.getLastName());
			medicalRecordTest.setBirthdate(medicalRecordOther.getBirthdate());
			medicalRecordTest.setMedications(medicalRecordOther.getMedications());
			assertTrue(medicalRecord.update(medicalRecordTest));
			assertEquals(medicalRecordTest.getBirthdate(), medicalRecord.getBirthdate());
			assertEquals(medicalRecordTest.getMedications(), medicalRecord.getMedications());
		}

		@Test
		void updateTestIfFirstNull() {
			MedicalRecord medicalRecordBefore = medicalRecordTest;
			assertFalse(medicalRecordTest.update(medicalRecord));
			assertEquals(medicalRecordBefore, medicalRecordTest);
		}

		@Test
		void updateTestIfSecondNull() {
			MedicalRecord medicalRecordBefore = medicalRecord;
			assertFalse(medicalRecord.update(medicalRecordTest));
			assertEquals(medicalRecordBefore, medicalRecord);
		}

		@Test
		void updateTestIfSame() {
			MedicalRecord medicalRecordBefore = medicalRecord;
			medicalRecordTest = medicalRecord;
			assertTrue(medicalRecord.update(medicalRecordTest));
			assertEquals(medicalRecordBefore, medicalRecord);
		}

		@Test
		void updateTestIfNotSameName() {
			MedicalRecord medicalRecordBefore = medicalRecord;
			assertFalse(medicalRecord.update(medicalRecordOther));
			assertEquals(medicalRecordBefore, medicalRecord);
		}
	}
}
