package com.openclassrooms.safetynetalerts.integration;

import static org.hamcrest.CoreMatchers.is;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.controller.MedicalRecordController;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.repository.DataBase;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordIT {

	@Autowired
	MedicalRecordController medicalRecordController = new MedicalRecordController();;

	@Autowired
	private MockMvc mockMvc;

	final ArrayList<String> medications = new ArrayList<String>(Arrays.asList("medication 1", "medication 2"));
	final ArrayList<String> allergies = new ArrayList<String>(Arrays.asList("allergy 1", "allergy 2"));
	final MedicalRecord medicalRecord = new MedicalRecord("firstName", "lastName", "06/06/1966", medications,
			allergies);
	final MedicalRecord medicalRecordNotInDB = new MedicalRecord("firstNameOther", "lastNameOther", "06/06/2006",
			medications, allergies);
	MedicalRecord medicalRecordTest;
	final ArrayList<MedicalRecord> medicalRecordList = new ArrayList<MedicalRecord>(
			Arrays.asList(medicalRecord));

	@BeforeEach
	public void setUpPerTest() {
		DataBase.setDataBase(null, null, medicalRecordList);
		medicalRecordTest = new MedicalRecord();
	}

	@Nested
	class putMedicalRecordTests {

		@Test
		public void putMedicalRecordTest() throws Exception {
			mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(medicalRecord))).andExpect(status().isCreated())
					.andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications.[0]", is(medicalRecord.getMedications().get(0))))
					.andExpect(jsonPath("medications.[1]", is(medicalRecord.getMedications().get(1))))
					.andExpect(jsonPath("allergies.[0]", is(medicalRecord.getAllergies().get(0))))
					.andExpect(jsonPath("allergies.[1]", is(medicalRecord.getAllergies().get(1))));
		}

		@Test
		public void putMedicalRecordTestIfNotInDB() throws Exception {
			mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(medicalRecordNotInDB)))
					.andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void putMedicalRecordTestIfNoRequestBody() throws Exception {
			mockMvc.perform(put("/medicalRecord")).andExpect(status().isBadRequest())
					.andExpect(jsonPath("$").doesNotExist());
			;
		}
	}

	@Nested
	class postMedicalRecordTests {

		@Test
		public void postMedicalRecord() throws Exception {
			mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(medicalRecordNotInDB)))
					.andExpect(status().isCreated())
					.andExpect(jsonPath("firstName", is(medicalRecordNotInDB.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecordNotInDB.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecordNotInDB.getBirthdate())))
					.andExpect(jsonPath("medications.[0]", is(medicalRecordNotInDB.getMedications().get(0))))
					.andExpect(jsonPath("medications.[1]", is(medicalRecordNotInDB.getMedications().get(1))))
					.andExpect(jsonPath("allergies.[0]", is(medicalRecordNotInDB.getAllergies().get(0))))
					.andExpect(jsonPath("allergies.[1]", is(medicalRecordNotInDB.getAllergies().get(1))));
		}

		@Test
		public void postMedicalRecordTestIfAlreadyInDB() throws Exception {
			mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(medicalRecord))).andExpect(status().isConflict())
					.andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void postMedicalRecordTestIfNoRequestBody() throws Exception {
			mockMvc.perform(post("/medicalRecord")).andExpect(status().isBadRequest())
					.andExpect(jsonPath("$").doesNotExist());
			;
		}
	}

	@Nested
	class deleteMedicalRecordTests {

		@Test
		public void deleteMedicalRecord() throws Exception {
			mockMvc.perform(delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(medicalRecord))).andExpect(status().isOk())
					.andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications.[0]", is(medicalRecord.getMedications().get(0))))
					.andExpect(jsonPath("medications.[1]", is(medicalRecord.getMedications().get(1))))
					.andExpect(jsonPath("allergies.[0]", is(medicalRecord.getAllergies().get(0))))
					.andExpect(jsonPath("allergies.[1]", is(medicalRecord.getAllergies().get(1))));
		}

		@Test
		public void deleteMedicalRecordIfNotInDB() throws Exception {
			mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(medicalRecordNotInDB)))
					.andExpect(status().isNotFound());
		}

		@Test
		public void deleteMedicalRecordIfNoRequestBody() throws Exception {
			mockMvc.perform(delete(String.format("/medicalRecord"))).andExpect(status().isBadRequest());
		}

		@Test
		public void deleteMedicalRecordIfOnlyFirstNameAndLastName() throws Exception {
			medicalRecordTest.setFirstName(medicalRecord.getFirstName());
			medicalRecordTest.setLastName(medicalRecord.getLastName());
			mockMvc.perform(delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(medicalRecordTest))).andExpect(status().isOk())
					.andExpect(jsonPath("firstName", is(medicalRecord.getFirstName())))
					.andExpect(jsonPath("lastName", is(medicalRecord.getLastName())))
					.andExpect(jsonPath("birthdate", is(medicalRecord.getBirthdate())))
					.andExpect(jsonPath("medications.[0]", is(medicalRecord.getMedications().get(0))))
					.andExpect(jsonPath("medications.[1]", is(medicalRecord.getMedications().get(1))))
					.andExpect(jsonPath("allergies.[0]", is(medicalRecord.getAllergies().get(0))))
					.andExpect(jsonPath("allergies.[1]", is(medicalRecord.getAllergies().get(1))));
		}
	}
}
