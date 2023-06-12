package com.openclassrooms.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

	static FireStation fireStation = new FireStation("1509 Culver St", "3");

	@BeforeEach
	private void setUp() {
		when(fireStationRepository.delete(any(FireStation.class))).thenReturn(fireStation);
		when(fireStationRepository.get(any(FireStation.class))).thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		when(fireStationRepository.getAll()).thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		when(fireStationRepository.save(any(FireStation.class))).thenReturn(fireStation);
	}

	@Test
	void contextLoads() {
	}
	
	/*
	 * Deletes every fire station corresponding to the specified fire station
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - A boolean that is false if the fire station is not found
	 */

	@Test
	public void deleteFireStationTest() {
		assertEquals(new ArrayList<FireStation>(Arrays.asList(fireStation)), fireStationService.deleteFireStation(fireStation));
		verify(fireStationRepository, Mockito.times(1)).get(any(FireStation.class));
		verify(fireStationRepository, Mockito.times(1)).delete(any(FireStation.class));
	}

	@Test
	public void deleteFireStationTestIfNotInDB() {
		when(fireStationRepository.get(any(FireStation.class))).thenReturn(new ArrayList<FireStation>());
		assertFalse(fireStationService.deleteFireStation(fireStation).equals(new ArrayList<FireStation>(Arrays.asList(fireStation))));
		verify(fireStationRepository, Mockito.times(1)).get(any(FireStation.class));
		verify(fireStationRepository, Mockito.times(0)).delete(any(FireStation.class));
	}

	/*
	 * Get every fire station corresponding to the specified station number
	 * 
	 * @param - An int representing a station number
	 * 
	 * @return - An Iterable<FireStation>
	 */

	/*
	 * Get every fire station corresponding to the specified fire station
	 * 
	 * @param - A FireStation representing a fire station
	 * 
	 * @return - An Iterable<FireStation>
	 */

	@Test
	public void getFireStationTest() {
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
		verify(fireStationRepository, Mockito.times(1)).get(any(FireStation.class));
	}

	@Test
	public void getFireStationTestIfNotInDB() {
		when(fireStationRepository.get(any(FireStation.class))).thenReturn(new ArrayList<FireStation>());
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationService.getFireStation(fireStation));
		assertTrue(result.isEmpty());
		verify(fireStationRepository, Mockito.times(1)).get(any(FireStation.class));
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
		verify(fireStationRepository, Mockito.times(1)).getAll();
	}

	@Test
	public void getAllFireStationsTestIfDBIsEmpty() {
		when(fireStationRepository.getAll()).thenReturn(new ArrayList<FireStation>());
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationService.getAllFireStations());
		assertTrue(result.isEmpty());
		verify(fireStationRepository, Mockito.times(1)).getAll();
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
		assertEquals(fireStationService.postFireStation(fireStation), new FireStation());
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
		verify(fireStationRepository, Mockito.times(1)).get(any(FireStation.class));
		verify(fireStationRepository, Mockito.times(1)).delete(any(FireStation.class));
		verify(fireStationRepository, Mockito.times(1)).save(any(FireStation.class));
	}

	@Test
	public void putFireStationTestIfNotInDB() {
		when(fireStationRepository.get(any(FireStation.class))).thenReturn(new ArrayList<FireStation>());
		assertEquals(fireStationService.putFireStation(fireStation), new FireStation());
		verify(fireStationRepository, Mockito.times(1)).get(any(FireStation.class));
		verify(fireStationRepository, Mockito.times(0)).delete(any(FireStation.class));
		verify(fireStationRepository, Mockito.times(0)).save(any(FireStation.class));
	}

}
