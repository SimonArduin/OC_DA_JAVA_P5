package com.openclassrooms.safetynetalerts;

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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.safetynetalerts.controller.UrlController;
import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.FireStationService;
import com.openclassrooms.safetynetalerts.service.MedicalRecordService;
import com.openclassrooms.safetynetalerts.service.PersonService;

@WebMvcTest(controllers = UrlController.class)
public class UrlControllerTest {

	@Autowired
	UrlController urlController;

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

	/**
	 * Read - Get info on residents covered by a certain fire station or get all
	 * fire stations
	 * 
	 * @param - An int corresponding to the fire station number
	 * @return - A FireStationURLInfo object
	 */

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

	/**
	 * Read - Get info on children living at a certain address and a list of all
	 * adults living with them
	 * 
	 * @param - A String corresponding to the address
	 * @return - A ChildAlertURLInfo object
	 */

	@Test
	public void ChildAlertURLTest() throws Exception {
		mockMvc.perform(get(String.format("/childAlert?address=%s", personChild.getAddress())))
				.andExpect(status().isOk())

				.andExpect(jsonPath("children.[0].firstName", is(personChild.getFirstName())))
				.andExpect(jsonPath("children.[0].lastName", is(personChild.getLastName())))
				.andExpect(jsonPath("children.[0].age", is(medicalRecordChild.getAge())))

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
	public void ChildAlertURLTestIfNoPerson() throws Exception {
		Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
		mockMvc.perform(get(String.format("/childAlert?address=%s", personChild.getAddress())))
				.andExpect(status().isOk());

		verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(person);
		verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(personChild);
	}

	@Test
	public void ChildAlertURLTestIfNoMedicalRecord() throws Exception {
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
	public void ChildAlertURLTestIfNoBirthdate() throws Exception {
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

	/**
	 * Read - Get the phone numbers of every person corresponding to the station
	 * number
	 * 
	 * @param - A String corresponding to the station number
	 * @return - A List<String> containing phone numbers
	 */

	@Test
	public void PhoneAlertURLTest() throws Exception {
		mockMvc.perform(get(String.format("/phoneAlert?firestation=%s", fireStation.getStation())))
				.andExpect(status().isOk())

				.andExpect(jsonPath("[0]", is(person.getPhone())))

				.andExpect(jsonPath("[1]", is(personChild.getPhone())));

		verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
		verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
	}

	@Test
	public void PhoneAlertURLTestIfNoFireStation() throws Exception {
		Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
				.thenReturn(new ArrayList<FireStation>());
		mockMvc.perform(get(String.format("/phoneAlert?firestation=%s", fireStation.getStation())))
				.andExpect(status().isOk());

		verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
		verify(personService, Mockito.times(0)).getPerson(any(Person.class));
	}

	@Test
	public void PhoneAlertURLTestIfNoPerson() throws Exception {
		Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
		mockMvc.perform(get(String.format("/phoneAlert?firestation=%s", fireStation.getStation())))
				.andExpect(status().isOk());

		verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
		verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
	}

	@Test
	public void PhoneAlertURLTestIfNoPhone() throws Exception {
		Mockito.when(personService.getPerson(any(Person.class)))
				.thenReturn(new ArrayList<Person>(Arrays.asList(new Person())));
		mockMvc.perform(get(String.format("/phoneAlert?firestation=%s", fireStation.getStation())))
				.andExpect(status().isOk());

		verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
		verify(personService, Mockito.times(numberOfFireStationByStationNumber)).getPerson(any(Person.class));
	}

	/**
	 * Read - Get info on residents of an address and the number of corresponding
	 * fire station
	 * 
	 * @param - A String corresponding to the address
	 * @return - A FireURLInfo object
	 */

	@Test
	public void FireURLTest() throws Exception {
		mockMvc.perform(get(String.format("/fire?address=%s", fireStation.getAddress()))).andExpect(status().isOk())

				.andExpect(jsonPath("persons.[0].firstName", is(person.getFirstName())))
				.andExpect(jsonPath("persons.[0].lastName", is(person.getLastName())))
				.andExpect(jsonPath("persons.[0].phone", is(person.getPhone())))
				.andExpect(jsonPath("persons.[0].age", is(medicalRecord.getAge())))
				.andExpect(jsonPath("persons.[0].medications", is(medicalRecord.getMedications())))
				.andExpect(jsonPath("persons.[0].allergies", is(medicalRecord.getAllergies())))

				.andExpect(jsonPath("persons.[1].firstName", is(personChild.getFirstName())))
				.andExpect(jsonPath("persons.[1].lastName", is(personChild.getLastName())))
				.andExpect(jsonPath("persons.[1].phone", is(personChild.getPhone())))
				.andExpect(jsonPath("persons.[1].age", is(medicalRecordChild.getAge())))
				.andExpect(jsonPath("persons.[1].medications", is(medicalRecordChild.getMedications())))
				.andExpect(jsonPath("persons.[1].allergies", is(medicalRecordChild.getAllergies())))

				.andExpect(jsonPath("fireStationNumber", is(fireStation.getStation())));

		verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
		verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		verify(medicalRecordService, Mockito.times(numberOfAdults + numberOfChildren))
				.getMedicalRecord(any(Person.class));
	}

	@Test
	public void FireURLTestIfNoFireStation() throws Exception {
		Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
				.thenReturn(new ArrayList<FireStation>());
		mockMvc.perform(get(String.format("/fire?address=%s", fireStation.getAddress()))).andExpect(status().isOk())

				.andExpect(jsonPath("persons.[0].firstName", is(person.getFirstName())))
				.andExpect(jsonPath("persons.[0].lastName", is(person.getLastName())))
				.andExpect(jsonPath("persons.[0].phone", is(person.getPhone())))
				.andExpect(jsonPath("persons.[0].age", is(medicalRecord.getAge())))
				.andExpect(jsonPath("persons.[0].medications", is(medicalRecord.getMedications())))
				.andExpect(jsonPath("persons.[0].allergies", is(medicalRecord.getAllergies())))

				.andExpect(jsonPath("persons.[1].firstName", is(personChild.getFirstName())))
				.andExpect(jsonPath("persons.[1].lastName", is(personChild.getLastName())))
				.andExpect(jsonPath("persons.[1].phone", is(personChild.getPhone())))
				.andExpect(jsonPath("persons.[1].age", is(medicalRecordChild.getAge())))
				.andExpect(jsonPath("persons.[1].medications", is(medicalRecordChild.getMedications())))
				.andExpect(jsonPath("persons.[1].allergies", is(medicalRecordChild.getAllergies())));

		verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
		verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		verify(medicalRecordService, Mockito.times(numberOfAdults + numberOfChildren))
				.getMedicalRecord(any(Person.class));
	}

	@Test
	public void FireURLTestIfNoPerson() throws Exception {
		Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
		mockMvc.perform(get(String.format("/fire?address=%s", fireStation.getAddress()))).andExpect(status().isOk())

				.andExpect(jsonPath("fireStationNumber", is(fireStation.getStation())));

		verify(fireStationService, Mockito.times(1)).getFireStation(any(FireStation.class));
		verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
	}

	@Test
	public void FireURLTestIfNoMedicalRecord() throws Exception {
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
		verify(medicalRecordService, Mockito.times(numberOfAdults + numberOfChildren))
				.getMedicalRecord(any(Person.class));
	}

	/**
	 * Read - Get a list of residents corresponding to a list of fire stations
	 * 
	 * @param - A List<String> corresponding to the station numbers
	 * @return - A FloodStationsURLInfo object
	 */

	@Test
	public void FloodStationsURLTest() throws Exception {
		mockMvc.perform(get(String.format("/flood/stations?stations=%s", fireStationStationsList)))
				.andExpect(status().isOk())

				.andExpect(jsonPath("[0].persons.[0].firstName", is(person.getFirstName())))
				.andExpect(jsonPath("[0].persons.[0].lastName", is(person.getLastName())))
				.andExpect(jsonPath("[0].persons.[0].phone", is(person.getPhone())))
				.andExpect(jsonPath("[0].persons.[0].age", is(medicalRecord.getAge())))
				.andExpect(jsonPath("[0].persons.[0].medications", is(medicalRecord.getMedications())))
				.andExpect(jsonPath("[0].persons.[0].allergies", is(medicalRecord.getAllergies())))

				.andExpect(jsonPath("[0].persons.[1].firstName", is(personChild.getFirstName())))
				.andExpect(jsonPath("[0].persons.[1].lastName", is(personChild.getLastName())))
				.andExpect(jsonPath("[0].persons.[1].phone", is(personChild.getPhone())))
				.andExpect(jsonPath("[0].persons.[1].age", is(medicalRecordChild.getAge())))
				.andExpect(jsonPath("[0].persons.[1].medications", is(medicalRecordChild.getMedications())))
				.andExpect(jsonPath("[0].persons.[1].allergies", is(medicalRecordChild.getAllergies())))

				.andExpect(jsonPath("[0].fireStationNumber", is(fireStation.getStation())))

				.andExpect(jsonPath("[1].persons.[0].firstName", is(person.getFirstName())))
				.andExpect(jsonPath("[1].persons.[0].lastName", is(person.getLastName())))
				.andExpect(jsonPath("[1].persons.[0].phone", is(person.getPhone())))
				.andExpect(jsonPath("[1].persons.[0].age", is(medicalRecord.getAge())))
				.andExpect(jsonPath("[1].persons.[0].medications", is(medicalRecord.getMedications())))
				.andExpect(jsonPath("[1].persons.[0].allergies", is(medicalRecord.getAllergies())))

				.andExpect(jsonPath("[1].persons.[1].firstName", is(personChild.getFirstName())))
				.andExpect(jsonPath("[1].persons.[1].lastName", is(personChild.getLastName())))
				.andExpect(jsonPath("[1].persons.[1].phone", is(personChild.getPhone())))
				.andExpect(jsonPath("[1].persons.[1].age", is(medicalRecordChild.getAge())))
				.andExpect(jsonPath("[1].persons.[1].medications", is(medicalRecordChild.getMedications())))
				.andExpect(jsonPath("[1].persons.[1].allergies", is(medicalRecordChild.getAllergies())))

				.andExpect(jsonPath("[1].fireStationNumber", is(fireStation.getStation())));

		verify(fireStationService, Mockito.times(fireStationStationsList.size()))
				.getFireStation(any(FireStation.class));
		verify(personService, Mockito.times(fireStationStationsList.size())).getPerson(any(Person.class));
		verify(medicalRecordService,
				Mockito.times(fireStationStationsList.size() * (numberOfAdults + numberOfChildren)))
				.getMedicalRecord(any(Person.class));
	}

	@Test
	public void FloodStationsURLTestIfNoFireStation() throws Exception {
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
	public void FloodStationsURLTestIfNoPerson() throws Exception {
		Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
		mockMvc.perform(get(String.format("/flood/stations?stations=%s", fireStationStationsList)))
				.andExpect(status().isOk())

				.andExpect(jsonPath("[0].fireStationNumber", is(fireStation.getStation())))

				.andExpect(jsonPath("[1].fireStationNumber", is(fireStation.getStation())));

		verify(fireStationService, Mockito.times(fireStationStationsList.size()))
				.getFireStation(any(FireStation.class));
		verify(personService, Mockito.times(fireStationStationsList.size())).getPerson(any(Person.class));
		verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
	}

	@Test
	public void FloodStationsURLTestIfNoMedicalRecord() throws Exception {
		Mockito.when(medicalRecordService.getMedicalRecord(any(Person.class))).thenReturn(new MedicalRecord());
		mockMvc.perform(get(String.format("/flood/stations?stations=%s", fireStationStationsList)))
				.andExpect(status().isOk())

				.andExpect(jsonPath("[0].persons.[0].firstName", is(person.getFirstName())))
				.andExpect(jsonPath("[0].persons.[0].lastName", is(person.getLastName())))
				.andExpect(jsonPath("[0].persons.[0].phone", is(person.getPhone())))
				.andExpect(jsonPath("[0].persons.[0].age", is(ageIfNoBirthdate)))
				.andExpect(jsonPath("[0].persons.[0].medications", nullValue()))
				.andExpect(jsonPath("[0].persons.[0].allergies", nullValue()))

				.andExpect(jsonPath("[0].persons.[1].firstName", is(personChild.getFirstName())))
				.andExpect(jsonPath("[0].persons.[1].lastName", is(personChild.getLastName())))
				.andExpect(jsonPath("[0].persons.[1].phone", is(personChild.getPhone())))
				.andExpect(jsonPath("[0].persons.[1].age", is(ageIfNoBirthdate)))
				.andExpect(jsonPath("[0].persons.[1].medications", nullValue()))
				.andExpect(jsonPath("[0].persons.[1].allergies", nullValue()))

				.andExpect(jsonPath("[0].fireStationNumber", is(fireStation.getStation())))

				.andExpect(jsonPath("[1].persons.[0].firstName", is(person.getFirstName())))
				.andExpect(jsonPath("[1].persons.[0].lastName", is(person.getLastName())))
				.andExpect(jsonPath("[1].persons.[0].phone", is(person.getPhone())))
				.andExpect(jsonPath("[1].persons.[0].age", is(ageIfNoBirthdate)))
				.andExpect(jsonPath("[1].persons.[0].medications", nullValue()))
				.andExpect(jsonPath("[1].persons.[0].allergies", nullValue()))

				.andExpect(jsonPath("[1].persons.[1].firstName", is(personChild.getFirstName())))
				.andExpect(jsonPath("[1].persons.[1].lastName", is(personChild.getLastName())))
				.andExpect(jsonPath("[1].persons.[1].phone", is(personChild.getPhone())))
				.andExpect(jsonPath("[1].persons.[1].age", is(ageIfNoBirthdate)))
				.andExpect(jsonPath("[1].persons.[1].medications", nullValue()))
				.andExpect(jsonPath("[1].persons.[1].allergies", nullValue()))

				.andExpect(jsonPath("[1].fireStationNumber", is(fireStation.getStation())));

		verify(fireStationService, Mockito.times(fireStationStationsList.size()))
				.getFireStation(any(FireStation.class));
		verify(personService, Mockito.times(fireStationStationsList.size())).getPerson(any(Person.class));
		verify(medicalRecordService,
				Mockito.times(fireStationStationsList.size() * (numberOfAdults + numberOfChildren)))
				.getMedicalRecord(any(Person.class));
	}

	/**
	 * Read - Get info on a person
	 * 
	 * @param - Two String corresponding to the first and last name of the person
	 * @return - A List<personInfoURLPerson> object
	 */
	
	@Test
	public void PersonInfoURLTest() throws Exception {
		Mockito.when(personService.getPerson(any(Person.class)))
				.thenReturn(new ArrayList<Person>(Arrays.asList(person, personChild)));
		mockMvc.perform(
				get(String.format("/personInfo?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
				.andExpect(status().isOk())

				.andExpect(jsonPath("[0].firstName", is(person.getFirstName())))
				.andExpect(jsonPath("[0].lastName", is(person.getLastName())))
				.andExpect(jsonPath("[0].age", is(medicalRecord.getAge())))
				.andExpect(jsonPath("[0].address", is(person.getAddress())))
				.andExpect(jsonPath("[0].medications", is(medicalRecord.getMedications())))
				.andExpect(jsonPath("[0].allergies", is(medicalRecord.getAllergies())))

				.andExpect(jsonPath("[1].firstName", is(personChild.getFirstName())))
				.andExpect(jsonPath("[1].lastName", is(personChild.getLastName())))
				.andExpect(jsonPath("[1].age", is(medicalRecordChild.getAge())))
				.andExpect(jsonPath("[1].address", is(personChild.getAddress())))
				.andExpect(jsonPath("[1].medications", is(medicalRecordChild.getMedications())))
				.andExpect(jsonPath("[1].allergies", is(medicalRecordChild.getAllergies())));

		verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		verify(medicalRecordService, Mockito.times(numberOfAdults + numberOfChildren)).getMedicalRecord(any(Person.class));
	}

	@Test
	public void PersonInfoURLTestIfNoPerson() throws Exception {
		Mockito.when(personService.getPerson(any(Person.class))).thenReturn(new ArrayList<Person>());
		mockMvc.perform(
				get(String.format("/personInfo?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
				.andExpect(status().isOk());

		verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		verify(medicalRecordService, Mockito.times(0)).getMedicalRecord(any(Person.class));
	}

	@Test
	public void PersonInfoURLTestIfNoMedicalRecord() throws Exception {
		Mockito.when(medicalRecordService.getMedicalRecord(any(Person.class))).thenReturn(new MedicalRecord());
		mockMvc.perform(
				get(String.format("/personInfo?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
				.andExpect(status().isOk())

				.andExpect(jsonPath("[0].firstName", is(person.getFirstName())))
				.andExpect(jsonPath("[0].lastName", is(person.getLastName())))
				.andExpect(jsonPath("[0].age", is(ageIfNoBirthdate)))
				.andExpect(jsonPath("[0].address", is(person.getAddress())))
				.andExpect(jsonPath("[0].medications", nullValue())).andExpect(jsonPath("[0].allergies", nullValue()));

		verify(personService, Mockito.times(1)).getPerson(any(Person.class));
		verify(medicalRecordService, Mockito.times(numberOfAdults + numberOfChildren)).getMedicalRecord(any(Person.class));
	}
}
