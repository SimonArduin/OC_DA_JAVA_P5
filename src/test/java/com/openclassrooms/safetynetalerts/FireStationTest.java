package com.openclassrooms.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.model.FireStation;

@SpringBootTest(classes = FireStation.class)
public class FireStationTest {

	FireStation fireStation;
	FireStation fireStationOther;
	FireStation fireStationTest;

	@BeforeEach
	void setUpPerTest() {
		fireStation = new FireStation("address", "station");
		fireStationOther = new FireStation("otherAddress", "otherStation");
		fireStationTest = new FireStation();
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class isEmptyTests {

		@Test
		void isEmptyTest() {
			assertFalse(fireStation.isEmpty());
		}

		@Test
		void isEmptyTestIfEmpty() {
			assertTrue(fireStationTest.isEmpty());
		}

		@Test
		void isEmptyTestIfOnlyAddress() {
			fireStationTest.setAddress(fireStation.getAddress());
			assertFalse(fireStationTest.isEmpty());
		}
	}

	@Nested
	class equalsTests {

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

	@Nested
	class updateTests {

		@Test
		void updateTest() {
			fireStationTest.setAddress(fireStation.getAddress());
			fireStationTest.setStation(fireStationOther.getStation());
			assertTrue(fireStation.update(fireStationTest));
			assertEquals(fireStationTest.getAddress(), fireStation.getAddress());
			assertEquals(fireStationTest.getStation(), fireStation.getStation());
		}

		@Test
		void updateTestIfFirstNull() {
			FireStation fireStationBefore = fireStationTest;
			assertFalse(fireStationTest.update(fireStation));
			assertEquals(fireStationBefore, fireStationTest);
		}

		@Test
		void updateTestIfSecondNull() {
			FireStation fireStationBefore = fireStation;
			assertFalse(fireStation.update(fireStationTest));
			assertEquals(fireStationBefore, fireStation);
		}

		@Test
		void updateTestIfSame() {
			FireStation fireStationBefore = fireStation;
			fireStationTest = fireStation;
			assertTrue(fireStation.update(fireStationTest));
			assertEquals(fireStationBefore, fireStation);
		}

		@Test
		void updateTestIfNotSameName() {
			FireStation fireStationBefore = fireStation;
			assertFalse(fireStation.update(fireStationOther));
			assertEquals(fireStationBefore, fireStation);
		}
	}
}
