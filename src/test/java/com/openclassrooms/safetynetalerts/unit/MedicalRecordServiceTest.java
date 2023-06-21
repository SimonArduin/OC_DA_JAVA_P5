package com.openclassrooms.safetynetalerts.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalerts.service.MedicalRecordService;

@SpringBootTest(classes = MedicalRecordService.class)
public class MedicalRecordServiceTest {

	@Autowired
	MedicalRecordService medicalRecordService;

	@MockBean
	MedicalRecordRepository medicalRecordRepository;

	final ArrayList<String> medications = new ArrayList<String>(Arrays.asList("medication 1", "medication 2"));
	final ArrayList<String> allergies = new ArrayList<String>(Arrays.asList("allergy 1", "allergy 2"));
	final MedicalRecord medicalRecord = new MedicalRecord("firstName", "lastName", "birthdate", medications, allergies);
	final MedicalRecord emptyMedicalRecord = new MedicalRecord();
	final ArrayList<MedicalRecord> medicalRecordList = new ArrayList<MedicalRecord>(Arrays.asList(medicalRecord));
	final ArrayList<MedicalRecord> emptyMedicalRecordList = new ArrayList<MedicalRecord>();
	
	final Person person = new Person(medicalRecord.getFirstName(), medicalRecord.getLastName());

