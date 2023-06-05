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
	
	static FireStation fireStation = new FireStation("1509 Culver St", 3);
	static FireStation fireStationOtherAddress = new FireStation("wrong address", fireStation.getStation());
	static FireStation fireStationOtherStationNumber = new FireStation(fireStation.getAddress(), 421);

	@BeforeEach
	private void setUp() {
		Mockito.when(fireStationService.deleteFireStation(any(FireStation.class))).thenReturn(true);
		Mockito.when(fireStationService.deleteFireStation(anyString())).thenReturn(true);
		Mockito.when(fireStationService.deleteFireStation(anyInt())).thenReturn(true);
		Mockito.when(fireStationService.getFireStation(any(FireStation.class))).thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		Mockito.when(fireStationService.putFireStation(any(FireStation.class))).thenReturn(true);
		Mockito.when(fireStationService.postFireStation(any(FireStation.class))).thenReturn(true);
		Mockito.when(fireStationService.getAllFireStations()).thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
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
	public void getFireStationTestIfNoParam() throws Exception {
		mockMvc.perform(get("/firestation")).andExpect(status().isOk()).andExpect(jsonPath("$[0].address", is(fireStation.getAddress()))).andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
		verify(fireStationService, Mockito.times(1)).getAllFireStations();
	}

		/*
		ArrayList<Object> result = new ArrayList<Object>();
		int numberOfAdults = 0;
		int numberOfChildren = 0;
		if (stationNumber.isPresent()) {
			ArrayList<FireStation> fireStations = new ArrayList<FireStation>(
					fireStationService.getFireStation(stationNumber.get()));
			for (FireStation fireStation : fireStations) {
				ArrayList<Person> persons = new ArrayList<Person>(
						personService.getPersonByAddress(fireStation.getAddress()));
				for (Person person : persons) {
					result.add(new String((String.format(
							"\"firstName\":\"%s\",\"lastName\":\"%s\",\"address\":\"%s\",\"phone\":\"%s\"",
							person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))));
					// get medical record of the patient
					MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByName(person.getFirstName(),
							person.getLastName());
					LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(),
							DateTimeFormatter.ofPattern("MM/dd/yyyy"));
					if (Period.between(birthdate, LocalDate.now()).getYears() > 18)
						numberOfAdults++;
					else
						numberOfChildren++;
				}
			}
			result.add(new String((String.format("\"numberofadults\":\"%s\",\"numberofchildren\":\"%s\"",
					numberOfAdults, numberOfChildren))));
		}

		else {
			result = new ArrayList<Object>(fireStationService.getAllFireStations());
		}
		return result;
		*/

	/**
	 * Put - Changes the station number of a firestation in the database
	 * 
	 * @param - A String corresponding to the address of the fire station and an
	 *          optional int corresponding to the new fire station number
	 */
	@Test
	public void putFireStationTest() throws Exception {
		mockMvc.perform(put(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(), fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("address", is(fireStation.getAddress()))).andExpect(jsonPath("station", is(fireStation.getStation())));
		verify(fireStationService, Mockito.times(1)).putFireStation(any(FireStation.class));
	}
	
	@Test
	public void putFireStationTestIfError() throws Exception {
		Mockito.when(fireStationService.putFireStation(any(FireStation.class))).thenReturn(false);
		mockMvc.perform(put(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(), fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("address", nullValue())).andExpect(jsonPath("station", is(0)));
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
		mockMvc.perform(post(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(), fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("address", is(fireStation.getAddress()))).andExpect(jsonPath("station", is(fireStation.getStation())));
		verify(fireStationService, Mockito.times(1)).postFireStation(any(FireStation.class));
	}
	
	@Test
	public void postFireStationTestIfError() throws Exception {
		Mockito.when(fireStationService.postFireStation(any(FireStation.class))).thenReturn(false);
		mockMvc.perform(post(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(), fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("address", nullValue())).andExpect(jsonPath("station", is(0)));
		verify(fireStationService, Mockito.times(1)).postFireStation(any(FireStation.class));
	}

	/**
	 * Delete - Removes a firestation from the database
	 * 
	 * @param - An optional String corresponding to the address of the fire station
	 *          and an optional int corresponding to the new fire station number
	 */
	
	@Test
	public void deleteFireStationByFireStation() throws Exception {
		mockMvc.perform(delete(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(), fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("address", is(fireStation.getAddress()))).andExpect(jsonPath("station", is(fireStation.getStation())));
		verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
		verify(fireStationService, Mockito.times(0)).deleteFireStation(anyString());
		verify(fireStationService, Mockito.times(0)).deleteFireStation(anyInt());
	}

	@Test
	public void deleteFireStationByFireStationIfError() throws Exception {
		Mockito.when(fireStationService.deleteFireStation(any(FireStation.class))).thenReturn(false);
		mockMvc.perform(delete(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(), fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("address", nullValue())).andExpect(jsonPath("station", is(0)));
		verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
		verify(fireStationService, Mockito.times(0)).deleteFireStation(anyString());
		verify(fireStationService, Mockito.times(0)).deleteFireStation(anyInt());
	}
	
	@Test
	public void deleteFireStationByAddress() throws Exception {
		mockMvc.perform(delete(String.format("/firestation?address=%s", fireStation.getAddress()))).andExpect(status().isOk()).andExpect(jsonPath("address", is(fireStation.getAddress()))).andExpect(jsonPath("station", is(0)));
		verify(fireStationService, Mockito.times(0)).deleteFireStation(any(FireStation.class));
		verify(fireStationService, Mockito.times(1)).deleteFireStation(anyString());
		verify(fireStationService, Mockito.times(0)).deleteFireStation(anyInt());
	}

	@Test
	public void deleteFireStationByAddressIfError() throws Exception {
		Mockito.when(fireStationService.deleteFireStation(anyString())).thenReturn(false);
		mockMvc.perform(delete(String.format("/firestation?address=%s", fireStation.getAddress()))).andExpect(status().isOk()).andExpect(jsonPath("address", nullValue())).andExpect(jsonPath("station", is(0)));
		verify(fireStationService, Mockito.times(0)).deleteFireStation(any(FireStation.class));
		verify(fireStationService, Mockito.times(1)).deleteFireStation(anyString());
		verify(fireStationService, Mockito.times(0)).deleteFireStation(anyInt());
	}

	
	@Test
	public void deleteFireStationByStation() throws Exception {
		mockMvc.perform(delete(String.format("/firestation?stationNumber=%s", fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("address", nullValue())).andExpect(jsonPath("station", is(fireStation.getStation())));
		verify(fireStationService, Mockito.times(0)).deleteFireStation(any(FireStation.class));
		verify(fireStationService, Mockito.times(0)).deleteFireStation(anyString());
		verify(fireStationService, Mockito.times(1)).deleteFireStation(anyInt());
	}
	
	@Test
	public void deleteFireStationByStationIfError() throws Exception {
		Mockito.when(fireStationService.deleteFireStation(anyInt())).thenReturn(false);
		mockMvc.perform(delete(String.format("/firestation?stationNumber=%s", fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("address", nullValue())).andExpect(jsonPath("station", is(0)));
		verify(fireStationService, Mockito.times(0)).deleteFireStation(any(FireStation.class));
		verify(fireStationService, Mockito.times(0)).deleteFireStation(anyString());
		verify(fireStationService, Mockito.times(1)).deleteFireStation(anyInt());
	}
	
	@Test
	public void deleteFireStationIfNoParam() throws Exception {
		mockMvc.perform(delete(String.format("/firestation"))).andExpect(status().isOk()).andExpect(jsonPath("address", nullValue())).andExpect(jsonPath("station", is(0)));
		verify(fireStationService, Mockito.times(0)).deleteFireStation(any(FireStation.class));
		verify(fireStationService, Mockito.times(0)).deleteFireStation(anyString());
		verify(fireStationService, Mockito.times(0)).deleteFireStation(anyInt());
	}
}
