package com.openclassrooms.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.repository.FireStationRepository;
import com.openclassrooms.safetynetalerts.service.FireStationService;

@SpringBootTest(classes = FireStationService.class)
public class FireStationServiceTest {

	@Autowired
	FireStationService fireStationService;

	@MockBean
	FireStationRepository fireStationRepository;

	static FireStation fireStation = new FireStation("1509 Culver St", 3);
	static FireStation fireStationOtherAddress = new FireStation("wrong address", fireStation.getStation());
	static FireStation fireStationOtherStation = new FireStation(fireStation.getAddress(), 421);

	@BeforeEach
	private void setUp() {
		when(fireStationRepository.delete(any(FireStation.class))).thenReturn(fireStation);
		when(fireStationRepository.find(any(FireStation.class))).thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		when(fireStationRepository.findAll()).thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		when(fireStationRepository.findByAddress(anyString())).thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		when(fireStationRepository.findByStation(anyInt())).thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		when(fireStationRepository.save(any(FireStation.class))).thenReturn(fireStation);
	}

	@Test
	void contextLoads() {
	}

	/*
	 * Deletes every fire station with the specified address
	 * 
	 * @param - A String representing an address
	 * 
	 * @return - A boolean that is false if no fire station with the specified
	 * address is found
	 */

	@Test
	public void deleteFireStationTestByAddress() {
		assertEquals(new ArrayList<FireStation>(Arrays.asList(fireStation)), fireStationService.deleteFireStation(fireStation.getAddress()));
		verify(fireStationRepository, Mockito.times(1)).findByAddress(anyString());
		verify(fireStationRepository, Mockito.times(1)).delete(any(FireStation.class));
	}

	@Test
	public void deleteFireStationTestByAddressIfNotInDB() {
		when(fireStationRepository.findByAddress(anyString())).thenReturn(new ArrayList<FireStation>());
		assertFalse(fireStationService.deleteFireStation(fireStationOtherAddress.getAddress()).equals(new ArrayList<FireStation>(Arrays.asList(fireStation))));
		verify(fireStationRepository, Mockito.times(1)).findByAddress(anyString());
		verify(fireStationRepository, Mockito.times(0)).delete(any(FireStation.class));
	}

	/*
	 * Deletes every fire station with the specified station number
	 * 
	 * @param - An int representing a station number
	 * 
	 * @return - A boolean that is false if no fire station with the specified
	 * station number is found
	 */

	@Test
	public void deleteFireStationTestByStation() {
		assertEquals(new ArrayList<FireStation>(Arrays.asList(fireStation)), fireStationService.deleteFireStation(fireStation.getStation()));
		verify(fireStationRepository, Mockito.times(1)).findByStation(anyInt());
		verify(fireStationRepository, Mockito.times(1)).delete(any(FireStation.class));
	}

	@Test
	public void deleteFireStationTestByStationIfNotInDB() {
		when(fireStationRepository.findByStation(anyInt())).thenReturn(new ArrayList<FireStation>());
		assertFalse(fireStationService.deleteFireStation(fireStationOtherStation.getStation()).equals(new ArrayList<FireStation>(Arrays.asList(fireStation))));
		verify(fireStationRepository, Mockito.times(1)).findByStation(anyInt());
		verify(fireStationRepository, Mockito.times(0)).delete(any(FireStation.class));
	}

	/*
	 * Deletes every fire station corresponding to the specified fire station
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - A boolean that is false if the fire station is not found
	 */

	@Test
	public void deleteFireStationTestByFireStation() {
		assertEquals(new ArrayList<FireStation>(Arrays.asList(fireStation)), fireStationService.deleteFireStation(fireStation));
		verify(fireStationRepository, Mockito.times(1)).find(any(FireStation.class));
		verify(fireStationRepository, Mockito.times(1)).delete(any(FireStation.class));
	}

	@Test
	public void deleteFireStationTestByFireStationIfNotInDB() {
		when(fireStationRepository.find(any(FireStation.class))).thenReturn(new ArrayList<FireStation>());
		assertFalse(fireStationService.deleteFireStation(fireStation).equals(new ArrayList<FireStation>(Arrays.asList(fireStation))));
		verify(fireStationRepository, Mockito.times(1)).find(any(FireStation.class));
		verify(fireStationRepository, Mockito.times(0)).delete(any(FireStation.class));
	}

	/*
	 * Get every fire station corresponding to the specified address
	 * 
	 * @param - A String representing an adress
	 * 
	 * @return - An Iterable<FireStation>
	 */

