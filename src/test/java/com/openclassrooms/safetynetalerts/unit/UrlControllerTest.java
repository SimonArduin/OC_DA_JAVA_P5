package com.openclassrooms.safetynetalerts.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.openclassrooms.safetynetalerts.controller.URLController;
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

	@Autowired
	private MockMvc mockMvc;

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

	final int ageIfNoBirthdate = 999;
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
			mockMvc.perform(get(String.format("/childAlert?address=%s", personChild.getAddress())))
					.andExpect(status().isOk())

					.andExpect(jsonPath("children.[0].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("children.[0].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("children.[0].age", is(medicalRecordChild.calculateAge())))

					.andExpect(jsonPath("adults.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("adults.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("adults.[0].address", is(person.getAddress())))
					.andExpect(jsonPath("adults.[0].city", is(person.getCity())))
					.andExpect(jsonPath("adults.[0].zip", is(person.getZip())))
					.andExpect(jsonPath("adults.[0].phone", is(person.getPhone())));

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(1)).getMedicalRecord(person);
			verify(medicalRecordService, Mockito.times(1)).getMedicalRecord(personChild);
		}

		@Test
		public void childAlertURLTestIfEmptyParams() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
			mockMvc.perform(get(String.format("/childAlert?address=%s", nullValue()))).andExpect(status().isOk());

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(person);
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(personChild);
		}

		@Test
		public void childAlertURLTestIfNoParams() throws Exception {
			mockMvc.perform(get(String.format("/childAlert"))).andExpect(status().is(400));

			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(person);
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(personChild);
		}

		@Test
		public void childAlertURLTestIfNoPerson() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
			mockMvc.perform(get(String.format("/childAlert?address=%s", personChild.getAddress())))
					.andExpect(status().isOk());

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(person);
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(personChild);
		}

		@Test
		public void childAlertURLTestIfNoMedicalRecord() throws Exception {
			Mockito.when(medicalRecordService.getMedicalRecord(any(Person.class))).thenReturn(new MedicalRecord());
			mockMvc.perform(get(String.format("/childAlert?address=%s", personChild.getAddress())))
					.andExpect(status().isOk())

					.andExpect(jsonPath("adults.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("adults.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("adults.[0].address", is(person.getAddress())))
					.andExpect(jsonPath("adults.[0].city", is(person.getCity())))
					.andExpect(jsonPath("adults.[0].zip", is(person.getZip())))
					.andExpect(jsonPath("adults.[0].phone", is(person.getPhone())))

					.andExpect(jsonPath("adults.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("adults.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("adults.[1].address", is(personChild.getAddress())))
					.andExpect(jsonPath("adults.[1].city", is(personChild.getCity())))
					.andExpect(jsonPath("adults.[1].zip", is(personChild.getZip())))
					.andExpect(jsonPath("adults.[1].phone", is(personChild.getPhone())));

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(1)).getMedicalRecord(person);
			verify(medicalRecordService, Mockito.times(1)).getMedicalRecord(personChild);
		}

		@Test
		public void childAlertURLTestIfNoBirthdate() throws Exception {
			Mockito.when(medicalRecordService.getMedicalRecord(personChild)).thenReturn(medicalRecordChildOnlyName);
			mockMvc.perform(get(String.format("/childAlert?address=%s", personChild.getAddress())))
					.andExpect(status().isOk())

					.andExpect(jsonPath("adults.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("adults.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("adults.[0].address", is(person.getAddress())))
					.andExpect(jsonPath("adults.[0].city", is(person.getCity())))
					.andExpect(jsonPath("adults.[0].zip", is(person.getZip())))
					.andExpect(jsonPath("adults.[0].phone", is(person.getPhone())))

					.andExpect(jsonPath("adults.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("adults.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("adults.[1].address", is(personChild.getAddress())))
					.andExpect(jsonPath("adults.[1].city", is(personChild.getCity())))
					.andExpect(jsonPath("adults.[1].zip", is(personChild.getZip())))
					.andExpect(jsonPath("adults.[1].phone", is(personChild.getPhone())));

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(1)).getMedicalRecord(person);
			verify(medicalRecordService, Mockito.times(1)).getMedicalRecord(personChild);
		}
	}

	@Nested
	class PhoneAlertURLTests {

		@Test
		public void phoneAlertURLTest() throws Exception {
			mockMvc.perform(get(String.format("/phoneAlert?firestation=%s", fireStation.getStation())))
					.andExpect(status().isOk())

					.andExpect(jsonPath("phones.[0]", is(person.getPhone())))

					.andExpect(jsonPath("phones.[1]", is(personChild.getPhone())));

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
		}

		@Test
		public void phoneAlertURLTestIfEmptyParams() throws Exception {
			Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
					.thenReturn(new ArrayList<FireStation>());
			mockMvc.perform(get(String.format("/phoneAlert?firestation=%s", nullValue()))).andExpect(status().isOk());

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
		}

		@Test
		public void phoneAlertURLTestIfNoParams() throws Exception {
			mockMvc.perform(get(String.format("/phoneAlert"))).andExpect(status().is(400));

			verify(fireStationService, Mockito.times(0)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
		}

		@Test
		public void phoneAlertURLTestIfNoFireStation() throws Exception {
			Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
					.thenReturn(new ArrayList<FireStation>());
			mockMvc.perform(get(String.format("/phoneAlert?firestation=%s", fireStation.getStation())))
					.andExpect(status().isOk());

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
		}

		@Test
		public void phoneAlertURLTestIfNoPerson() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
			mockMvc.perform(get(String.format("/phoneAlert?firestation=%s", fireStation.getStation())))
					.andExpect(status().isOk());

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
		}

		@Test
		public void phoneAlertURLTestIfNoPhone() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class)))
					.thenReturn(new ArrayList<Person>(Arrays.asList(new Person())));
			mockMvc.perform(get(String.format("/phoneAlert?firestation=%s", fireStation.getStation())))
					.andExpect(status().isOk());

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
		}
	}

	@Nested
	class FireURLTests {

		@Test
		public void fireURLTest() throws Exception {
			mockMvc.perform(get(String.format("/fire?address=%s", fireStation.getAddress()))).andExpect(status().isOk())

					.andExpect(jsonPath("persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("persons.[0].phone", is(person.getPhone())))
					.andExpect(jsonPath("persons.[0].age", is(medicalRecord.calculateAge())))
					.andExpect(jsonPath("persons.[0].medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("persons.[0].allergies", is(medicalRecord.getAllergies())))

					.andExpect(jsonPath("persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("persons.[1].phone", is(personChild.getPhone())))
					.andExpect(jsonPath("persons.[1].age", is(medicalRecordChild.calculateAge())))
					.andExpect(jsonPath("persons.[1].medications", is(medicalRecordChild.getMedications())))
					.andExpect(jsonPath("persons.[1].allergies", is(medicalRecordChild.getAllergies())))

					.andExpect(jsonPath("fireStationNumber", is(fireStation.getStation())));

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(numberOfPersons)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void fireURLTestIfEmptyParams() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
			Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
					.thenReturn(new ArrayList<FireStation>());
			mockMvc.perform(get(String.format("/fire?address=%s", nullValue()))).andExpect(status().isOk());

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void fireURLTestIfNoParams() throws Exception {
			mockMvc.perform(get(String.format("/fire", fireStation.getAddress()))).andExpect(status().is(400));

			verify(fireStationService, Mockito.times(0)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void fireURLTestIfNoFireStation() throws Exception {
			Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
					.thenReturn(new ArrayList<FireStation>());
			mockMvc.perform(get(String.format("/fire?address=%s", fireStation.getAddress()))).andExpect(status().isOk())

					.andExpect(jsonPath("persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("persons.[0].phone", is(person.getPhone())))
					.andExpect(jsonPath("persons.[0].age", is(medicalRecord.calculateAge())))
					.andExpect(jsonPath("persons.[0].medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("persons.[0].allergies", is(medicalRecord.getAllergies())))

					.andExpect(jsonPath("persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("persons.[1].phone", is(personChild.getPhone())))
					.andExpect(jsonPath("persons.[1].age", is(medicalRecordChild.calculateAge())))
					.andExpect(jsonPath("persons.[1].medications", is(medicalRecordChild.getMedications())))
					.andExpect(jsonPath("persons.[1].allergies", is(medicalRecordChild.getAllergies())));

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(numberOfPersons)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void fireURLTestIfNoPerson() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
			mockMvc.perform(get(String.format("/fire?address=%s", fireStation.getAddress()))).andExpect(status().isOk())

					.andExpect(jsonPath("fireStationNumber", is(fireStation.getStation())));

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void fireURLTestIfNoMedicalRecord() throws Exception {
			Mockito.when(medicalRecordService.getMedicalRecord(any(Person.class))).thenReturn(new MedicalRecord());
			mockMvc.perform(get(String.format("/fire?address=%s", fireStation.getAddress()))).andExpect(status().isOk())

					.andExpect(jsonPath("persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("persons.[0].phone", is(person.getPhone())))
					.andExpect(jsonPath("persons.[0].age", is(ageIfNoBirthdate)))
					.andExpect(jsonPath("persons.[0].medications", nullValue()))
					.andExpect(jsonPath("persons.[0].allergies", nullValue()))

					.andExpect(jsonPath("persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("persons.[1].phone", is(personChild.getPhone())))
					.andExpect(jsonPath("persons.[1].age", is(ageIfNoBirthdate)))
					.andExpect(jsonPath("persons.[1].medications", nullValue()))
					.andExpect(jsonPath("persons.[1].allergies", nullValue()))

					.andExpect(jsonPath("fireStationNumber", is(fireStation.getStation())));

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(numberOfPersons)).getMedicalRecord(any(Person.class));
		}
	}

	@Nested
	class FloodStationsURLTests {

		@Test
		public void floodStationsURLTest() throws Exception {
			mockMvc.perform(get(String.format("/flood/stations?stations=%s", fireStationStationsList)))
					.andExpect(status().isOk())

					.andExpect(jsonPath("homes.[0].persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("homes.[0].persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("homes.[0].persons.[0].phone", is(person.getPhone())))
					.andExpect(jsonPath("homes.[0].persons.[0].age", is(medicalRecord.calculateAge())))
					.andExpect(jsonPath("homes.[0].persons.[0].medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("homes.[0].persons.[0].allergies", is(medicalRecord.getAllergies())))

					.andExpect(jsonPath("homes.[0].persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("homes.[0].persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("homes.[0].persons.[1].phone", is(personChild.getPhone())))
					.andExpect(jsonPath("homes.[0].persons.[1].age", is(medicalRecordChild.calculateAge())))
					.andExpect(jsonPath("homes.[0].persons.[1].medications", is(medicalRecordChild.getMedications())))
					.andExpect(jsonPath("homes.[0].persons.[1].allergies", is(medicalRecordChild.getAllergies())))

					.andExpect(jsonPath("homes.[0].fireStationNumber", is(fireStation.getStation())))

					.andExpect(jsonPath("homes.[1].persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("homes.[1].persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("homes.[1].persons.[0].phone", is(person.getPhone())))
					.andExpect(jsonPath("homes.[1].persons.[0].age", is(medicalRecord.calculateAge())))
					.andExpect(jsonPath("homes.[1].persons.[0].medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("homes.[1].persons.[0].allergies", is(medicalRecord.getAllergies())))

					.andExpect(jsonPath("homes.[1].persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("homes.[1].persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("homes.[1].persons.[1].phone", is(personChild.getPhone())))
					.andExpect(jsonPath("homes.[1].persons.[1].age", is(medicalRecordChild.calculateAge())))
					.andExpect(jsonPath("homes.[1].persons.[1].medications", is(medicalRecordChild.getMedications())))
					.andExpect(jsonPath("homes.[1].persons.[1].allergies", is(medicalRecordChild.getAllergies())))

					.andExpect(jsonPath("homes.[1].fireStationNumber", is(fireStation.getStation())));

			verify(fireStationService, Mockito.times(fireStationStationsList.size()))
					.getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(fireStationStationsList.size())).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(fireStationStationsList.size() * (numberOfPersons)))
					.getMedicalRecord(any(Person.class));
		}

		@Test
		public void floodStationsURLTestIfEmptyParams() throws Exception {
			Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
					.thenReturn(new ArrayList<FireStation>());
			mockMvc.perform(get(String.format("/flood/stations?stations=%s", nullValue()))).andExpect(status().isOk());

			verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void floodStationsURLTestIfNoParams() throws Exception {
			mockMvc.perform(get(String.format("/flood/stations"))).andExpect(status().is(400));

			verify(fireStationService, Mockito.times(0)).getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void floodStationsURLTestIfNoFireStation() throws Exception {
			Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
					.thenReturn(new ArrayList<FireStation>());
			mockMvc.perform(get(String.format("/flood/stations?stations=%s", fireStationStationsList)))
					.andExpect(status().isOk());

			verify(fireStationService, Mockito.times(fireStationStationsList.size()))
					.getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void floodStationsURLTestIfNoPerson() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
			mockMvc.perform(get(String.format("/flood/stations?stations=%s", fireStationStationsList)))
					.andExpect(status().isOk())

					.andExpect(jsonPath("homes.[0].fireStationNumber", is(fireStation.getStation())))

					.andExpect(jsonPath("homes.[1].fireStationNumber", is(fireStation.getStation())));

			verify(fireStationService, Mockito.times(fireStationStationsList.size()))
					.getFireStation(any(FireStation.class));
			verify(personService, Mockito.times(fireStationStationsList.size())).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void floodStationsURLTestIfNoMedicalRecord() throws Exception {
			Mockito.when(medicalRecordService.getMedicalRecord(any(Person.class))).thenReturn(new MedicalRecord());
			mockMvc.perform(get(String.format("/flood/stations?stations=%s", fireStationStationsList)))
					.andExpect(status().isOk())

					.andExpect(jsonPath("homes.[0].persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("homes.[0].persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("homes.[0].persons.[0].phone", is(person.getPhone())))
					.andExpect(jsonPath("homes.[0].persons.[0].age", is(ageIfNoBirthdate)))
					.andExpect(jsonPath("homes.[0].persons.[0].medications", nullValue()))
					.andExpect(jsonPath("homes.[0].persons.[0].allergies", nullValue()))

					.andExpect(jsonPath("homes.[0].persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("homes.[0].persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("homes.[0].persons.[1].phone", is(personChild.getPhone())))
					.andExpect(jsonPath("homes.[0].persons.[1].age", is(ageIfNoBirthdate)))
					.andExpect(jsonPath("homes.[0].persons.[1].medications", nullValue()))
					.andExpect(jsonPath("homes.[0].persons.[1].allergies", nullValue()))

					.andExpect(jsonPath("homes.[0].fireStationNumber", is(fireStation.getStation())))

					.andExpect(jsonPath("homes.[1].persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("homes.[1].persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("homes.[1].persons.[0].phone", is(person.getPhone())))
					.andExpect(jsonPath("homes.[1].persons.[0].age", is(ageIfNoBirthdate)))
					.andExpect(jsonPath("homes.[1].persons.[0].medications", nullValue()))
					.andExpect(jsonPath("homes.[1].persons.[0].allergies", nullValue()))

					.andExpect(jsonPath("homes.[1].persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("homes.[1].persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("homes.[1].persons.[1].phone", is(personChild.getPhone())))
					.andExpect(jsonPath("homes.[1].persons.[1].age", is(ageIfNoBirthdate)))
					.andExpect(jsonPath("homes.[1].persons.[1].medications", nullValue()))
					.andExpect(jsonPath("homes.[1].persons.[1].allergies", nullValue()))

					.andExpect(jsonPath("homes.[1].fireStationNumber", is(fireStation.getStation())));

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
			mockMvc.perform(get(
					String.format("/personInfo?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
					.andExpect(status().isOk())

					.andExpect(jsonPath("persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("persons.[0].age", is(medicalRecord.calculateAge())))
					.andExpect(jsonPath("persons.[0].address", is(person.getAddress())))
					.andExpect(jsonPath("persons.[0].medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("persons.[0].allergies", is(medicalRecord.getAllergies())))

					.andExpect(jsonPath("persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("persons.[1].age", is(medicalRecordChild.calculateAge())))
					.andExpect(jsonPath("persons.[1].address", is(personChild.getAddress())))
					.andExpect(jsonPath("persons.[1].medications", is(medicalRecordChild.getMedications())))
					.andExpect(jsonPath("persons.[1].allergies", is(medicalRecordChild.getAllergies())));

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(numberOfPersons)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void personInfoURLTestIfEmptyParams() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
			mockMvc.perform(get(String.format("/personInfo?firstName=%s&lastName=%s", nullValue(), nullValue())))
					.andExpect(status().isOk());

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void personInfoURLTestIfNoParams() throws Exception {
			mockMvc.perform(get(String.format("/personInfo"))).andExpect(status().is(400));

			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void personInfoURLTestIfNoPerson() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
			mockMvc.perform(get(
					String.format("/personInfo?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
					.andExpect(status().isOk());

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
		}

		@Test
		public void personInfoURLTestIfNoMedicalRecord() throws Exception {
			Mockito.when(medicalRecordService.getMedicalRecord(any(Person.class))).thenReturn(new MedicalRecord());
			mockMvc.perform(get(
					String.format("/personInfo?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
					.andExpect(status().isOk())

					.andExpect(jsonPath("persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("persons.[0].age", is(ageIfNoBirthdate)))
					.andExpect(jsonPath("persons.[0].address", is(person.getAddress())))
					.andExpect(jsonPath("persons.[0].medications", nullValue()))
					.andExpect(jsonPath("persons.[0].allergies", nullValue()));

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
			verify(medicalRecordService, Mockito.times(numberOfPersons)).getMedicalRecord(any(Person.class));
		}
	}

	@Nested
	class CommunityEmailURLTests {

		@Test
		public void communityEmailURLTest() throws Exception {
			mockMvc.perform(get(String.format("/communityEmail?city=%s", person.getCity()))).andExpect(status().isOk())

					.andExpect(jsonPath("emails.[0]", is(person.getEmail())))

					.andExpect(jsonPath("emails.[1]", is(personChild.getEmail())));

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		}

		@Test
		public void communityEmailURLTestIfEmptyParams() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
			mockMvc.perform(get(String.format("/communityEmail?city=%s", nullValue()))).andExpect(status().isOk());

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		}

		@Test
		public void communityEmailURLTestIfNoParams() throws Exception {
			mockMvc.perform(get(String.format("/communityEmail"))).andExpect(status().is(400));

			verify(personService, Mockito.times(0)).getPerson(any(Person.class));
		}

		@Test
		public void communityEmailURLTestIfNoPerson() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
			mockMvc.perform(get(String.format("/communityEmail?city=%s", person.getCity()))).andExpect(status().isOk());

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		}

		@Test
		public void communityEmailURLTestIfNoEmail() throws Exception {
			Mockito.when(personService.getPerson(any(Person.class))).thenReturn(
					new ArrayList<Person>(Arrays.asList(new Person(person.getFirstName(), person.getLastName()))));
			mockMvc.perform(get(String.format("/communityEmail?city=%s", person.getCity()))).andExpect(status().isOk());

			verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		}
	}
}
