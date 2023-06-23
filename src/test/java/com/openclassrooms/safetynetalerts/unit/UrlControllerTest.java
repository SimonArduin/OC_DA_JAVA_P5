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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.safetynetalerts.controller.URLController;
import com.openclassrooms.safetynetalerts.dto.ChildAlertURLDto;
import com.openclassrooms.safetynetalerts.dto.CommunityEmailURLDto;
import com.openclassrooms.safetynetalerts.dto.FireURLDto;
import com.openclassrooms.safetynetalerts.dto.FloodStationsURLDto;
import com.openclassrooms.safetynetalerts.dto.PersonInfoURLDto;
import com.openclassrooms.safetynetalerts.dto.PhoneAlertURLDto;
import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.FireStationService;
import com.openclassrooms.safetynetalerts.service.MedicalRecordService;
import com.openclassrooms.safetynetalerts.service.PersonService;

@WebMvcTest(controllers = URLController.class)
public class URLControllerTest {

	@Autowired
	URLController urlController;

	@MockBean
	FireStationService fireStationService;

	@MockBean
	PersonService personService;

	@MockBean
	MedicalRecordService medicalRecordService;

	final FireStation fireStation = new FireStation("address", "station");
	final FireStation fireStationOther = new FireStation("otherAdress", "otherStation");
	final ArrayList<String> fireStationStationsList = new ArrayList<String>(
			Arrays.asList(fireStation.getStation(), fireStationOther.getStation()));

	final Person person = new Person("firstName", "lastName", fireStation.getAddress(), "city", "zip", "phone",
			"email");
	final Person personChild = new Person("firstNameChild", "lastNameChild", person.getAddress(), person.getCity(),
			person.getZip(), person.getPhone(), person.getEmail());

	final ArrayList<String> medications = new ArrayList<String>(Arrays.asList("medication 1", "medication 2"));
	final ArrayList<String> allergies = new ArrayList<String>(Arrays.asList("allergy 1", "allergy 2"));
	final MedicalRecord medicalRecord = new MedicalRecord("firstName", "lastName", "06/06/1966", medications,
			allergies);
	final MedicalRecord medicalRecordOnlyName = new MedicalRecord(medicalRecord.getFirstName(),
			medicalRecord.getLastName());
	final MedicalRecord medicalRecordChild = new MedicalRecord("firstNameChild", "lastNameChild", "06/06/2006",
			medications, allergies);
	final MedicalRecord medicalRecordChildOnlyName = new MedicalRecord(medicalRecordChild.getFirstName(),
			medicalRecordChild.getLastName());

	final int numberOfAdults = 1;
	final int numberOfChildren = 1;
	final int numberOfPersons = numberOfAdults + numberOfChildren;
	final int numberOfFireStationByStationNumber = 1;

	@BeforeEach
	private void setUp() {
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
	class ChildAlertURLTests {

		@Test
		public void childAlertURLTest() throws Exception {
			ResponseEntity<ChildAlertURLDto> result = urlController.childAlertURL(personChild.getAddress());

			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
			assertEquals(personChild.getFirstName(), result.getBody().getChildren().get(0).getFirstName());
			assertEquals(personChild.getLastName(), result.getBody().getChildren().get(0).getLastName());
			assertEquals(medicalRecordChild.calculateAge(), result.getBody().getChildren().get(0).getAge());
			assertEquals(person, result.getBody().getAdults().get(0));
			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(numberOfAdults)).getMedicalRecord(person);
			verify(medicalRecordService, Mockito.times(numberOfChildren)).getMedicalRecord(personChild);
		}

		@Test
		public void childAlertURLTestIfNoParams() throws Exception {
			ResponseEntity<ChildAlertURLDto> result = urlController.childAlertURL(null);

			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(person);
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(personChild);
		}

		@Test
		public void childAlertURLTestIfNoPerson() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(null);

			ResponseEntity<ChildAlertURLDto> result = urlController.childAlertURL(personChild.getAddress());

			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(person);
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(personChild);
		}

