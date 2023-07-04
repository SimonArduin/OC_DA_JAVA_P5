package com.openclassrooms.safetynetalerts.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.safetynetalerts.controller.FireStationController;
import com.openclassrooms.safetynetalerts.dto.FireStationURLDto;
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
	final int numberOfPersons = numberOfAdults + numberOfChildren;

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
			assertEquals(HttpStatus.valueOf(201), result.getStatusCode());
			assertEquals(fireStation, result.getBody());
			verify(fireStationService, Mockito.times(1)).putFireStation(any(FireStation.class));
		}

		@Test
		public void putFireStationTestIfNotInDB() throws Exception {
			Mockito.when(fireStationService.putFireStation(any(FireStation.class))).thenReturn(null);
			ResponseEntity<FireStation> result = fireStationController.putFireStation(fireStation);
			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(1)).putFireStation(any(FireStation.class));
		}

		@Test
		public void putFireStationTestIfEmptyBody() throws Exception {
			ResponseEntity<FireStation> result = fireStationController.putFireStation(null);
			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(0)).putFireStation(any(FireStation.class));
		}
	}

	@Nested
	class postFireStationTests {
		
		@Test
		public void postFireStation() throws Exception {
			ResponseEntity<FireStation> result = fireStationController.postFireStation(fireStation);
			assertEquals(HttpStatus.valueOf(201), result.getStatusCode());
			assertEquals(fireStation, result.getBody());
			verify(fireStationService, Mockito.times(1)).postFireStation(any(FireStation.class));
		}

		@Test
		public void postFireStationTestIfNotInDB() throws Exception {
			Mockito.when(fireStationService.postFireStation(any(FireStation.class))).thenReturn(null);
			ResponseEntity<FireStation> result = fireStationController.postFireStation(fireStation);
			assertEquals(HttpStatus.valueOf(409), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(1)).postFireStation(any(FireStation.class));
		}

		@Test
		public void postFireStationTestIfEmptyBody() throws Exception {
			ResponseEntity<FireStation> result = fireStationController.postFireStation(null);
			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(0)).postFireStation(any(FireStation.class));
		}
	}
	
	@Nested
	class deleteFireStationTests {

		@Test
		public void deleteFireStation() throws Exception {
			ResponseEntity<List<FireStation>> result = fireStationController.deleteFireStation(fireStation);
			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
			assertEquals(new ArrayList<FireStation>(Arrays.asList(fireStation)), result.getBody());
			verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
		}

		@Test
		public void deleteFireStationTestIfNotInDB() throws Exception {
			Mockito.when(fireStationService.deleteFireStation(any(FireStation.class))).thenReturn(null);
			ResponseEntity<List<FireStation>> result = fireStationController.deleteFireStation(fireStation);
			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
		}

		@Test
		public void deleteFireStationTestIfEmptyBody() throws Exception {
			ResponseEntity<List<FireStation>> result = fireStationController.deleteFireStation(null);
			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(0)).deleteFireStation(any(FireStation.class));
		}
	}

	@Nested
	class FireStationURLTests {

		@Test
		public void FireStationURLTest() throws Exception {
			
			ResponseEntity<FireStationURLDto> result = fireStationController.fireStationURL(fireStation.getStation());
			
			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
		
			assertEquals(person.getFirstName(), result.getBody().getPersons().get(0).getFirstName());
			assertEquals(person.getLastName(), result.getBody().getPersons().get(0).getLastName());
			assertEquals(person.getAddress(), result.getBody().getPersons().get(0).getAddress());
			assertEquals(person.getPhone(), result.getBody().getPersons().get(0).getPhone());
			
			assertEquals(personChild.getFirstName(), result.getBody().getPersons().get(1).getFirstName());
			assertEquals(personChild.getLastName(), result.getBody().getPersons().get(1).getLastName());
			assertEquals(personChild.getAddress(), result.getBody().getPersons().get(1).getAddress());
			assertEquals(personChild.getPhone(), result.getBody().getPersons().get(1).getPhone());
			
			assertEquals(numberOfAdults, result.getBody().getNumberOfAdults());
			assertEquals(numberOfChildren, result.getBody().getNumberOfChildren());

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(numberOfPersons)).getMedicalRecord(any(Person.class));
		}
		
		@Test
		public void FireStationURLTestIfNoParams() throws Exception {
			
			ResponseEntity<FireStationURLDto> result = fireStationController.fireStationURL(null);
			
			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			
			verify(fireStationService, Mockito.times(0)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void FireStationURLTestIfNoFireStationInDB() throws Exception {
			
			Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
					.thenReturn(null);
			
			ResponseEntity<FireStationURLDto> result = fireStationController.fireStationURL(fireStation.getStation());
			
			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			
			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void FireStationURLTestIfNoPersonsInDB() throws Exception {
			
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(null);
			
			ResponseEntity<FireStationURLDto> result = fireStationController.fireStationURL(fireStation.getStation());
			
			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());

			assertTrue(result.getBody().getPersons().isEmpty());
			
			assertEquals(0, result.getBody().getNumberOfAdults());
			assertEquals(0, result.getBody().getNumberOfChildren());
			
			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}
	}
}
