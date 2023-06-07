package com.openclassrooms.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.repository.FireStationRepository;

@SpringBootTest(classes = FireStationRepository.class)
public class FireStationRepositoryTest {

	@Autowired
	FireStationRepository fireStationRepository;
	static FireStation fireStation = new FireStation("1509 Culver St", 3);
	static FireStation fireStationOtherAddress = new FireStation("wrong address", fireStation.getStation());
	static FireStation fireStationOtherStation = new FireStation(fireStation.getAddress(), 421);
	int sizeOfDB = 13;
	int nbOfFireStationsWithAddress = 1;
	int nbOfFireStationsWithStation = 5;
	
	@BeforeEach
	private void setUpPerTest() {
		try {
			fireStationRepository.resetDataBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void contextLoads() {
	}
	
	@Test
	public void deleteTest() {
		assertEquals(fireStation, fireStationRepository.delete(fireStation));
	}
	
	@Test
	public void deleteTestIfFireStationNotInDB() {
		assertFalse(fireStationRepository.delete(fireStationOtherAddress).equals(fireStation));
	}

	@Test
	public void findAllTest() {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationRepository.findAll());
		assertEquals(result.size(), sizeOfDB);
		assertTrue(result.contains(fireStation));
	}

	@Test
	public void findTest() {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationRepository.find(fireStation));
		boolean wrongFireStation = false;
		for(FireStation fireStationInResult : result) {
			if(!fireStationInResult.equals(fireStation))
				wrongFireStation = true;
		}
		assertEquals(result.size(), nbOfFireStationsWithAddress);
		assertTrue(result.contains(fireStation));
		assertFalse(wrongFireStation);
	}
	
	@Test
	public void findIfFireStationNotInDB() {
		assertEquals(fireStationRepository.find(fireStationOtherAddress).size(), 0);
	}

	@Test
	public void findByAddressTest() {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationRepository.findByAddress(fireStation.getAddress()));
		boolean wrongAddress = false;
		for(FireStation fireStationInResult : result) {
			if(!fireStationInResult.getAddress().equals(fireStation.getAddress()))
				wrongAddress = true;
		}
		assertEquals(result.size(), nbOfFireStationsWithAddress);
		assertTrue(result.contains(fireStation));
		assertFalse(wrongAddress);
	}
	
	@Test
	public void findByAddressTestIfFireStationNotInDB() {
		assertEquals(fireStationRepository.findByAddress(fireStationOtherAddress.getAddress()).size(), 0);
	}

	@Test
	public void findByStationTest() {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationRepository.findByStation(fireStation.getStation()));
		boolean wrongStation = false;
		for(FireStation fireStationInResult : result) {
			if(fireStationInResult.getStation() != fireStation.getStation())
				wrongStation = true;
		}
		assertEquals(result.size(), nbOfFireStationsWithStation);
		assertTrue(result.contains(fireStation));
		assertFalse(wrongStation);
	}
	
	@Test
	public void findByStationTestIfFireStationNotInDB() {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationRepository.findByStation(fireStationOtherStation.getStation()));
		assertEquals(result.size(), 0);
	}

	@Test
	public void saveTest() {
		assertEquals(fireStationOtherAddress, fireStationRepository.save(fireStationOtherAddress));
	}

	@Test
	public void saveTestIfAlreadyInDB() {
		assertFalse(fireStationRepository.save(fireStation).equals(fireStation));
	}

}