		@Test
		public void childAlertURLTestIfNoMedicalRecord() throws Exception {
			Mockito.when(medicalRecordService.getMedicalRecord(any(Person.class))).thenReturn(null);

			ResponseEntity<ChildAlertURLDto> result = urlController.childAlertURL(personChild.getAddress());

			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
			assertEquals(person, result.getBody().getAdults().get(0));
			assertEquals(personChild, result.getBody().getAdults().get(1));
			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(1)).getMedicalRecord(person);
			verify(medicalRecordService, Mockito.times(1)).getMedicalRecord(personChild);
		}

		@Test
		public void childAlertURLTestIfNoBirthdate() throws Exception {
			Mockito.when(medicalRecordService.getMedicalRecord(personChild)).thenReturn(medicalRecordChildOnlyName);

			ResponseEntity<ChildAlertURLDto> result = urlController.childAlertURL(personChild.getAddress());

			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
			assertEquals(person, result.getBody().getAdults().get(0));
			assertEquals(personChild, result.getBody().getAdults().get(1));
			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(1)).getMedicalRecord(person);
			verify(medicalRecordService, Mockito.times(1)).getMedicalRecord(personChild);
		}
	}

	@Nested
	class PhoneAlertURLTests {

		@Test
		public void phoneAlertURLTest() throws Exception {
			ResponseEntity<PhoneAlertURLDto> result = urlController.phoneAlertURL(fireStation.getStation());

			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
			assertEquals(person.getPhone(), result.getBody().getPhones().get(0));
			assertEquals(personChild.getPhone(), result.getBody().getPhones().get(1));
			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
		}

		@Test
		public void phoneAlertURLTestIfNoParams() throws Exception {
			ResponseEntity<PhoneAlertURLDto> result = urlController.phoneAlertURL(null);

			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(0)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
		}

		@Test
		public void phoneAlertURLTestIfNoFireStation() throws Exception {
			Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
					.thenReturn(null);

			ResponseEntity<PhoneAlertURLDto> result = urlController.phoneAlertURL(fireStation.getStation());

			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
		}

		@Test
		public void phoneAlertURLTestIfNoPerson() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(null);

			ResponseEntity<PhoneAlertURLDto> result = urlController.phoneAlertURL(fireStation.getStation());

			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
		}

		@Test
		public void phoneAlertURLTestIfNoPhone() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class)))
					.thenReturn(new ArrayList<Person>(Arrays.asList(new Person())));

			ResponseEntity<PhoneAlertURLDto> result = urlController.phoneAlertURL(fireStation.getStation());

			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
		}
	}

	@Nested
	class FireURLTests {

		@Test
		public void fireURLTest() throws Exception {
			ResponseEntity<FireURLDto> result = urlController.fireURL(fireStation.getAddress());

			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());

			assertEquals(person.getFirstName(), result.getBody().getPersons().get(0).getFirstName());
			assertEquals(person.getLastName(), result.getBody().getPersons().get(0).getLastName());
			assertEquals(person.getPhone(), result.getBody().getPersons().get(0).getPhone());
			assertEquals(medicalRecord.calculateAge(), result.getBody().getPersons().get(0).getAge());
			assertEquals(medicalRecord.getMedications(), result.getBody().getPersons().get(0).getMedications());
			assertEquals(medicalRecord.getAllergies(), result.getBody().getPersons().get(0).getAllergies());

			assertEquals(personChild.getFirstName(), result.getBody().getPersons().get(1).getFirstName());
			assertEquals(personChild.getLastName(), result.getBody().getPersons().get(1).getLastName());
			assertEquals(personChild.getPhone(), result.getBody().getPersons().get(1).getPhone());
			assertEquals(medicalRecordChild.calculateAge(), result.getBody().getPersons().get(1).getAge());
			assertEquals(medicalRecordChild.getMedications(), result.getBody().getPersons().get(1).getMedications());
			assertEquals(medicalRecordChild.getAllergies(), result.getBody().getPersons().get(1).getAllergies());

			assertEquals(fireStation.getStation(), result.getBody().getFireStationNumber());

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(numberOfPersons)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void fireURLTestIfNoParams() throws Exception {
			ResponseEntity<FireURLDto> result = urlController.fireURL(null);

			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(0)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void fireURLTestIfNoFireStation() throws Exception {
			Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
					.thenReturn(null);

			ResponseEntity<FireURLDto> result = urlController.fireURL(fireStation.getAddress());

			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void fireURLTestIfNoPerson() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(null);

			ResponseEntity<FireURLDto> result = urlController.fireURL(fireStation.getAddress());

			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void fireURLTestIfNoMedicalRecord() throws Exception {
			Mockito.when(medicalRecordService.getMedicalRecord(any(Person.class))).thenReturn(null);

			ResponseEntity<FireURLDto> result = urlController.fireURL(fireStation.getAddress());

			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());

			assertEquals(person.getFirstName(), result.getBody().getPersons().get(0).getFirstName());
			assertEquals(person.getLastName(), result.getBody().getPersons().get(0).getLastName());
			assertEquals(person.getPhone(), result.getBody().getPersons().get(0).getPhone());
			assertEquals(null, result.getBody().getPersons().get(0).getAge());
			assertEquals(null, result.getBody().getPersons().get(0).getMedications());
			assertEquals(null, result.getBody().getPersons().get(0).getAllergies());

			assertEquals(personChild.getFirstName(), result.getBody().getPersons().get(1).getFirstName());
			assertEquals(personChild.getLastName(), result.getBody().getPersons().get(1).getLastName());
			assertEquals(personChild.getPhone(), result.getBody().getPersons().get(1).getPhone());
			assertEquals(null, result.getBody().getPersons().get(1).getAge());
			assertEquals(null, result.getBody().getPersons().get(1).getMedications());
			assertEquals(null, result.getBody().getPersons().get(1).getAllergies());

			assertEquals(fireStation.getStation(), result.getBody().getFireStationNumber());

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(numberOfPersons)).getMedicalRecord(any(Person.class));
		}
	}

	@Nested
	class FloodStationsURLTests {

		@Test
		public void floodStationsURLTest() throws Exception {
			ResponseEntity<FloodStationsURLDto> result = urlController.floodStationsURL(fireStationStationsList);

			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());

			assertEquals(person.getFirstName(), result.getBody().getHomes().get(0).getPersons().get(0).getFirstName());
			assertEquals(person.getLastName(), result.getBody().getHomes().get(0).getPersons().get(0).getLastName());
			assertEquals(person.getPhone(), result.getBody().getHomes().get(0).getPersons().get(0).getPhone());
			assertEquals(medicalRecord.calculateAge(), result.getBody().getHomes().get(0).getPersons().get(0).getAge());
			assertEquals(medicalRecord.getMedications(),
					result.getBody().getHomes().get(0).getPersons().get(0).getMedications());
			assertEquals(medicalRecord.getAllergies(),
					result.getBody().getHomes().get(0).getPersons().get(0).getAllergies());

			assertEquals(personChild.getFirstName(),
					result.getBody().getHomes().get(0).getPersons().get(1).getFirstName());
			assertEquals(personChild.getLastName(),
					result.getBody().getHomes().get(0).getPersons().get(1).getLastName());
			assertEquals(personChild.getPhone(), result.getBody().getHomes().get(0).getPersons().get(1).getPhone());
			assertEquals(medicalRecordChild.calculateAge(),
					result.getBody().getHomes().get(0).getPersons().get(1).getAge());
			assertEquals(medicalRecordChild.getMedications(),
					result.getBody().getHomes().get(0).getPersons().get(1).getMedications());
			assertEquals(medicalRecordChild.getAllergies(),
					result.getBody().getHomes().get(0).getPersons().get(1).getAllergies());

			assertEquals(fireStation.getStation(), result.getBody().getHomes().get(0).getFireStationNumber());

			assertEquals(person.getFirstName(), result.getBody().getHomes().get(1).getPersons().get(0).getFirstName());
			assertEquals(person.getLastName(), result.getBody().getHomes().get(1).getPersons().get(0).getLastName());
			assertEquals(person.getPhone(), result.getBody().getHomes().get(1).getPersons().get(0).getPhone());
			assertEquals(medicalRecord.calculateAge(), result.getBody().getHomes().get(1).getPersons().get(0).getAge());
			assertEquals(medicalRecord.getMedications(),
					result.getBody().getHomes().get(1).getPersons().get(0).getMedications());
			assertEquals(medicalRecord.getAllergies(),
					result.getBody().getHomes().get(1).getPersons().get(0).getAllergies());

			assertEquals(personChild.getFirstName(),
					result.getBody().getHomes().get(1).getPersons().get(1).getFirstName());
			assertEquals(personChild.getLastName(),
					result.getBody().getHomes().get(1).getPersons().get(1).getLastName());
			assertEquals(personChild.getPhone(), result.getBody().getHomes().get(1).getPersons().get(1).getPhone());
			assertEquals(medicalRecordChild.calculateAge(),
					result.getBody().getHomes().get(1).getPersons().get(1).getAge());
			assertEquals(medicalRecordChild.getMedications(),
					result.getBody().getHomes().get(1).getPersons().get(1).getMedications());
			assertEquals(medicalRecordChild.getAllergies(),
					result.getBody().getHomes().get(1).getPersons().get(1).getAllergies());

			assertEquals(fireStation.getStation(), result.getBody().getHomes().get(1).getFireStationNumber());

			verify(fireStationService, Mockito.times(fireStationStationsList.size()))
					.getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(fireStationStationsList.size())).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(fireStationStationsList.size() * (numberOfPersons)))
					.getMedicalRecord(any(Person.class));
		}

		@Test
		public void floodStationsURLTestIfNoParams() throws Exception {
			ResponseEntity<FloodStationsURLDto> result = urlController.floodStationsURL(null);

			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());

			verify(fireStationService, Mockito.times(0)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void floodStationsURLTestIfNoFireStation() throws Exception {
			Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
					.thenReturn(null);

			ResponseEntity<FloodStationsURLDto> result = urlController.floodStationsURL(fireStationStationsList);

			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());

			verify(fireStationService, Mockito.times(fireStationStationsList.size()))
					.getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void floodStationsURLTestIfNoPerson() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(null);

			ResponseEntity<FloodStationsURLDto> result = urlController.floodStationsURL(fireStationStationsList);

			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
			assertEquals(fireStation.getStation(), result.getBody().getHomes().get(0).getFireStationNumber());
			assertEquals(fireStation.getStation(), result.getBody().getHomes().get(1).getFireStationNumber());

			verify(fireStationService, Mockito.times(fireStationStationsList.size()))
					.getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(fireStationStationsList.size())).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void floodStationsURLTestIfNoMedicalRecord() throws Exception {
			Mockito.when(medicalRecordService.getMedicalRecord(any(Person.class))).thenReturn(null);

			ResponseEntity<FloodStationsURLDto> result = urlController.floodStationsURL(fireStationStationsList);

			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());

			assertEquals(person.getFirstName(), result.getBody().getHomes().get(0).getPersons().get(0).getFirstName());
			assertEquals(person.getLastName(), result.getBody().getHomes().get(0).getPersons().get(0).getLastName());
			assertEquals(person.getPhone(), result.getBody().getHomes().get(0).getPersons().get(0).getPhone());
			assertEquals(null, result.getBody().getHomes().get(0).getPersons().get(0).getAge());
			assertEquals(null, result.getBody().getHomes().get(0).getPersons().get(0).getMedications());
			assertEquals(null, result.getBody().getHomes().get(0).getPersons().get(0).getAllergies());

			assertEquals(personChild.getFirstName(),
					result.getBody().getHomes().get(0).getPersons().get(1).getFirstName());
			assertEquals(personChild.getLastName(),
					result.getBody().getHomes().get(0).getPersons().get(1).getLastName());
			assertEquals(personChild.getPhone(), result.getBody().getHomes().get(0).getPersons().get(1).getPhone());
			assertEquals(null, result.getBody().getHomes().get(0).getPersons().get(1).getAge());
			assertEquals(null, result.getBody().getHomes().get(0).getPersons().get(1).getMedications());
			assertEquals(null, result.getBody().getHomes().get(0).getPersons().get(1).getAllergies());

			assertEquals(fireStation.getStation(), result.getBody().getHomes().get(0).getFireStationNumber());

			assertEquals(person.getFirstName(), result.getBody().getHomes().get(1).getPersons().get(0).getFirstName());
			assertEquals(person.getLastName(), result.getBody().getHomes().get(1).getPersons().get(0).getLastName());
			assertEquals(person.getPhone(), result.getBody().getHomes().get(1).getPersons().get(0).getPhone());
			assertEquals(null, result.getBody().getHomes().get(1).getPersons().get(0).getAge());
			assertEquals(null, result.getBody().getHomes().get(1).getPersons().get(0).getMedications());
			assertEquals(null, result.getBody().getHomes().get(1).getPersons().get(0).getAllergies());

			assertEquals(personChild.getFirstName(),
					result.getBody().getHomes().get(1).getPersons().get(1).getFirstName());
			assertEquals(personChild.getLastName(),
					result.getBody().getHomes().get(1).getPersons().get(1).getLastName());
			assertEquals(personChild.getPhone(), result.getBody().getHomes().get(1).getPersons().get(1).getPhone());
			assertEquals(null, result.getBody().getHomes().get(1).getPersons().get(1).getAge());
			assertEquals(null, result.getBody().getHomes().get(1).getPersons().get(1).getMedications());
			assertEquals(null, result.getBody().getHomes().get(1).getPersons().get(1).getAllergies());

			assertEquals(fireStation.getStation(), result.getBody().getHomes().get(1).getFireStationNumber());

			verify(fireStationService, Mockito.times(fireStationStationsList.size()))
					.getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(fireStationStationsList.size())).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(fireStationStationsList.size() * (numberOfPersons)))
					.getMedicalRecord(any(Person.class));
		}
	}

	@Nested
	class PersonInfoURLTests {

		@Test
		public void personInfoURLTest() throws Exception {
			ResponseEntity<PersonInfoURLDto> result = urlController.personInfoURL(person.getFirstName(),
					person.getLastName());

			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());

			assertEquals(person.getFirstName(), result.getBody().getPersons().get(0).getFirstName());
			assertEquals(person.getLastName(), result.getBody().getPersons().get(0).getLastName());
			assertEquals(medicalRecord.calculateAge(), result.getBody().getPersons().get(0).getAge());
			assertEquals(person.getAddress(), result.getBody().getPersons().get(0).getAddress());
			assertEquals(medicalRecord.getMedications(), result.getBody().getPersons().get(0).getMedications());
			assertEquals(medicalRecord.getAllergies(), result.getBody().getPersons().get(0).getAllergies());

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(numberOfPersons)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void personInfoURLTestIfNoParams() throws Exception {
			ResponseEntity<PersonInfoURLDto> result = urlController.personInfoURL(null, null);

			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());

			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void personInfoURLTestIfOnlyFirstName() throws Exception {
			ResponseEntity<PersonInfoURLDto> result = urlController.personInfoURL(person.getFirstName(), null);

			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());

			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void personInfoURLTestIfOnlyLastName() throws Exception {
			ResponseEntity<PersonInfoURLDto> result = urlController.personInfoURL(null, person.getLastName());

			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());

			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}
		
		@Test
		public void personInfoURLTestIfNoPerson() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(null);

			ResponseEntity<PersonInfoURLDto> result = urlController.personInfoURL(person.getFirstName(),
					person.getLastName());

			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void personInfoURLTestIfNoMedicalRecord() throws Exception {
			Mockito.when(medicalRecordService.getMedicalRecord(any(Person.class))).thenReturn(null);
			
			ResponseEntity<PersonInfoURLDto> result = urlController.personInfoURL(person.getFirstName(),
					person.getLastName());

			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());

			assertEquals(person.getFirstName(), result.getBody().getPersons().get(0).getFirstName());
			assertEquals(person.getLastName(), result.getBody().getPersons().get(0).getLastName());
			assertEquals(null, result.getBody().getPersons().get(0).getAge());
			assertEquals(person.getAddress(), result.getBody().getPersons().get(0).getAddress());
			assertEquals(null, result.getBody().getPersons().get(0).getMedications());
			assertEquals(null, result.getBody().getPersons().get(0).getAllergies());

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(numberOfPersons)).getMedicalRecord(any(Person.class));
		}
	}

	@Nested
	class CommunityEmailURLTests {

		@Test
		public void communityEmailURLTest() throws Exception {
			ResponseEntity<CommunityEmailURLDto> result = urlController.communityEmailURL(person.getCity());

			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
			assertEquals(person.getEmail(), result.getBody().getEmails().get(0));
			assertEquals(personChild.getEmail(), result.getBody().getEmails().get(1));

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		}

		@Test
		public void communityEmailURLTestIfNoParams() throws Exception {
			ResponseEntity<CommunityEmailURLDto> result = urlController.communityEmailURL(null);

			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());

			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
		}

		@Test
		public void communityEmailURLTestIfNoPerson() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(null);
			
			ResponseEntity<CommunityEmailURLDto> result = urlController.communityEmailURL(person.getCity());

			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		}

		@Test
		public void communityEmailURLTestIfNoEmail() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>(Arrays.asList(new Person())));
			
			ResponseEntity<CommunityEmailURLDto> result = urlController.communityEmailURL(person.getCity());

			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		}
	}
}
