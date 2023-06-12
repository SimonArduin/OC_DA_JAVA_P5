package com.openclassrooms.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalerts.service.MedicalRecordService;

@SpringBootTest(classes = MedicalRecordService.class)
public class MedicalRecordServiceTest {

	@Autowired
	MedicalRecordService medicalRecordService;

	@MockBean
	MedicalRecordRepository medicalRecordRepository;

	final ArrayList<String> medications = new ArrayList<String>(Arrays.asList("medication 1","medication 2"));
	final ArrayList<String> allergies = new ArrayList<String>(Arrays.asList("allergy 1","allergy 2"));
	final MedicalRecord medicalRecord = new MedicalRecord("firstName", "lastName", "birthdate", medications, allergies);
	final MedicalRecord emptyMedicalRecord = new MedicalRecord();
	final ArrayList<MedicalRecord> medicalRecordList = new ArrayList<MedicalRecord>(Arrays.asList(medicalRecord));
	final ArrayList<MedicalRecord> emptyList = new ArrayList<MedicalRecord>();

	@BeforeEach
	private void SetUp() {
		Mockito.when(medicalRecordRepository.get(any(MedicalRecord.class))).thenReturn(medicalRecord);
		Mockito.when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(medicalRecord);
		Mockito.when(medicalRecordRepository.delete(any(MedicalRecord.class))).thenReturn(medicalRecord);
	}

	@Test
	void contextLoads() {
	}
	
	@Test
	public void getMedicalRecordTest() {
		assertEquals(medicalRecord, medicalRecordService.getMedicalRecord(medicalRecord));
		verify(medicalRecordRepository, Mockito.times(1)).get(any(MedicalRecord.class));
	}

	@Test
	public void getMedicalRecordTestIfNotInDB() {
		Mockito.when(medicalRecordRepository.get(any(MedicalRecord.class))).thenReturn(emptyMedicalRecord);
		assertEquals(emptyMedicalRecord, medicalRecordService.getMedicalRecord(medicalRecord));
		verify(medicalRecordRepository, Mockito.times(1)).get(any(MedicalRecord.class));
	}

	@Test
	public void putMedicalRecordTest() {
		assertEquals(medicalRecord, medicalRecordService.putMedicalRecord(medicalRecord));
		verify(medicalRecordRepository, Mockito.times(1)).get(any(MedicalRecord.class));
		verify(medicalRecordRepository, Mockito.times(1)).delete(medicalRecord);
		verify(medicalRecordRepository, Mockito.times(1)).save(medicalRecord);
		}

	@Test
	public void putMedicalRecordTestIfNotInDB() {
		Mockito.when(medicalRecordRepository.get(any(MedicalRecord.class))).thenReturn(emptyMedicalRecord);
		assertEquals(emptyMedicalRecord, medicalRecordService.putMedicalRecord(medicalRecord));
		verify(medicalRecordRepository, Mockito.times(1)).get(any(MedicalRecord.class));
		verify(medicalRecordRepository, Mockito.times(0)).delete(medicalRecord);
		verify(medicalRecordRepository, Mockito.times(0)).save(medicalRecord);
		}
	
	@Test
	public void postMedicalRecordTest() {
		assertEquals(medicalRecord, medicalRecordService.postMedicalRecord(medicalRecord));
		verify(medicalRecordRepository, Mockito.times(1)).save(medicalRecord);
	}

	@Test
	public void postMedicalRecordTestIfAlreadyInDB() {
		Mockito.when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(emptyMedicalRecord);
		assertEquals(emptyMedicalRecord, medicalRecordService.postMedicalRecord(medicalRecord));
		verify(medicalRecordRepository, Mockito.times(1)).save(medicalRecord);
	}

	@Test
	public void deleteMedicalRecordTest() {
		assertEquals(medicalRecord, medicalRecordService.deleteMedicalRecord(medicalRecord));
		verify(medicalRecordRepository, Mockito.times(1)).delete(medicalRecord);
	}

	@Test
	public void deleteMedicalRecordTestIfNotInDB() {
		Mockito.when(medicalRecordRepository.delete(any(MedicalRecord.class))).thenReturn(emptyMedicalRecord);
		assertEquals(emptyMedicalRecord, medicalRecordService.deleteMedicalRecord(medicalRecord));
		verify(medicalRecordRepository, Mockito.times(1)).delete(medicalRecord);
	}
}
