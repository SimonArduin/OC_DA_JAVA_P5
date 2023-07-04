package com.openclassrooms.safetynetalerts.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.repository.DataBase;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationIT {

	@Autowired
	private MockMvc mockMvc;

	final FireStation fireStation = new FireStation("address", "station");
	final FireStation fireStationNotInDB = new FireStation("addresOther", "stationOther");
	FireStation fireStationTest;
	final ArrayList<FireStation> fireStationList = new ArrayList<FireStation>(Arrays.asList(fireStation));

	final Person person = new Person("firstName", "lastName", fireStation.getAddress(), "city", "zip", "phone", "email");
	final Person personChild = new Person("firstNameChild", "lastNameChild", person.getAddress(), person.getCity(),
			person.getZip(), person.getPhone(), person.getEmail());
	final ArrayList<Person> personList = new ArrayList<Person>(Arrays.asList(person, personChild));

	final ArrayList<String> medications = new ArrayList<String>(Arrays.asList("medication 1", "medication 2"));
	final ArrayList<String> allergies = new ArrayList<String>(Arrays.asList("allergy 1", "allergy 2"));
	final MedicalRecord medicalRecord = new MedicalRecord(person.getFirstName(), person.getLastName(), "06/06/1966", medications,
			allergies);
	final MedicalRecord medicalRecordChild = new MedicalRecord(personChild.getFirstName(), personChild.getLastName(), "06/06/2006",
			medications, allergies);
	final ArrayList<MedicalRecord> medicalRecordList = new ArrayList<MedicalRecord>(
			Arrays.asList(medicalRecord, medicalRecordChild));

	final int numberOfAdults = 1;
	final int numberOfChildren = 1;

	@BeforeEach
	public void setUpPerTest() {
		DataBase.setDataBase(fireStationList, personList, medicalRecordList);
		fireStationTest = new FireStation();
	}

	@Nested
	class getFireStationTests {

		@Test
		public void getFireStationTest() throws Exception {
			mockMvc.perform(get(String.format("/firestation?stationNumber=%s", fireStation.getStation())))
					.andExpect(status().isOk())

					.andExpect(jsonPath("persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("persons.[0].address", is(person.getAddress())))
					.andExpect(jsonPath("persons.[0].phone", is(person.getPhone())))

					.andExpect(jsonPath("persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("persons.[1].address", is(personChild.getAddress())))
					.andExpect(jsonPath("persons.[1].phone", is(personChild.getPhone())))

					.andExpect(jsonPath("numberOfAdults", is(numberOfAdults)))
					.andExpect(jsonPath("numberOfChildren", is(numberOfChildren)));
		}

		@Test
		public void getFireStationTestIfNoFireStation() throws Exception {
			DataBase.setDataBase(null, personList, medicalRecordList);
			mockMvc.perform(get(String.format("/firestation?stationNumber=%s", fireStation.getStation())))
					.andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void getFireStationTestIfNoPersons() throws Exception {
			DataBase.setDataBase(fireStationList, null, medicalRecordList);
			mockMvc.perform(get(String.format("/firestation?stationNumber=%s", fireStation.getStation())))
			.andExpect(status().isOk())

			.andExpect(jsonPath("persons").isEmpty())

			.andExpect(jsonPath("numberOfAdults", is(0)))
			.andExpect(jsonPath("numberOfChildren", is(0)));
		}

		@Test
		public void getFireStationTestIfNoMedicalRecords() throws Exception {
			DataBase.setDataBase(fireStationList, personList, null);
			mockMvc.perform(get(String.format("/firestation?stationNumber=%s", fireStation.getStation())))
					.andExpect(status().isOk())

					.andExpect(jsonPath("persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("persons.[0].address", is(person.getAddress())))
					.andExpect(jsonPath("persons.[0].phone", is(person.getPhone())))

					.andExpect(jsonPath("persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("persons.[1].address", is(personChild.getAddress())))
					.andExpect(jsonPath("persons.[1].phone", is(personChild.getPhone())))

					.andExpect(jsonPath("numberOfAdults", is(numberOfAdults + numberOfChildren)))
					.andExpect(jsonPath("numberOfChildren", is(0)));
		}

		@Test
		public void getFireStationTestIfNoParams() throws Exception {
			mockMvc.perform(get("/firestation")).andExpect(status().isBadRequest())
					.andExpect(jsonPath("$").doesNotExist());
			;
		}
	}

	@Nested
	class putFireStationTests {

		@Test
		public void putFireStationTest() throws Exception {
			mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(fireStation))).andExpect(status().isCreated())
					.andExpect(jsonPath("address", is(fireStation.getAddress())))
					.andExpect(jsonPath("station", is(fireStation.getStation())));
		}

		@Test
		public void putFireStationTestIfNotInDB() throws Exception {
			mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(fireStationNotInDB)))
					.andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void putFireStationTestIfNoRequestBody() throws Exception {
			mockMvc.perform(put("/firestation")).andExpect(status().isBadRequest())
					.andExpect(jsonPath("$").doesNotExist());
			;
		}
	}

	@Nested
	class postFireStationTests {

		@Test
		public void postFireStation() throws Exception {
			mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(fireStationNotInDB))).andExpect(status().isCreated())
					.andExpect(jsonPath("address", is(fireStationNotInDB.getAddress())))
					.andExpect(jsonPath("station", is(fireStationNotInDB.getStation())));
		}

		@Test
		public void postFireStationTestIfAlreadyInDB() throws Exception {
			mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(fireStation))).andExpect(status().isConflict())
					.andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void postFireStationTestIfNoRequestBody() throws Exception {
			mockMvc.perform(post("/firestation")).andExpect(status().isBadRequest())
					.andExpect(jsonPath("$").doesNotExist());
			;
		}
	}

	@Nested
	class deleteFireStationTests {

		@Test
		public void deleteFireStation() throws Exception {
			mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(fireStation))).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
					.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
		}

		@Test
		public void deleteFireStationIfNotInDB() throws Exception {
			mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(fireStationNotInDB)))
					.andExpect(status().isNotFound());
		}

		@Test
		public void deleteFireStationIfNoRequestBody() throws Exception {
			mockMvc.perform(delete(String.format("/firestation"))).andExpect(status().isBadRequest());
		}

		@Test
		public void deleteFireStationByAddress() throws Exception {
			fireStationTest.setAddress(fireStation.getAddress());
			mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(fireStationTest))).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
					.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
		}

		@Test
		public void deleteFireStationByStation() throws Exception {
			fireStationTest.setStation(fireStation.getStation());
			mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(fireStationTest))).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
					.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
		}
	}
}