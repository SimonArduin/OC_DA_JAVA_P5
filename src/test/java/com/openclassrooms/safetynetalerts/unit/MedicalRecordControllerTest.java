package com.openclassrooms.safetynetalerts.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.safetynetalerts.controller.MedicalRecordController;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.service.MedicalRecordService;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

	@Autowired
	MedicalRecordController medicalRecordController;

	@MockBean
	MedicalRecordService medicalRecordService;

	final ArrayList<String> medications = new ArrayList<String>(Arrays.asList("medication 1", "medication 2"));
	final ArrayList<String> allergies = new ArrayList<String>(Arrays.asList("allergy 1", "allergy 2"));
	final MedicalRecord medicalRecord = new MedicalRecord("firstName", "lastName", "birthdate", medications, allergies);
	final MedicalRecord medicalRecordOnlyName = new MedicalRecord("firstName", "lastName");

	@BeforeEach
	private void setUp() {
		Mockito.when(medicalRecordService.deleteMedicalRecord(medicalRecord)).thenReturn(medicalRecord);
		Mockito.when(medicalRecordService.putMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord);
		Mockito.when(medicalRecordService.postMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord);
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class putMedicalRecordTests {

		@Test
		public void putMedicalRecord() throws Exception {
			ResponseEntity<MedicalRecord> result = medicalRecordController.putMedicalRecord(medicalRecord);
			assertEquals(HttpStatus.valueOf(201), result.getStatusCode());
			assertEquals(medicalRecord, result.getBody());
			verify(medicalRecordService, Mockito.times(1)).putMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void putMedicalRecordTestIfNotInDB() throws Exception {
			Mockito.when(medicalRecordService.putMedicalRecord(any(MedicalRecord.class))).thenReturn(null);
			ResponseEntity<MedicalRecord> result = medicalRecordController.putMedicalRecord(medicalRecord);
			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(medicalRecordService, Mockito.times(1)).putMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void putMedicalRecordTestIfEmptyBody() throws Exception {
			ResponseEntity<MedicalRecord> result = medicalRecordController.putMedicalRecord(null);
			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(medicalRecordService, Mockito.times(0)).putMedicalRecord(any(MedicalRecord.class));
		}
	}

	@Nested
	class postMedicalRecordTests {
		
		@Test
		public void postMedicalRecord() throws Exception {
			ResponseEntity<MedicalRecord> result = medicalRecordController.postMedicalRecord(medicalRecord);
			assertEquals(HttpStatus.valueOf(201), result.getStatusCode());
			assertEquals(medicalRecord, result.getBody());
			verify(medicalRecordService, Mockito.times(1)).postMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void postMedicalRecordTestIfNotInDB() throws Exception {
			Mockito.when(medicalRecordService.postMedicalRecord(any(MedicalRecord.class))).thenReturn(null);
			ResponseEntity<MedicalRecord> result = medicalRecordController.postMedicalRecord(medicalRecord);
			assertEquals(HttpStatus.valueOf(409), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(medicalRecordService, Mockito.times(1)).postMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void postMedicalRecordTestIfEmptyBody() throws Exception {
			ResponseEntity<MedicalRecord> result = medicalRecordController.postMedicalRecord(null);
			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(medicalRecordService, Mockito.times(0)).postMedicalRecord(any(MedicalRecord.class));
		}
	}
	
	@Nested
	class deleteMedicalRecordTests {

		@Test
		public void deleteMedicalRecord() throws Exception {
			ResponseEntity<MedicalRecord> result = medicalRecordController.deleteMedicalRecord(medicalRecord);
			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
			assertEquals(medicalRecord, result.getBody());
			verify(medicalRecordService, Mockito.times(1)).deleteMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void deleteMedicalRecordTestIfNotInDB() throws Exception {
			Mockito.when(medicalRecordService.deleteMedicalRecord(any(MedicalRecord.class))).thenReturn(null);
			ResponseEntity<MedicalRecord> result = medicalRecordController.deleteMedicalRecord(medicalRecord);
			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(medicalRecordService, Mockito.times(1)).deleteMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void deleteMedicalRecordTestIfEmptyBody() throws Exception {
			ResponseEntity<MedicalRecord> result = medicalRecordController.deleteMedicalRecord(null);
			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(medicalRecordService, Mockito.times(0)).deleteMedicalRecord(any(MedicalRecord.class));
		}
	}
}