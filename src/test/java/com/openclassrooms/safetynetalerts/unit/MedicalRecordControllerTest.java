package com.openclassrooms.safetynetalerts.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.safetynetalerts.controller.MedicalRecordController;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.service.MedicalRecordService;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

	@Autowired
	MedicalRecordController medicalRecordController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	MedicalRecordService medicalRecordService;

	final ArrayList<String> medications = new ArrayList<String>(Arrays.asList("medication 1", "medication 2"));
	final ArrayList<String> allergies = new ArrayList<String>(Arrays.asList("allergy 1", "allergy 2"));
	final MedicalRecord medicalRecord = new MedicalRecord("firstName", "lastName", "birthdate", medications, allergies);
	final MedicalRecord medicalRecordOnlyName = new MedicalRecord("firstName", "lastName");

	@BeforeEach
	private void setUp() {
		Mockito.when(medicalRecordService.deleteMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord);
		Mockito.when(medicalRecordService.putMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord);
		Mockito.when(medicalRecordService.postMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord);
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class putMedicalRecordTests {

		@Test
		public void putMedicalRecordTest() throws Exception {
			mockMvc.perform(put(
					String.format("/medicalRecord?firstName=%s&lastName=%s&birthdate=%s&medications=%s&allergies=%s",
							medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getBirthdate(),
							medicalRecord.getMedications(), medicalRecord.getAllergies())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("allergies", is(medicalRecord.getAllergies())));
			verify(medicalRecordService, Mockito.times(1)).putMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void putMedicalRecordTestIfNotInDB() throws Exception {
			Mockito.when(medicalRecordService.putMedicalRecord(any(MedicalRecord.class)))
					.thenReturn(new MedicalRecord());
			mockMvc.perform(put(
					String.format("/medicalRecord?firstName=%s&lastName=%s&birthdate=%s&medications=%s&allergies=%s",
							medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getBirthdate(),
							medicalRecord.getMedications(), medicalRecord.getAllergies())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(nullValue())))
					.andExpect(jsonPath("lastName", is(nullValue()))).andExpect(jsonPath("birthdate", is(nullValue())))
					.andExpect(jsonPath("medications", is(nullValue())))
					.andExpect(jsonPath("allergies", is(nullValue())));
			verify(medicalRecordService, Mockito.times(1)).putMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void putMedicalRecordTestIfEmptyParams() throws Exception {
			Mockito.when(medicalRecordService.putMedicalRecord(any(MedicalRecord.class)))
					.thenReturn(new MedicalRecord());
			mockMvc.perform(put(
					String.format("/medicalRecord?firstName=%s&lastName=%s&birthdate=%s&medications=%s&allergies=%s",
							medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getBirthdate(),
							medicalRecord.getMedications(), medicalRecord.getAllergies())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(nullValue())))
					.andExpect(jsonPath("lastName", is(nullValue()))).andExpect(jsonPath("birthdate", is(nullValue())))
					.andExpect(jsonPath("medications", is(nullValue())))
					.andExpect(jsonPath("allergies", is(nullValue())));
			verify(medicalRecordService, Mockito.times(1)).putMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void putMedicalRecordTestIfNoParams() throws Exception {
			mockMvc.perform(put(String.format("/medicalRecord"))).andExpect(status().is(400));
			verify(medicalRecordService, Mockito.times(0)).putMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void putMedicalRecordTestIfOnlyName() throws Exception {
			Mockito.when(medicalRecordService.putMedicalRecord(any(MedicalRecord.class)))
					.thenReturn(medicalRecordOnlyName);
			mockMvc.perform(put(String.format("/medicalRecord?firstName=%s&lastName=%s", medicalRecord.getFirstName(),
					medicalRecord.getLastName()))).andExpect(status().isOk())
					.andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(nullValue())))
					.andExpect(jsonPath("medications", is(nullValue())))
					.andExpect(jsonPath("allergies", is(nullValue())));
			verify(medicalRecordService, Mockito.times(1)).putMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void putMedicalRecordTestIfOnlyBirthdate() throws Exception {
			mockMvc.perform(put(String.format("/medicalRecord?firstName=%s&lastName=%s&birthdate=%s",
					medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getBirthdate())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("allergies", is(medicalRecord.getAllergies())));
			verify(medicalRecordService, Mockito.times(1)).putMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void putMedicalRecordTestIfOnlyMedications() throws Exception {
			mockMvc.perform(put(String.format("/medicalRecord?firstName=%s&lastName=%s&medications=%s",
					medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getMedications())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("allergies", is(medicalRecord.getAllergies())));
			verify(medicalRecordService, Mockito.times(1)).putMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void putMedicalRecordTestIfOnlyAllergies() throws Exception {
			mockMvc.perform(put(String.format("/medicalRecord?firstName=%s&lastName=%s&allergies=%s",
					medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getAllergies())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("allergies", is(medicalRecord.getAllergies())));
			verify(medicalRecordService, Mockito.times(1)).putMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void putMedicalRecordTestIfBirthdateAndAllergies() throws Exception {
			mockMvc.perform(put(String.format("/medicalRecord?firstName=%s&lastName=%s&birthdate=%s&allergies=%s",
					medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getBirthdate(),
					medicalRecord.getAllergies()))).andExpect(status().isOk())
					.andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("allergies", is(medicalRecord.getAllergies())));
			verify(medicalRecordService, Mockito.times(1)).putMedicalRecord(any(MedicalRecord.class));
		}
	}

	@Nested
	class postMedicalRecordTests {

		@Test
		public void postMedicalRecordTest() throws Exception {
			mockMvc.perform(post(
					String.format("/medicalRecord?firstName=%s&lastName=%s&birthdate=%s&medications=%s&allergies=%s",
							medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getBirthdate(),
							medicalRecord.getMedications(), medicalRecord.getAllergies())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("allergies", is(medicalRecord.getAllergies())));
			verify(medicalRecordService, Mockito.times(1)).postMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void postMedicalRecordTestIfNotInDB() throws Exception {
			Mockito.when(medicalRecordService.postMedicalRecord(any(MedicalRecord.class)))
					.thenReturn(new MedicalRecord());
			mockMvc.perform(post(
					String.format("/medicalRecord?firstName=%s&lastName=%s&birthdate=%s&medications=%s&allergies=%s",
							medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getBirthdate(),
							medicalRecord.getMedications(), medicalRecord.getAllergies())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(nullValue())))
					.andExpect(jsonPath("lastName", is(nullValue()))).andExpect(jsonPath("birthdate", is(nullValue())))
					.andExpect(jsonPath("medications", is(nullValue())))
					.andExpect(jsonPath("allergies", is(nullValue())));
			verify(medicalRecordService, Mockito.times(1)).postMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void postMedicalRecordTestIfEmptyParams() throws Exception {
			Mockito.when(medicalRecordService.postMedicalRecord(any(MedicalRecord.class)))
					.thenReturn(new MedicalRecord());
			mockMvc.perform(post(
					String.format("/medicalRecord?firstName=%s&lastName=%s&birthdate=%s&medications=%s&allergies=%s",
							medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getBirthdate(),
							medicalRecord.getMedications(), medicalRecord.getAllergies())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(nullValue())))
					.andExpect(jsonPath("lastName", is(nullValue()))).andExpect(jsonPath("birthdate", is(nullValue())))
					.andExpect(jsonPath("medications", is(nullValue())))
					.andExpect(jsonPath("allergies", is(nullValue())));
			verify(medicalRecordService, Mockito.times(1)).postMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void postMedicalRecordTestIfNoParams() throws Exception {
			mockMvc.perform(post(String.format("/medicalRecord"))).andExpect(status().is(400));
			verify(medicalRecordService, Mockito.times(0)).postMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void postMedicalRecordTestIfOnlyName() throws Exception {
			Mockito.when(medicalRecordService.postMedicalRecord(any(MedicalRecord.class)))
					.thenReturn(medicalRecordOnlyName);
			mockMvc.perform(post(String.format("/medicalRecord?firstName=%s&lastName=%s", medicalRecord.getFirstName(),
					medicalRecord.getLastName()))).andExpect(status().isOk())
					.andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(nullValue())))
					.andExpect(jsonPath("medications", is(nullValue())))
					.andExpect(jsonPath("allergies", is(nullValue())));
			verify(medicalRecordService, Mockito.times(1)).postMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void postMedicalRecordTestIfOnlyBirthdate() throws Exception {
			mockMvc.perform(post(String.format("/medicalRecord?firstName=%s&lastName=%s&birthdate=%s",
					medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getBirthdate())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("allergies", is(medicalRecord.getAllergies())));
			verify(medicalRecordService, Mockito.times(1)).postMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void postMedicalRecordTestIfOnlyMedications() throws Exception {
			mockMvc.perform(post(String.format("/medicalRecord?firstName=%s&lastName=%s&medications=%s",
					medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getMedications())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("allergies", is(medicalRecord.getAllergies())));
			verify(medicalRecordService, Mockito.times(1)).postMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void postMedicalRecordTestIfOnlyAllergies() throws Exception {
			mockMvc.perform(post(String.format("/medicalRecord?firstName=%s&lastName=%s&allergies=%s",
					medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getAllergies())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("allergies", is(medicalRecord.getAllergies())));
			verify(medicalRecordService, Mockito.times(1)).postMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void postMedicalRecordTestIfBirthdateAndAllergies() throws Exception {
			mockMvc.perform(post(String.format("/medicalRecord?firstName=%s&lastName=%s&birthdate=%s&allergies=%s",
					medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord.getBirthdate(),
					medicalRecord.getAllergies()))).andExpect(status().isOk())
					.andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("allergies", is(medicalRecord.getAllergies())));
			verify(medicalRecordService, Mockito.times(1)).postMedicalRecord(any(MedicalRecord.class));
		}
	}

	@Nested
	class deleteMedicalRecordTests {

		@Test
		public void deleteMedicalRecord() throws Exception {
			mockMvc.perform(delete(String.format("/medicalRecord?firstName=%s&lastName=%s",
					medicalRecord.getFirstName(), medicalRecord.getLastName()))).andExpect(status().isOk())
					.andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("allergies", is(medicalRecord.getAllergies())));
			verify(medicalRecordService, Mockito.times(1)).deleteMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void deleteMedicalRecordIfNotInDB() throws Exception {
			Mockito.when(medicalRecordService.deleteMedicalRecord(any(MedicalRecord.class)))
					.thenReturn(new MedicalRecord());
			mockMvc.perform(delete(String.format("/medicalRecord?firstName=%s&lastName=%s",
					medicalRecord.getFirstName(), medicalRecord.getLastName()))).andExpect(status().isOk())
					.andExpect(jsonPath("firstName", is(nullValue()))).andExpect(jsonPath("lastName", is(nullValue())))
					.andExpect(jsonPath("birthdate", is(nullValue())))
					.andExpect(jsonPath("medications", is(nullValue())))
					.andExpect(jsonPath("allergies", is(nullValue())));
			verify(medicalRecordService, Mockito.times(1)).deleteMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void deleteMedicalRecordTestIfEmptyParams() throws Exception {
			Mockito.when(medicalRecordService.deleteMedicalRecord(any(MedicalRecord.class)))
					.thenReturn(new MedicalRecord());
			mockMvc.perform(delete(
					String.format("/medicalRecord?firstName=%s&lastName=%s",
							medicalRecord.getFirstName(), medicalRecord.getLastName())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(nullValue())))
					.andExpect(jsonPath("lastName", is(nullValue()))).andExpect(jsonPath("birthdate", is(nullValue())))
					.andExpect(jsonPath("medications", is(nullValue())))
					.andExpect(jsonPath("allergies", is(nullValue())));
			verify(medicalRecordService, Mockito.times(1)).deleteMedicalRecord(any(MedicalRecord.class));
		}

		@Test
		public void deleteMedicalRecordTestIfNoParams() throws Exception {
			mockMvc.perform(delete(String.format("/medicalRecord"))).andExpect(status().is(400));
			verify(medicalRecordService, Mockito.times(0)).deleteMedicalRecord(any(MedicalRecord.class));
		}
	}
}
