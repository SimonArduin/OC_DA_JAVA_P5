package com.openclassrooms.safetynetalerts.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.safetynetalerts.controller.FireStationController;
import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.FireStationService;
import com.openclassrooms.safetynetalerts.service.MedicalRecordService;
import com.openclassrooms.safetynetalerts.service.PersonService;

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

	@Autowired
	FireStationController fireStationController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	FireStationService fireStationService;

	@MockBean
	PersonService personService;

	@MockBean
	MedicalRecordService medicalRecordService;

	final FireStation fireStation = new FireStation("address", "station");
	final FireStation fireStationOtherAddress = new FireStation("otherAddress", fireStation.getStation());

	final Person person = new Person("firstName", "lastName", fireStation.getAddress(), "city", "zip", "phone",
			"email");
	final Person personChild = new Person("firstNameChild", "lastNameChild", person.getAddress(), person.getCity(),
			person.getZip(), person.getPhone(), person.getEmail());

	final ArrayList<String> medications = new ArrayList<String>(Arrays.asList("medication 1", "medication 2"));
	final ArrayList<String> allergies = new ArrayList<String>(Arrays.asList("allergy 1", "allergy 2"));
	final MedicalRecord medicalRecord = new MedicalRecord("firstName", "lastName", "06/06/1966", medications,
			allergies);
	final MedicalRecord medicalRecordChild = new MedicalRecord("firstNameChild", "lastNameChild", "06/06/2006",
			medications, allergies);

	final int numberOfFireStationByStationNumber = 1;
	final int numberOfAdults = 1;
	final int numberOfChildren = 1;

	@BeforeEach
	private void setUp() {
		Mockito.when(fireStationService.deleteFireStation(any(FireStation.class)))
				.thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		Mockito.when(fireStationService.putFireStation(any(FireStation.class))).thenReturn(fireStation);
		Mockito.when(fireStationService.postFireStation(any(FireStation.class))).thenReturn(fireStation);
		Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
				.thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		Mockito.when(personService.getPerson(any(Person.class)))
				.thenReturn(new ArrayList<Person>(Arrays.asList(person, personChild)));
		Mockito.when(medicalRecordService.getMedicalRecord(person)).thenReturn(medicalRecord);
		Mockito.when(medicalRecordService.getMedicalRecord(personChild)).thenReturn(medicalRecordChild);
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class putFireStationTests {

		@Test
		public void putFireStation() throws Exception {
			ResponseEntity<FireStation> result = fireStationController.putFireStation(fireStation);
			assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
			assertEquals(fireStation, result.getBody());
			verify(fireStationService, Mockito.times(1)).putFireStation(any(FireStation.class));
		}

		@Test
		public void putFireStationTestIfNotInDB() throws Exception {
			Mockito.when(fireStationService.putFireStation(any(FireStation.class))).thenReturn(null);
			ResponseEntity<FireStation> result = fireStationController.putFireStation(fireStation);
			assertEquals(HttpStatusCode.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(1)).putFireStation(any(FireStation.class));
		}

		@Test
		public void putFireStationTestIfEmptyBody() throws Exception {
			ResponseEntity<FireStation> result = fireStationController.putFireStation(null);
			assertEquals(HttpStatusCode.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(0)).putFireStation(any(FireStation.class));
		}
	}

	@Nested
	class postFireStationTests {
		
		@Test
		public void postFireStation() throws Exception {
			ResponseEntity<FireStation> result = fireStationController.postFireStation(fireStation);
			assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
			assertEquals(fireStation, result.getBody());
			verify(fireStationService, Mockito.times(1)).postFireStation(any(FireStation.class));
		}

		@Test
		public void postFireStationTestIfNotInDB() throws Exception {
			Mockito.when(fireStationService.postFireStation(any(FireStation.class))).thenReturn(null);
			ResponseEntity<FireStation> result = fireStationController.postFireStation(fireStation);
			assertEquals(HttpStatusCode.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(1)).postFireStation(any(FireStation.class));
		}

		@Test
		public void postFireStationTestIfEmptyBody() throws Exception {
			ResponseEntity<FireStation> result = fireStationController.postFireStation(null);
			assertEquals(HttpStatusCode.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(0)).postFireStation(any(FireStation.class));
		}
	}
	
	@Nested
	class deleteFireStationTests {

		@Test
		public void deleteFireStation() throws Exception {
			ResponseEntity<List<FireStation>> result = fireStationController.deleteFireStation(fireStation);
			assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
			assertEquals(new ArrayList<FireStation>(Arrays.asList(fireStation)), result.getBody());
			verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
		}

		@Test
		public void deleteFireStationTestIfNotInDB() throws Exception {
			Mockito.when(fireStationService.deleteFireStation(any(FireStation.class))).thenReturn(null);
			ResponseEntity<List<FireStation>> result = fireStationController.deleteFireStation(fireStation);
			assertEquals(HttpStatusCode.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
		}

		@Test
		public void deleteFireStationTestIfEmptyBody() throws Exception {
			ResponseEntity<List<FireStation>> result = fireStationController.deleteFireStation(null);
			assertEquals(HttpStatusCode.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(0)).deleteFireStation(any(FireStation.class));
		}
	}

	@Nested
	class FireStationURLTests {

		@Test
		public void FireStationURLTest() throws Exception {
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

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(2)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void FireStationURLTestIfEmptyParams() throws Exception {
			Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
					.thenReturn(new ArrayList<FireStation>());
			mockMvc.perform(get(String.format("/firestation?stationNumber=%s", nullValue()))).andExpect(status().isOk())
					.andExpect(jsonPath("numberOfAdults", is(0))).andExpect(jsonPath("numberOfChildren", is(0)));

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void FireStationURLTestIfNoParams() throws Exception {
			mockMvc.perform(get(String.format("/firestation"))).andExpect(status().is(400));

			verify(fireStationService, Mockito.times(0)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void FireStationURLTestIfNoFireStation() throws Exception {
			Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
					.thenReturn(new ArrayList<FireStation>());
			mockMvc.perform(get(String.format("/firestation?stationNumber=%s", fireStation.getStation())))
					.andExpect(status().isOk()).andExpect(jsonPath("numberOfAdults", is(0)))
					.andExpect(jsonPath("numberOfChildren", is(0)));

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void FireStationURLTestIfNoPersons() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
			mockMvc.perform(get(String.format("/firestation?stationNumber=%s", fireStation.getStation())))
					.andExpect(status().isOk()).andExpect(jsonPath("numberOfAdults", is(0)))
					.andExpect(jsonPath("numberOfChildren", is(0)));

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}
	}
}
