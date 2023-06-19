package com.openclassrooms.safetynetalerts.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

	final FireStation fireStation = new FireStation("address", "station");
	final ArrayList<FireStation> fireStationList = new ArrayList<FireStation>(Arrays.asList(fireStation));

	@BeforeEach
	private void setUp() {
		when(fireStationRepository.delete(any(FireStation.class))).thenReturn(fireStation);
		when(fireStationRepository.get(any(FireStation.class))).thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		when(fireStationRepository.save(any(FireStation.class))).thenReturn(fireStation);
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class deleteFireStationTests {

		@Test
		public void deleteFireStationTest() {
			assertEquals(new ArrayList<FireStation>(Arrays.asList(fireStation)), fireStationService.deleteFireStation(fireStation));
			verify(fireStationRepository, Mockito.times(1)).get(any(FireStation.class));
			verify(fireStationRepository, Mockito.times(1)).delete(any(FireStation.class));
		}

		@Test
		public void deleteFireStationTestIfNotInDB() {
			when(fireStationRepository.get(any(FireStation.class))).thenReturn(new ArrayList<FireStation>());
			assertEquals(new ArrayList<FireStation>(), fireStationService.deleteFireStation(fireStation));
			verify(fireStationRepository, Mockito.times(1)).get(any(FireStation.class));
			verify(fireStationRepository, Mockito.times(0)).delete(any(FireStation.class));
		}

		@Test
		public void deleteFireStationTestIfEmpty() {
			when(fireStationRepository.get(any(FireStation.class))).thenReturn(new ArrayList<FireStation>());
			assertEquals(new ArrayList<FireStation>(), fireStationService.deleteFireStation(new FireStation()));
			verify(fireStationRepository, Mockito.times(1)).get(any(FireStation.class));
			verify(fireStationRepository, Mockito.times(0)).delete(any(FireStation.class));
		}

		@Test
		public void deleteFireStationTestIfNull() {
			when(fireStationRepository.get(null)).thenReturn(new ArrayList<FireStation>());
			assertEquals(new ArrayList<FireStation>(), fireStationService.deleteFireStation(null));
			verify(fireStationRepository, Mockito.times(1)).get(null);
			verify(fireStationRepository, Mockito.times(0)).delete(any(FireStation.class));
		}

		@Test
		public void deleteFireStationTestIfErrorOnDelete() {
			when(fireStationRepository.delete(any(FireStation.class))).thenReturn(new FireStation());
			assertEquals(new ArrayList<FireStation>(), fireStationService.deleteFireStation(fireStation));
			verify(fireStationRepository, Mockito.times(1)).get(any(FireStation.class));
			verify(fireStationRepository, Mockito.times(1)).delete(any(FireStation.class));
		}
	}

	@Nested
	class getFireStationTests {

		@Test
		public void getFireStationTest() {
			assertEquals(fireStationList, fireStationService.getFireStation(fireStation));
			verify(fireStationRepository, Mockito.times(1)).get(any(FireStation.class));
		}

		@Test
		public void getFireStationTestIfNotInDB() {
			when(fireStationRepository.get(any(FireStation.class))).thenReturn(new ArrayList<FireStation>());
			assertEquals(new ArrayList<FireStation>(), fireStationService.getFireStation(fireStation));
			verify(fireStationRepository, Mockito.times(1)).get(any(FireStation.class));
		}

		@Test
		public void getFireStationTestIfEmpty() {
			when(fireStationRepository.get(any(FireStation.class))).thenReturn(new ArrayList<FireStation>());
			assertEquals(new ArrayList<FireStation>(), fireStationService.getFireStation(new FireStation()));
			verify(fireStationRepository, Mockito.times(1)).get(any(FireStation.class));
		}

		@Test
		public void getFireStationTestIfNull() {
			when(fireStationRepository.get(null)).thenReturn(new ArrayList<FireStation>());
			assertEquals(new ArrayList<FireStation>(), fireStationService.getFireStation(null));
			verify(fireStationRepository, Mockito.times(1)).get(null);
		}
	}

	@Nested
	class postFireStationTests {

		@Test
		public void postFireStationTest() {
			assertEquals(fireStation, fireStationService.postFireStation(fireStation));
			verify(fireStationRepository, Mockito.times(1)).save(any(FireStation.class));
		}

		@Test
		public void postFireStationTestIfAlreadyInDB() {
			when(fireStationRepository.save(any(FireStation.class))).thenReturn(new FireStation());
			assertEquals(new FireStation(), fireStationService.postFireStation(fireStation));
			verify(fireStationRepository, Mockito.times(1)).save(any(FireStation.class));
		}

		@Test
		public void postFireStationTestIfEmpty() {
			when(fireStationRepository.save(any(FireStation.class))).thenReturn(new FireStation());
			assertEquals(new FireStation(), fireStationService.postFireStation(new FireStation()));
			verify(fireStationRepository, Mockito.times(1)).save(any(FireStation.class));
		}

		@Test
		public void postFireStationTestIfNull() {
			when(fireStationRepository.save(null)).thenReturn(new FireStation());
			assertEquals(new FireStation(), fireStationService.postFireStation(null));
			verify(fireStationRepository, Mockito.times(1)).save(null);
		}
	}

	@Nested
	class putFireStationTests {

		@Test
		public void putFireStationTest() {
			assertEquals(fireStation, fireStationService.putFireStation(fireStation));
			verify(fireStationRepository, Mockito.times(1)).save(any(FireStation.class));
		}

		@Test
		public void putFireStationTestIfAlreadyInDB() {
			when(fireStationRepository.save(any(FireStation.class))).thenReturn(new FireStation());
			assertEquals(new FireStation(), fireStationService.putFireStation(fireStation));
			verify(fireStationRepository, Mockito.times(1)).save(any(FireStation.class));
		}

		@Test
		public void putFireStationTestIfEmpty() {
			assertEquals(new FireStation(), fireStationService.putFireStation(new FireStation()));
			verify(fireStationRepository, Mockito.times(0)).save(any(FireStation.class));
		}

		@Test
		public void putFireStationTestIfNull() {
			when(fireStationRepository.save(null)).thenReturn(new FireStation());
			assertEquals(new FireStation(), fireStationService.putFireStation(null));
			verify(fireStationRepository, Mockito.times(0)).save(null);
		}
	}
}
