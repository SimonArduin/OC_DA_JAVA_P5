package com.openclassrooms.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.repository.DataBase;
import com.openclassrooms.safetynetalerts.repository.FireStationRepository;

@SpringBootTest(classes = FireStationRepository.class)
public class FireStationRepositoryTest {

	DataBase dataBase = mock(DataBase.class);

	FireStationRepository fireStationRepository = new FireStationRepository(dataBase);

	final FireStation fireStation = new FireStation("address", "station");
	final ArrayList<FireStation> fireStations = new ArrayList<FireStation>(Arrays.asList(fireStation));

	@BeforeEach
	private void setUpPerTest() {
		Mockito.when(dataBase.getFireStations()).thenReturn(fireStations);
		Mockito.when(dataBase.getFireStations(any(FireStation.class))).thenReturn(fireStations);
		Mockito.when(dataBase.addFireStation(any(FireStation.class))).thenReturn(fireStation);
		Mockito.when(dataBase.removeFireStation(any(FireStation.class))).thenReturn(fireStation);
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class deleteTests {

		@Test
		public void deleteTest() {
			assertEquals(fireStation, fireStationRepository.delete(fireStation));
			verify(dataBase, Mockito.times(1)).removeFireStation(any(FireStation.class));
		}

		@Test
		public void deleteTestIfNotInDB() {
			Mockito.when(dataBase.removeFireStation(any(FireStation.class))).thenReturn(new FireStation());
			assertEquals(new FireStation(), fireStationRepository.delete(fireStation));
			verify(dataBase, Mockito.times(1)).removeFireStation(any(FireStation.class));
		}

		@Test
		public void deleteTestIfEmpty() {
			Mockito.when(dataBase.removeFireStation(any(FireStation.class))).thenReturn(new FireStation());
			assertEquals(new FireStation(), fireStationRepository.delete(new FireStation()));
			verify(dataBase, Mockito.times(1)).removeFireStation(any(FireStation.class));
		}

		@Test
		public void deleteTestIfNull() {
			Mockito.when(dataBase.removeFireStation(null)).thenReturn(new FireStation());
			assertEquals(new FireStation(), fireStationRepository.delete(null));
			verify(dataBase, Mockito.times(1)).removeFireStation(null);
		}
	}

	@Test
	public void getAllTest() {
		ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationRepository.getAll());
		verify(dataBase, Mockito.times(1)).getFireStations();
		assertEquals(fireStations.size(), result.size());
		assertTrue(result.contains(fireStation));
	}

	@Nested
	class getTests {

		@Test
		public void getTest() {
			ArrayList<FireStation> result = new ArrayList<FireStation>(fireStationRepository.get(fireStation));
			verify(dataBase, Mockito.times(1)).getFireStations(any(FireStation.class));
			for (FireStation fireStationInResult : result)
				assertTrue(fireStation.equals(fireStationInResult));
			assertEquals(fireStations.size(), result.size());
			assertTrue(result.contains(fireStation));
		}

		@Test
		public void getTestIfNotInDB() {
			Mockito.when(dataBase.getFireStations(any(FireStation.class))).thenReturn(new ArrayList<FireStation>());
			assertEquals(new ArrayList<FireStation>(), fireStationRepository.get(fireStation));
			verify(dataBase, Mockito.times(1)).getFireStations(any(FireStation.class));
		}

		@Test
		public void getTestIfEmpty() {
			Mockito.when(dataBase.getFireStations(any(FireStation.class))).thenReturn(new ArrayList<FireStation>());
			assertEquals(new ArrayList<FireStation>(), fireStationRepository.get(new FireStation()));
			verify(dataBase, Mockito.times(1)).getFireStations(any(FireStation.class));
		}

		@Test
		public void getTestIfNull() {
			Mockito.when(dataBase.getFireStations(null)).thenReturn(new ArrayList<FireStation>());
			assertEquals(new ArrayList<FireStation>(), fireStationRepository.get(null));
			verify(dataBase, Mockito.times(1)).getFireStations(null);
		}
	}

	@Nested
	class saveTests {

		@Test
		public void saveTest() {
			assertEquals(fireStation, fireStationRepository.save(fireStation));
			verify(dataBase, Mockito.times(1)).addFireStation(any(FireStation.class));
		}

		@Test
		public void saveTestIfAlreadyInDB() {
			Mockito.when(dataBase.addFireStation(any(FireStation.class))).thenReturn(new FireStation());
			assertEquals(new FireStation(), fireStationRepository.save(fireStation));
			verify(dataBase, Mockito.times(1)).addFireStation(any(FireStation.class));
		}

		@Test
		public void saveTestIfEmpty() {
			Mockito.when(dataBase.addFireStation(any(FireStation.class))).thenReturn(new FireStation());
			assertEquals(new FireStation(), fireStationRepository.save(new FireStation()));
			verify(dataBase, Mockito.times(1)).addFireStation(any(FireStation.class));
		}

		@Test
		public void saveTestIfNull() {
			Mockito.when(dataBase.addFireStation(null)).thenReturn(new FireStation());
			assertEquals(new FireStation(), fireStationRepository.save(null));
			verify(dataBase, Mockito.times(1)).addFireStation(null);
		}
	}
}
