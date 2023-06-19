package com.openclassrooms.safetynetalerts.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.safetynetalerts.controller.FireStationController;
import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.repository.DataBase;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationIT {

	@Autowired
	FireStationController fireStationController = new FireStationController();;

	@Autowired
	private MockMvc mockMvc;

	final FireStation fireStation = new FireStation("1509 Culver St", "3");
	final FireStation fireStationNotInDB = new FireStation("address", "station");

	@BeforeEach
	public void setUpPerTest() {
		DataBase.readDataBase();
	}

	@Nested
	class putFireStationTests {

		@Test
		public void putFireStationTest() throws Exception {
			mockMvc.perform(put(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
					fireStation.getStation()))).andExpect(status().isOk())
					.andExpect(jsonPath("address", is(fireStation.getAddress())))
					.andExpect(jsonPath("station", is(fireStation.getStation())));
		}

		@Test
		public void putFireStationTestIfNotInDB() throws Exception {
			mockMvc.perform(put(String.format("/firestation?address=%s&stationNumber=%s",
					fireStationNotInDB.getAddress(), fireStationNotInDB.getStation()))).andExpect(status().isOk())
					.andExpect(jsonPath("address", nullValue())).andExpect(jsonPath("station", nullValue()));
		}

		/*
		@Test
		public void putFireStationTestIfEmptyParams() throws Exception {
			mockMvc.perform(put(String.format("/firestation?address=%s&stationNumber=%s", null, null)))
					.andExpect(status().isOk()).andExpect(jsonPath("address", nullValue()))
					.andExpect(jsonPath("station", nullValue()));
		}
		*/

		@Test
		public void putFireStationTestIfNoParams() throws Exception {
			mockMvc.perform(put("/firestation")).andExpect(status().is(400));
		}
	}

	@Nested
	class postFireStationTests {

		@Test
		public void postFireStation() throws Exception {
			mockMvc.perform(post(String.format("/firestation?address=%s&stationNumber=%s",
					fireStationNotInDB.getAddress(), fireStationNotInDB.getStation()))).andExpect(status().isOk())
					.andExpect(jsonPath("address", is(fireStationNotInDB.getAddress())))
					.andExpect(jsonPath("station", is(fireStationNotInDB.getStation())));
		}

		@Test
		public void postFireStationTestIfAlreadyInDB() throws Exception {
			mockMvc.perform(post(String.format("/firestation?address=%s&stationNumber=%s", fireStation.getAddress(),
					fireStation.getStation()))).andExpect(status().isOk()).andExpect(jsonPath("address", nullValue()))
					.andExpect(jsonPath("station", nullValue()));
		}

		/*@Test
		public void postFireStationTestIfEmptyParams() throws Exception {
			mockMvc.perform(post(String.format("/firestation?address=%s&stationNumber=%s", null, null)))
					.andExpect(status().isOk()).andExpect(jsonPath("address", nullValue()))
					.andExpect(jsonPath("station", nullValue()));
		}*/

		@Test
		public void postFireStationTestIfNoParams() throws Exception {
			mockMvc.perform(post("/firestation")).andExpect(status().is(400));
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
		}

		@Test
		public void deleteFireStationIfNotInDB() throws Exception {
			mockMvc.perform(delete(String.format("/firestation?address=%s&stationNumber=%s",
					fireStationNotInDB.getAddress(), fireStationNotInDB.getStation()))).andExpect(status().isOk())
					.andExpect(jsonPath("$", is(new ArrayList<FireStation>())));
		}

		@Test
		public void deleteFireStationIfEmptyParams() throws Exception {
			mockMvc.perform(delete(String.format("/firestation?address=%s&stationNumber=%s", null, null)))
					.andExpect(status().isOk()).andExpect(jsonPath("$", is(new ArrayList<FireStation>())));
		}

		@Test
		public void deleteFireStationIfNoParams() throws Exception {
			mockMvc.perform(delete(String.format("/firestation"))).andExpect(status().isOk())
					.andExpect(jsonPath("$", is(new ArrayList<FireStation>())));
		}

		@Test
		public void deleteFireStationByAddress() throws Exception {
			mockMvc.perform(delete(String.format("/firestation?address=%s", fireStation.getAddress())))
					.andExpect(status().isOk()).andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
					.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
		}

		@Test
		public void deleteFireStationByAddressIfError() throws Exception {
			mockMvc.perform(delete(String.format("/firestation?address=%s", fireStationNotInDB.getAddress())))
					.andExpect(status().isOk()).andExpect(jsonPath("$", is(new ArrayList<FireStation>())));
		}

		@Test
		public void deleteFireStationByStation() throws Exception {
			mockMvc.perform(delete(String.format("/firestation?stationNumber=%s", fireStation.getStation())))
					.andExpect(status().isOk()).andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
					.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
		}

		@Test
		public void deleteFireStationByStationIfError() throws Exception {
			mockMvc.perform(delete("/firestation?stationNumber=%s", fireStationNotInDB.getStation()))
					.andExpect(status().isOk()).andExpect(jsonPath("$", is(new ArrayList<FireStation>())));
		}
	}
}
