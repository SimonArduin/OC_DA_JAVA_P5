package com.openclassrooms.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.model.FireStation;

@SpringBootTest(classes = FireStation.class)
public class FireStationTest {
	
	final FireStation fireStation = new FireStation("address", "station");
	final FireStation fireStationOther = new FireStation("otherAddress", "otherStation");
	FireStation fireStationTest;
	
	@BeforeEach
	void setUpPerTest() {
		fireStationTest = new FireStation();
	}
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void isEmpty() {
		assertFalse(fireStation.isEmpty());
	}

	@Test
	void isEmptyIfEmpty() {
		assertTrue(fireStationTest.isEmpty());
	}
	
	@Test
	void isEmptyIfOnlyAddress() {
		fireStationTest.setAddress(fireStation.getAddress());
		assertFalse(fireStationTest.isEmpty());
	}
	
	@Test
	void equalsTest() {
		assertTrue(fireStation.equals(fireStation));
	}
	
	@Test
	void equalsTestIfOtherFireStation() {
		assertFalse(fireStation.equals(fireStationOther));
	}

	@Test
	void equalsTestIfFirstNull() {
		assertFalse(fireStationTest.equals(fireStation));
	}
	
	@Test
	void equalsTestIfSecondNull() {
		assertFalse(fireStation.equals(fireStationTest));
	}

	@Test
	void equalsTestIfBothNull() {
		assertTrue(fireStationTest.equals(fireStationTest));
	}
	
	@Test
	void equalsTestIfOnlyStation() {
		fireStationTest.setStation(fireStation.getStation());
		assertTrue(fireStationTest.equals(fireStation));
	}

	@Test
	void equalsTestIfOnlyStationAndOtherFireStation() {
		fireStationTest.setStation(fireStation.getStation());
		assertFalse(fireStationTest.equals(fireStationOther));
	}
	
	@Test
	void equalsTestIfOnlyAddress() {
		fireStationTest.setAddress(fireStation.getAddress());
		assertTrue(fireStationTest.equals(fireStation));
	}

	@Test
	void equalsTestIfOnlyAddressAndOtherFireStation() {
		fireStationTest.setAddress(fireStation.getAddress());
		assertFalse(fireStationTest.equals(fireStationOther));
	}

	@Test
	void equalsTestIfAddressAndStationAndOtherFireStationStation() {
		fireStationTest.setAddress(fireStation.getAddress());
		fireStationTest.setStation(fireStationOther.getStation());
		assertFalse(fireStationTest.equals(fireStationOther));
	}
}