	@BeforeEach
	private void SetUp() {
		Mockito.when(medicalRecordRepository.get(any(MedicalRecord.class))).thenReturn(medicalRecordList);
		Mockito.when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(medicalRecord);
		Mockito.when(medicalRecordRepository.delete(any(MedicalRecord.class))).thenReturn(medicalRecord);
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class getMedicalRecordByMedicalRecordTests {

		@Test
		public void getMedicalRecordByMedicalRecordTest() {
			assertEquals(medicalRecordList, medicalRecordService.getMedicalRecord(medicalRecord));
			verify(medicalRecordRepository, Mockito.times(1)).get(any(MedicalRecord.class));
		}

		@Test
		public void getMedicalRecordByMedicalRecordTestIfNotInDB() {
			Mockito.when(medicalRecordRepository.get(any(MedicalRecord.class))).thenReturn(emptyMedicalRecordList);
			assertEquals(emptyMedicalRecordList, medicalRecordService.getMedicalRecord(medicalRecord));
			verify(medicalRecordRepository, Mockito.times(1)).get(any(MedicalRecord.class));
		}

		@Test
		public void getMedicalRecordByMedicalRecordTestIfEmpty() {
			Mockito.when(medicalRecordRepository.get(any(MedicalRecord.class))).thenReturn(emptyMedicalRecordList);
			assertEquals(emptyMedicalRecordList, medicalRecordService.getMedicalRecord(emptyMedicalRecord));
			verify(medicalRecordRepository, Mockito.times(1)).get(any(MedicalRecord.class));
		}

		@Test
		public void getMedicalRecordByMedicalRecordTestIfNull() {
			Mockito.when(medicalRecordRepository.get(null)).thenReturn(emptyMedicalRecordList);
			assertEquals(emptyMedicalRecordList, medicalRecordService.getMedicalRecord((MedicalRecord) null));
			verify(medicalRecordRepository, Mockito.times(1)).get(null);
		}
	}
	
	@Nested
	class getMedicalRecordByPersonTests {

		@Test
		public void getMedicalRecordByPersonTest() {
			assertEquals(medicalRecord, medicalRecordService.getMedicalRecord(person));
			verify(medicalRecordRepository, Mockito.times(1)).get(any(MedicalRecord.class));
		}

		@Test
		public void getMedicalRecordByPersonTestIfNotInDB() {
			Mockito.when(medicalRecordRepository.get(any(MedicalRecord.class))).thenReturn(emptyMedicalRecordList);
			assertEquals(emptyMedicalRecord, medicalRecordService.getMedicalRecord(person));
			verify(medicalRecordRepository, Mockito.times(1)).get(any(MedicalRecord.class));
		}

		@Test
		public void getMedicalRecordByPersonTestIfEmpty() {
			Mockito.when(medicalRecordRepository.get(any(MedicalRecord.class))).thenReturn(emptyMedicalRecordList);
			assertEquals(emptyMedicalRecord, medicalRecordService.getMedicalRecord(new Person()));
			verify(medicalRecordRepository, Mockito.times(1)).get(any(MedicalRecord.class));
		}

		@Test
		public void getMedicalRecordByPersonTestIfNull() {
			Mockito.when(medicalRecordRepository.get(null)).thenReturn(emptyMedicalRecordList);
			assertEquals(emptyMedicalRecord, medicalRecordService.getMedicalRecord((Person) null));
			verify(medicalRecordRepository, Mockito.times(0)).get(null);
		}
	}

	@Nested
	class puttMedicalRecordTests {

		@Test
		public void putMedicalRecordTest() {
			assertEquals(medicalRecord, medicalRecordService.putMedicalRecord(medicalRecord));
			verify(medicalRecordRepository, Mockito.times(1)).get(any(MedicalRecord.class));
			verify(medicalRecordRepository, Mockito.times(1)).delete(any(MedicalRecord.class));
			verify(medicalRecordRepository, Mockito.times(1)).save(any(MedicalRecord.class));
		}

		@Test
		public void putMedicalRecordTestIfNotInDB() {
			Mockito.when(medicalRecordRepository.get(any(MedicalRecord.class))).thenReturn(emptyMedicalRecordList);
			assertEquals(emptyMedicalRecord, medicalRecordService.putMedicalRecord(medicalRecord));
			verify(medicalRecordRepository, Mockito.times(1)).get(any(MedicalRecord.class));
			verify(medicalRecordRepository, Mockito.times(0)).delete(any(MedicalRecord.class));
			verify(medicalRecordRepository, Mockito.times(0)).save(any(MedicalRecord.class));
		}

		@Test
		public void putMedicalRecordTestIfEmpty() {
			assertEquals(new MedicalRecord(), medicalRecordService.putMedicalRecord(new MedicalRecord()));
			verify(medicalRecordRepository, Mockito.times(0)).get(any(MedicalRecord.class));
			verify(medicalRecordRepository, Mockito.times(0)).delete(any(MedicalRecord.class));
			verify(medicalRecordRepository, Mockito.times(0)).save(any(MedicalRecord.class));
		}

		@Test
		public void putMedicalRecordTestIfNull() {
			assertEquals(new MedicalRecord(), medicalRecordService.putMedicalRecord(null));
			verify(medicalRecordRepository, Mockito.times(0)).get(any(MedicalRecord.class));
			verify(medicalRecordRepository, Mockito.times(0)).delete(any(MedicalRecord.class));
			verify(medicalRecordRepository, Mockito.times(0)).save(any(MedicalRecord.class));
		}
	}

	@Nested
	class postMedicalRecordTests {

		@Test
		public void postMedicalRecordTest() {
			assertEquals(medicalRecord, medicalRecordService.postMedicalRecord(medicalRecord));
			verify(medicalRecordRepository, Mockito.times(1)).save(any(MedicalRecord.class));
		}

		@Test
		public void postMedicalRecordTestIfAlreadyInDB() {
			Mockito.when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(emptyMedicalRecord);
			assertEquals(emptyMedicalRecord, medicalRecordService.postMedicalRecord(medicalRecord));
			verify(medicalRecordRepository, Mockito.times(1)).save(any(MedicalRecord.class));
		}

		@Test
		public void postMedicalRecordTestIfEmpty() {
			Mockito.when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(emptyMedicalRecord);
			assertEquals(emptyMedicalRecord, medicalRecordService.postMedicalRecord(emptyMedicalRecord));
			verify(medicalRecordRepository, Mockito.times(1)).save(any(MedicalRecord.class));
		}

		@Test
		public void postMedicalRecordTestIfNull() {
			Mockito.when(medicalRecordRepository.save(null)).thenReturn(emptyMedicalRecord);
			assertEquals(emptyMedicalRecord, medicalRecordService.postMedicalRecord(null));
			verify(medicalRecordRepository, Mockito.times(1)).save(null);
		}
	}

	@Nested
	class deleteMedicalRecordTests {

		@Test
		public void deleteMedicalRecordTest() {
			assertEquals(medicalRecord, medicalRecordService.deleteMedicalRecord(medicalRecord));
			verify(medicalRecordRepository, Mockito.times(1)).delete(any(MedicalRecord.class));
		}

		@Test
		public void deleteMedicalRecordTestIfNotInDB() {
			Mockito.when(medicalRecordRepository.delete(any(MedicalRecord.class))).thenReturn(emptyMedicalRecord);
			assertEquals(emptyMedicalRecord, medicalRecordService.deleteMedicalRecord(medicalRecord));
			verify(medicalRecordRepository, Mockito.times(1)).delete(any(MedicalRecord.class));
		}

		@Test
		public void deleteMedicalRecordTestIfEmpty() {
			Mockito.when(medicalRecordRepository.delete(any(MedicalRecord.class))).thenReturn(emptyMedicalRecord);
			assertEquals(emptyMedicalRecord, medicalRecordService.deleteMedicalRecord(emptyMedicalRecord));
			verify(medicalRecordRepository, Mockito.times(1)).delete(any(MedicalRecord.class));
		}

		@Test
		public void deleteMedicalRecordTestIfNull() {
			Mockito.when(medicalRecordRepository.delete(null)).thenReturn(emptyMedicalRecord);
			assertEquals(emptyMedicalRecord, medicalRecordService.deleteMedicalRecord(null));
			verify(medicalRecordRepository, Mockito.times(1)).delete(null);
		}
	}
}
