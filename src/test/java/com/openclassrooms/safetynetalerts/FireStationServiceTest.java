package com.openclassrooms.safetynetalerts;

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
	static FireStation fireStationOtherAddress = new FireStation("wrong address", fireStation.getStationNumber());
	static FireStation fireStationOtherStationNumber = new FireStation(fireStation.getAddress(), 421);

	@BeforeEach
	private void setUp() {
		when(fireStationRepository.find(any(FireStation.class))).thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		when(fireStationRepository.findByAddress(anyString())).thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		when(fireStationRepository.findByStationNumber(anyInt())).thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		when(fireStationRepository.delete(any(FireStation.class))).thenReturn(true);
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
		assertTrue(fireStationService.deleteFireStation(fireStation.getAddress()));
		verify(fireStationRepository, Mockito.times(1)).findByAddress(anyString());
		verify(fireStationRepository, Mockito.times(1)).delete(any(FireStation.class));
	}

	@Test
	public void deleteFireStationTestByAddressIfNotInDB() {
		when(fireStationRepository.findByAddress(anyString())).thenReturn(new ArrayList<FireStation>());
		assertFalse(fireStationService.deleteFireStation(fireStationOtherAddress.getAddress()));
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
	public void deleteFireStationTestByStationNumber() {
		assertTrue(fireStationService.deleteFireStation(fireStation.getStationNumber()));
		verify(fireStationRepository, Mockito.times(1)).findByStationNumber(anyInt());
		verify(fireStationRepository, Mockito.times(1)).delete(any(FireStation.class));
	}

	@Test
	public void deleteFireStationTestByStationNumberIfNotInDB() {
		when(fireStationRepository.findByStationNumber(anyInt())).thenReturn(new ArrayList<FireStation>());
		assertFalse(fireStationService.deleteFireStation(fireStationOtherStationNumber.getStationNumber()));
		verify(fireStationRepository, Mockito.times(1)).findByStationNumber(anyInt());
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
		assertTrue(fireStationService.deleteFireStation(fireStation));
		verify(fireStationRepository, Mockito.times(1)).find(any(FireStation.class));
		verify(fireStationRepository, Mockito.times(1)).delete(any(FireStation.class));
	}

	@Test
	public void deleteFireStationTestByFireStationIfNotInDB() {
		when(fireStationRepository.find(any(FireStation.class))).thenReturn(new ArrayList<FireStation>());
		assertFalse(fireStationService.deleteFireStation(fireStation));
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
	}

	/*
	 * Get every fire station corresponding to the specified station number
	 * 
	 * @param - An int representing a station number
	 * 
	 * @return - An Iterable<FireStation>
	 */

	@Test
	public void getFireStationTestByStationNumber() {
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
	}

	/*
	 * Get every fire stations
	 * 
	 * @return - An Iterable<FireStation>
	 */

	@Test
	public void getAllFireStationsTest() {
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
	}

}
