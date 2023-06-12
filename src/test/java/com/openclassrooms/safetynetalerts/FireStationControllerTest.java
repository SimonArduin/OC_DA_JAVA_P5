package com.openclassrooms.safetynetalerts;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

	static FireStation fireStation = new FireStation("1509 Culver St", "3");
	static FireStation fireStationOtherAddress = new FireStation("wrong address", fireStation.getStation());
	static FireStation fireStationOtherStationNumber = new FireStation(fireStation.getAddress(), "421");
	static Person person = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
			"jaboyd@email.com");
	static Person personChild = new Person("Young", "Childington", "1509 Culver St", "Culver", "97451", "841-874-6512",
			"ychild@email.com");
	static MedicalRecord medicalRecord = new MedicalRecord("John", "Boyd", "03/06/1984",
			new ArrayList<String>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
			new ArrayList<String>(Arrays.asList("nillacilan")));
	static MedicalRecord medicalRecordChild = new MedicalRecord("Young", "Childington", "03/06/2014",
			new ArrayList<String>(), new ArrayList<String>());
	static MedicalRecord medicalRecordOnlyName = new MedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());
	static MedicalRecord medicalRecordChildOnlyName = new MedicalRecord(medicalRecordChild.getFirstName(), medicalRecordChild.getLastName());
	static int numberOfAdults = 1;
	static int numberOfChildren = 1;
	static int numberOfFireStationByStationNumber = 1;

	@BeforeEach
	private void setUp() {
		Mockito.when(fireStationService.deleteFireStation(any(FireStation.class)))
				.thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		Mockito.when(fireStationService.getFireStation(any(FireStation.class)))
				.thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		Mockito.when(fireStationService.putFireStation(any(FireStation.class))).thenReturn(fireStation);
		Mockito.when(fireStationService.postFireStation(any(FireStation.class))).thenReturn(fireStation);
		Mockito.when(fireStationService.getAllFireStations())
				.thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		Mockito.when(personService.getPerson(any(Person.class)))
				.thenReturn(new ArrayList<Person>(Arrays.asList(person, personChild)));
		Mockito.when(medicalRecordService.getMedicalRecord(medicalRecordOnlyName))
				.thenReturn(medicalRecord);
		Mockito.when(medicalRecordService.getMedicalRecord(medicalRecordChildOnlyName))
				.thenReturn(medicalRecordChild);
	}

	@Test
	void contextLoads() {
	}

	/**
	 * Read - Get info on residents covered by a certain fire station or get all
	 * fire stations
	 * 
	 * @param - An int corresponding to the fire station number
	 * @return - An Iterable object of info on residents or of FireStation full
	 *         filled
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
		verify(medicalRecordService, Mockito.times(1)).getMedicalRecord(medicalRecordOnlyName);
		verify(medicalRecordService, Mockito.times(1)).getMedicalRecord(medicalRecordChildOnlyName);
	}

	@Test
	public void getFireStationTest() throws Exception {
		mockMvc.perform(get("/firestation")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
				.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
		verify(fireStationService, Mockito.times(1)).getAllFireStations();
	}

	/**
	 * Put - Changes the station number of a firestation in the database
	 * 
	 * @param - A String corresponding to the address of the fire station and an
	 *          optional int corresponding to the new fire station number
	 */
	@Test
	public void putFireStationTest() throws Exception {
		mockMvc.perform(put(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
				fireStation.getStation()))).andExpect(status().isOk())
				.andExpect(jsonPath("address", is(fireStation.getAddress())))
				.andExpect(jsonPath("station", is(fireStation.getStation())));
		verify(fireStationService, Mockito.times(1)).putFireStation(any(FireStation.class));
	}

	@Test
	public void putFireStationTestIfError() throws Exception {
		Mockito.when(fireStationService.putFireStation(any(FireStation.class))).thenReturn(new FireStation());
		mockMvc.perform(put(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
				fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("address", nullValue()))
				.andExpect(jsonPath("station", nullValue()));
		verify(fireStationService, Mockito.times(1)).putFireStation(any(FireStation.class));
	}

	/**
	 * Post - Adds a new firestation to the database
	 * 
	 * @param - A String corresponding to the address of the fire station and an int
	 *          corresponding to the fire station number
	 */
	@Test
	public void postFireStation() throws Exception {
		mockMvc.perform(post(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
				fireStation.getStation()))).andExpect(status().isOk())
				.andExpect(jsonPath("address", is(fireStation.getAddress())))
				.andExpect(jsonPath("station", is(fireStation.getStation())));
		verify(fireStationService, Mockito.times(1)).postFireStation(any(FireStation.class));
	}

	@Test
	public void postFireStationTestIfError() throws Exception {
		Mockito.when(fireStationService.postFireStation(any(FireStation.class))).thenReturn(new FireStation());
		mockMvc.perform(post(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
				fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("address", nullValue()))
				.andExpect(jsonPath("station", nullValue()));
		verify(fireStationService, Mockito.times(1)).postFireStation(any(FireStation.class));
	}

	/**
	 * Delete - Removes a firestation from the database
	 * 
	 * @param - An optional String corresponding to the address of the fire station
	 *          and an optional int corresponding to the new fire station number
	 */

	@Test
	public void deleteFireStation() throws Exception {
		mockMvc.perform(delete(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
				fireStation.getStation()))).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
				.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
		verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
	}

	@Test
	public void deleteFireStationIfError() throws Exception {
		Mockito.when(fireStationService.deleteFireStation(any(FireStation.class)))
				.thenReturn(new ArrayList<FireStation>(Arrays.asList(new FireStation())));
		mockMvc.perform(delete(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
				fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("$[0].address", nullValue()))
				.andExpect(jsonPath("$[0].station", nullValue()));
		verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
	}

	@Test
	public void deleteFireStationByAddress() throws Exception {
		mockMvc.perform(delete(String.format("/firestation?address=%s", fireStation.getAddress())))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
				.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
		verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
	}

	@Test
	public void deleteFireStationByAddressIfError() throws Exception {
		Mockito.when(fireStationService.deleteFireStation(any(FireStation.class)))
		.thenReturn(new ArrayList<FireStation>(Arrays.asList(new FireStation())));
		mockMvc.perform(delete(String.format("/firestation?address=%s", fireStation.getAddress())))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].address", nullValue()))
				.andExpect(jsonPath("$[0].station", nullValue()));
		verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
	}

	@Test
	public void deleteFireStationByStation() throws Exception {
		mockMvc.perform(delete(String.format("/firestation?stationNumber=%s", fireStation.getStation())))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
				.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
		verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
	}

	@Test
	public void deleteFireStationByStationIfError() throws Exception {
		Mockito.when(fireStationService.deleteFireStation(any(FireStation.class)))
		.thenReturn(new ArrayList<FireStation>(Arrays.asList(new FireStation())));
		mockMvc.perform(delete(String.format("/firestation?stationNumber=%s", fireStation.getStation())))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].address", nullValue()))
				.andExpect(jsonPath("$[0].station", nullValue()));
		verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
	}

	@Test
	public void deleteFireStationIfNoParam() throws Exception {
		Mockito.when(fireStationService.deleteFireStation(any(FireStation.class)))
		.thenReturn(new ArrayList<FireStation>(Arrays.asList(new FireStation())));
		mockMvc.perform(delete(String.format("/firestation"))).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].address", nullValue())).andExpect(jsonPath("$[0].station", nullValue()));
		verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
	}
}
