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
	static FireStation fireStationOtherAddress = new FireStation("wrong address", fireStation.getStationNumber());
	static FireStation fireStationOtherStationNumber = new FireStation(fireStation.getAddress(), 421);
	int sizeOfDB = 13;
	int nbOfFireStationsWithAddress = 1;
	int nbOfFireStationsWithStationNumber = 5;
	
	@BeforeEach
	private void setUpPerTest() {
		fireStationRepository.resetDataBase();
	}

	@Test
	void contextLoads() {
	}
	
	@Test
	public void deleteTest() {
		assertTrue(fireStationRepository.delete(fireStation));
	}
	
	@Test
	public void deleteTestIfFireStationNotInDB() {
		assertFalse(fireStationRepository.delete(fireStationOtherAddress));
	}


	@Test
	public void findAllTest() {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationRepository.findAll());
		assertEquals(result.size(), sizeOfDB);
		assertTrue(result.contains(fireStation));
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
	public void findByStationNumberTest() {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationRepository.findByStationNumber(fireStation.getStationNumber()));
		boolean wrongStationNumber = false;
		for(FireStation fireStationInResult : result) {
			if(fireStationInResult.getStationNumber() != fireStation.getStationNumber())
				wrongStationNumber = true;
		}
		assertEquals(result.size(), nbOfFireStationsWithStationNumber);
		assertTrue(result.contains(fireStation));
		assertFalse(wrongStationNumber);
	}
	
	@Test
	public void findByStationNumberTestIfFireStationNotInDB() {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationRepository.findByStationNumber(fireStationOtherStationNumber.getStationNumber()));
		assertEquals(result.size(), 0);
	}

	@Test
	public void saveTest() {
		assertTrue(fireStationRepository.save(fireStationOtherAddress));
	}

	@Test
	public void saveTestIfAlreadyInDB() {
		assertFalse(fireStationRepository.save(fireStation));
	}

}
