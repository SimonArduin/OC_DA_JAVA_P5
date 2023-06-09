package com.openclassrooms.safetynetalerts.unit;

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
	Object[] objects = new Object[FireStation.class.getDeclaredFields().length];

	@BeforeEach
	void setUpPerTest() {
		fireStation = new FireStation("address", "station");
		fireStationOther = new FireStation("otherAddress", "otherStation");
		fireStationTest = new FireStation();
		objects[0] = fireStation.getAddress();
		objects[1] = fireStation.getStation();
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
		void equalsTestIfNotFireStation() {
			assertFalse(fireStation.equals(new Object()));
		}

		@Test
		void equalsTestIfFirstEmpty() {
			assertFalse(fireStationTest.equals(fireStation));
		}

		@Test
		void equalsTestIfSecondEmpty() {
			assertFalse(fireStation.equals(fireStationTest));
		}

		@Test
		void equalsTestIfBothEmpty() {
			assertTrue(fireStationTest.equals(fireStationTest));
		}

		@Test
		void equalsTestIfNull() {
			assertFalse(fireStation.equals(null));
		}

		@Test
		void equalsTestIfOtherFireStation() {
			assertFalse(fireStation.equals(fireStationOther));
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
			fireStation.update(fireStationOther);
			assertEquals(fireStationOther, fireStation);
		}

		@Test
		void updateTestIfFirstEmpty() {
			FireStation fireStationBefore = fireStationTest;
			fireStationTest.update(fireStation);
			assertEquals(fireStationBefore, fireStationTest);
		}

		@Test
		void updateTestIfSecondEmpty() {
			FireStation fireStationBefore = fireStation;
			fireStation.update(fireStationTest);
			assertEquals(fireStationBefore, fireStation);
		}
		
		@Test
		void updateTestIfNull() {
			FireStation fireStationBefore = fireStation;
			fireStation.update(null);
			assertEquals(fireStationBefore, fireStation);
		}

		@Test
		void updateTestIfSame() {
			FireStation fireStationBefore = fireStation;
			fireStationTest = fireStation;
			fireStation.update(fireStationTest);
			assertEquals(fireStationBefore, fireStation);
		}
	}
}
