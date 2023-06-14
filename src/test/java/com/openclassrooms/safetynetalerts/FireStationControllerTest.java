package com.openclassrooms.safetynetalerts;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.safetynetalerts.controller.FireStationController;
import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.service.FireStationService;

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

	@Autowired
	FireStationController fireStationController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	FireStationService fireStationService;

	final FireStation fireStation = new FireStation("address", "station");
	final FireStation fireStationOtherAddress = new FireStation("otherAddress", fireStation.getStation());
	final int numberOfFireStationByStationNumber = 1;

	@BeforeEach
	private void setUp() {
		Mockito.when(fireStationService.deleteFireStation(any(FireStation.class)))
				.thenReturn(new ArrayList<FireStation>(Arrays.asList(fireStation)));
		Mockito.when(fireStationService.putFireStation(any(FireStation.class))).thenReturn(fireStation);
		Mockito.when(fireStationService.postFireStation(any(FireStation.class))).thenReturn(fireStation);
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class putFireStationTests {

		@Test
		public void putFireStationTest() throws Exception {
			mockMvc.perform(put(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
					fireStation.getStation()))).andExpect(status().isOk())
					.andExpect(jsonPath("address", is(fireStation.getAddress())))
					.andExpect(jsonPath("station", is(fireStation.getStation())));
			verify(fireStationService, Mockito.times(1)).putFireStation(any(FireStation.class));
		}

		@Test
		public void putFireStationTestIfError() throws Exception {
			Mockito.when(fireStationService.putFireStation(any(FireStation.class))).thenReturn(new FireStation());
			mockMvc.perform(put(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
					fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("address", nullValue()))
					.andExpect(jsonPath("station", nullValue()));
			verify(fireStationService, Mockito.times(1)).putFireStation(any(FireStation.class));
		}
	}

	@Nested
	class postFireStationTests {
		
		@Test
		public void postFireStation() throws Exception {
			mockMvc.perform(post(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
					fireStation.getStation()))).andExpect(status().isOk())
					.andExpect(jsonPath("address", is(fireStation.getAddress())))
					.andExpect(jsonPath("station", is(fireStation.getStation())));
			verify(fireStationService, Mockito.times(1)).postFireStation(any(FireStation.class));
		}

		@Test
		public void postFireStationTestIfError() throws Exception {
			Mockito.when(fireStationService.postFireStation(any(FireStation.class))).thenReturn(new FireStation());
			mockMvc.perform(post(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
					fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("address", nullValue()))
					.andExpect(jsonPath("station", nullValue()));
			verify(fireStationService, Mockito.times(1)).postFireStation(any(FireStation.class));
		}
	}

	@Nested
	class deleteFireStationTests {

		@Test
		public void deleteFireStation() throws Exception {
			mockMvc.perform(delete(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
					fireStation.getStation()))).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
					.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
			verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
		}

		@Test
		public void deleteFireStationIfError() throws Exception {
			Mockito.when(fireStationService.deleteFireStation(any(FireStation.class)))
					.thenReturn(new ArrayList<FireStation>(Arrays.asList(new FireStation())));
			mockMvc.perform(delete(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
					fireStation.getStation()))).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].address", nullValue())).andExpect(jsonPath("$[0].station", nullValue()));
			verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
		}

		@Test
		public void deleteFireStationByAddress() throws Exception {
			mockMvc.perform(delete(String.format("/firestation?address=%s", fireStation.getAddress())))
					.andExpect(status().isOk()).andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
					.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
			verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
		}

		@Test
		public void deleteFireStationByAddressIfError() throws Exception {
			Mockito.when(fireStationService.deleteFireStation(any(FireStation.class)))
					.thenReturn(new ArrayList<FireStation>(Arrays.asList(new FireStation())));
			mockMvc.perform(delete(String.format("/firestation?address=%s", fireStation.getAddress())))
					.andExpect(status().isOk()).andExpect(jsonPath("$[0].address", nullValue()))
					.andExpect(jsonPath("$[0].station", nullValue()));
			verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
		}

		@Test
		public void deleteFireStationByStation() throws Exception {
			mockMvc.perform(delete(String.format("/firestation?stationNumber=%s", fireStation.getStation())))
					.andExpect(status().isOk()).andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
					.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
			verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
		}

		@Test
		public void deleteFireStationByStationIfError() throws Exception {
			Mockito.when(fireStationService.deleteFireStation(any(FireStation.class)))
					.thenReturn(new ArrayList<FireStation>(Arrays.asList(new FireStation())));
			mockMvc.perform(delete(String.format("/firestation?stationNumber=%s", fireStation.getStation())))
					.andExpect(status().isOk()).andExpect(jsonPath("$[0].address", nullValue()))
					.andExpect(jsonPath("$[0].station", nullValue()));
			verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
		}

		@Test
		public void deleteFireStationIfNoParam() throws Exception {
			Mockito.when(fireStationService.deleteFireStation(any(FireStation.class)))
					.thenReturn(new ArrayList<FireStation>(Arrays.asList(new FireStation())));
			mockMvc.perform(delete(String.format("/firestation"))).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].address", nullValue())).andExpect(jsonPath("$[0].station", nullValue()));
			verify(fireStationService, Mockito.times(1)).deleteFireStation(any(FireStation.class));
		}
	}
}
