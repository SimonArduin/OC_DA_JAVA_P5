package com.openclassrooms.safetynetalerts.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.repository.DataBase;
import com.openclassrooms.safetynetalerts.repository.MedicalRecordRepository;

@SpringBootTest(classes = MedicalRecordRepository.class)
public class MedicalRecordRepositoryTest {

	DataBase dataBase = mock(DataBase.class);

	MedicalRecordRepository medicalRecordRepository = new MedicalRecordRepository(dataBase);

	final ArrayList<String> medications = new ArrayList<String>(Arrays.asList("medication 1", "medication 2"));
	final ArrayList<String> allergies = new ArrayList<String>(Arrays.asList("allergy 1", "allergy 2"));
	final MedicalRecord medicalRecord = new MedicalRecord("firstName", "lastName", "birthdate", medications, allergies);
	final ArrayList<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>(Arrays.asList(medicalRecord));

	@BeforeEach
	private void setUpPerTest() {
		Mockito.when(dataBase.getMedicalRecords()).thenReturn(medicalRecords);
		Mockito.when(dataBase.getMedicalRecords(any(MedicalRecord.class))).thenReturn(medicalRecord);
		Mockito.when(dataBase.addMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord);
		Mockito.when(dataBase.removeMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord);
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class deleteTests {

		@Test
		public void deleteTest() {
			assertEquals(medicalRecord, medicalRecordRepository.delete(medicalRecord));
			verify(dataBase, Mockito.times(1)).removeMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void deleteTestIfNotInDB() {
			Mockito.when(dataBase.removeMedicalRecord(any(MedicalRecord.class))).thenReturn(new MedicalRecord());
			assertEquals(new MedicalRecord(), medicalRecordRepository.delete(medicalRecord));
			verify(dataBase, Mockito.times(1)).removeMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void deleteTestIfEmpty() {
			Mockito.when(dataBase.removeMedicalRecord(any(MedicalRecord.class))).thenReturn(new MedicalRecord());
			assertEquals(new MedicalRecord(), medicalRecordRepository.delete(new MedicalRecord()));
			verify(dataBase, Mockito.times(1)).removeMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void deleteTestIfNull() {
			Mockito.when(dataBase.removeMedicalRecord(null)).thenReturn(new MedicalRecord());
			assertEquals(new MedicalRecord(), medicalRecordRepository.delete(null));
			verify(dataBase, Mockito.times(1)).removeMedicalRecord(null);
		}
	}

	@Test
	public void getAllTest() {
		ArrayList<MedicalRecord> result = new ArrayList<MedicalRecord>(medicalRecordRepository.getAll());
		verify(dataBase, Mockito.times(1)).getMedicalRecords();
		assertEquals(medicalRecords.size(), result.size());
		assertTrue(result.contains(medicalRecord));
	}

	@Nested
	class getTests {

		@Test
		public void getTest() {
			assertEquals(medicalRecord, medicalRecordRepository.get(medicalRecord));
			verify(dataBase, Mockito.times(1)).getMedicalRecords(any(MedicalRecord.class));
		}

		@Test
		public void getTestIfNotInDB() {
			Mockito.when(dataBase.getMedicalRecords(any(MedicalRecord.class))).thenReturn(new MedicalRecord());
			assertEquals(new MedicalRecord(), medicalRecordRepository.get(medicalRecord));
			verify(dataBase, Mockito.times(1)).getMedicalRecords(any(MedicalRecord.class));
		}

		@Test
		public void getTestIfEmpty() {
			Mockito.when(dataBase.getMedicalRecords(any(MedicalRecord.class))).thenReturn(new MedicalRecord());
			assertEquals(new MedicalRecord(), medicalRecordRepository.get(new MedicalRecord()));
			verify(dataBase, Mockito.times(1)).getMedicalRecords(any(MedicalRecord.class));
		}

		@Test
		public void getTestIfNull() {
			Mockito.when(dataBase.getMedicalRecords(null)).thenReturn(new MedicalRecord());
			assertEquals(new MedicalRecord(), medicalRecordRepository.get(null));
			verify(dataBase, Mockito.times(1)).getMedicalRecords(null);
		}
	}

	@Nested
	class saveTests {

		@Test
		public void saveTest() {
			Mockito.when(dataBase.addMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord);
			assertEquals(medicalRecord, medicalRecordRepository.save(medicalRecord));
			verify(dataBase, Mockito.times(1)).addMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void saveTestIfAlreadyInDB() {
			Mockito.when(dataBase.addMedicalRecord(any(MedicalRecord.class))).thenReturn(new MedicalRecord());
			assertEquals(new MedicalRecord(), medicalRecordRepository.save(medicalRecord));
			verify(dataBase, Mockito.times(1)).addMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void saveTestIfEmpty() {
			Mockito.when(dataBase.addMedicalRecord(any(MedicalRecord.class))).thenReturn(new MedicalRecord());
			assertEquals(new MedicalRecord(), medicalRecordRepository.save(new MedicalRecord()));
			verify(dataBase, Mockito.times(1)).addMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void saveTestIfNull() {
			Mockito.when(dataBase.addMedicalRecord(null)).thenReturn(new MedicalRecord());
			assertEquals(new MedicalRecord(), medicalRecordRepository.save(null));
			verify(dataBase, Mockito.times(1)).addMedicalRecord(null);
		}
	}
}