	@Test
	public void getFireStationTestByAddress() {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationService.getFireStation(fireStation.getAddress()));
		boolean wrongAddress = false;
		for(FireStation fireStationInResult : result) {
			if(fireStationInResult.getAddress()!=fireStation.getAddress())
				wrongAddress = true;
		}
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertTrue(result.contains(fireStation));
		assertFalse(wrongAddress);
		verify(fireStationRepository, Mockito.times(1)).findByAddress(anyString());
	}

	@Test
	public void getFireStationTestByAddressIfNotInDB() {
		when(fireStationRepository.findByAddress(anyString())).thenReturn(new ArrayList<FireStation>());
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationService.getFireStation(fireStationOtherAddress.getAddress()));
		assertTrue(result.isEmpty());
		verify(fireStationRepository, Mockito.times(1)).findByAddress(anyString());
	}

	/*
	 * Get every fire station corresponding to the specified station number
	 * 
	 * @param - An int representing a station number
	 * 
	 * @return - An Iterable<FireStation>
	 */

	@Test
	public void getFireStationTestByStation() {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationService.getFireStation(fireStation.getStation()));
		boolean wrongStation = false;
		for(FireStation fireStationInResult : result) {
			if(!fireStationInResult.equals(fireStation))
				wrongStation = true;
		}
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertTrue(result.contains(fireStation));
		assertFalse(wrongStation);
		verify(fireStationRepository, Mockito.times(1)).findByStation(anyInt());
	}

	@Test
	public void getFireStationTestByStationIfNotInDB() {
		when(fireStationRepository.findByStation(anyInt())).thenReturn(new ArrayList<FireStation>());
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationService.getFireStation(fireStationOtherAddress.getStation()));
		assertTrue(result.isEmpty());
		verify(fireStationRepository, Mockito.times(1)).findByStation(anyInt());
	}

	/*
	 * Get every fire station corresponding to the specified fire station
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - An Iterable<FireStation>
	 */

	@Test
	public void getFireStationTestByFireStation() {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationService.getFireStation(fireStation));
		boolean wrongFireStation = false;
		for(FireStation fireStationInResult : result) {
			if(!fireStationInResult.equals(fireStation))
				wrongFireStation = true;
		}
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertTrue(result.contains(fireStation));
		assertFalse(wrongFireStation);
		verify(fireStationRepository, Mockito.times(1)).find(any(FireStation.class));
	}

	@Test
	public void getFireStationTestByFireStationIfNotInDB() {
		when(fireStationRepository.find(any(FireStation.class))).thenReturn(new ArrayList<FireStation>());
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationService.getFireStation(fireStationOtherAddress));
		assertTrue(result.isEmpty());
		verify(fireStationRepository, Mockito.times(1)).find(any(FireStation.class));
	}

	/*
	 * Get every fire stations
	 * 
	 * @return - An Iterable<FireStation>
	 */

	@Test
	public void getAllFireStationsTest() {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationService.getAllFireStations());
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertTrue(result.contains(fireStation));
		verify(fireStationRepository, Mockito.times(1)).findAll();
	}

	@Test
	public void getAllFireStationsTestIfDBIsEmpty() {
		when(fireStationRepository.findAll()).thenReturn(new ArrayList<FireStation>());
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationService.getAllFireStations());
		assertTrue(result.isEmpty());
		verify(fireStationRepository, Mockito.times(1)).findAll();
	}

	/*
	 * Adds a fire station
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - true if the fire station was correctly saved
	 */

	@Test
	public void postFireStationTest() {
		assertEquals(fireStation, fireStationService.postFireStation(fireStation));
		verify(fireStationRepository, Mockito.times(1)).save(any(FireStation.class));
	}

	@Test
	public void postFireStationTestIfAlreadyInDB() {
		when(fireStationRepository.save(any(FireStation.class))).thenReturn(new FireStation());
		assertFalse(fireStationService.postFireStation(fireStation).equals(fireStation));
		verify(fireStationRepository, Mockito.times(1)).save(any(FireStation.class));
	}

	/*
	 * Updates the station number of the specified fire station
	 * 
	 * If several fire stations have the same address as the specified fire station,
	 * only one of them will remain, and its station number will be updated
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - true if the fire station was correctly saved
	 */

	@Test
	public void putFireStationTest() {
		assertEquals(fireStation, fireStationService.putFireStation(fireStation));
		verify(fireStationRepository, Mockito.times(1)).findByAddress(anyString());
		verify(fireStationRepository, Mockito.times(1)).delete(any(FireStation.class));
		verify(fireStationRepository, Mockito.times(1)).save(any(FireStation.class));
	}

	@Test
	public void putFireStationTestIfNotInDB() {
		when(fireStationRepository.findByAddress(anyString())).thenReturn(new ArrayList<FireStation>());
		assertFalse(fireStationService.putFireStation(fireStationOtherAddress).equals(fireStationOtherAddress));
		verify(fireStationRepository, Mockito.times(1)).findByAddress(anyString());
		verify(fireStationRepository, Mockito.times(0)).delete(any(FireStation.class));
		verify(fireStationRepository, Mockito.times(0)).save(any(FireStation.class));
	}

}
