package com.openclassrooms.safetynetalerts.integration;

import static org.hamcrest.CoreMatchers.is;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
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

	final FireStation fireStation = new FireStation("address", "station");
	final FireStation fireStationNotInDB = new FireStation("addresOther", "stationOther");
	FireStation fireStationTest;
	final ArrayList<FireStation> fireStationList = new ArrayList<FireStation>(Arrays.asList(fireStation));

	@BeforeEach
	public void setUpPerTest() {
		DataBase.setDataBase(fireStationList, null, null);
		fireStationTest = new FireStation();
	}

		@Nested
		class putFireStationTests {

			@Test
			public void putFireStationTest() throws Exception {
				mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(fireStation))).andExpect(status().isCreated())
						.andExpect(jsonPath("address", is(fireStation.getAddress())))
						.andExpect(jsonPath("station", is(fireStation.getStation())));
			}

			@Test
			public void putFireStationTestIfNotInDB() throws Exception {
				mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(fireStationNotInDB)))
						.andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
			}

			@Test
			public void putFireStationTestIfNoRequestBody() throws Exception {
				mockMvc.perform(put("/firestation")).andExpect(status().isBadRequest())
						.andExpect(jsonPath("$").doesNotExist());
				;
			}
		}

		@Nested
		class postFireStationTests {

			@Test
			public void postFireStation() throws Exception {
				mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(fireStationNotInDB)))
						.andExpect(status().isCreated())
						.andExpect(jsonPath("address", is(fireStationNotInDB.getAddress())))
						.andExpect(jsonPath("station", is(fireStationNotInDB.getStation())));
			}

			@Test
			public void postFireStationTestIfAlreadyInDB() throws Exception {
				mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(fireStation))).andExpect(status().isConflict())
						.andExpect(jsonPath("$").doesNotExist());
			}

			@Test
			public void postFireStationTestIfNoRequestBody() throws Exception {
				mockMvc.perform(post("/firestation")).andExpect(status().isBadRequest())
						.andExpect(jsonPath("$").doesNotExist());
				;
			}
		}

		@Nested
		class deleteFireStationTests {

			@Test
			public void deleteFireStation() throws Exception {
				mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(fireStation))).andExpect(status().isOk())
						.andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
						.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
			}

			@Test
			public void deleteFireStationIfNotInDB() throws Exception {
				mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(fireStationNotInDB)))
						.andExpect(status().isNotFound());
			}

			@Test
			public void deleteFireStationIfNoRequestBody() throws Exception {
				mockMvc.perform(delete(String.format("/firestation"))).andExpect(status().isBadRequest());
			}

			@Test
			public void deleteFireStationByAddress() throws Exception {
				fireStationTest.setAddress(fireStation.getAddress());
				mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(fireStationTest))).andExpect(status().isOk())
						.andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
						.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
			}

			@Test
			public void deleteFireStationByStation() throws Exception {
				fireStationTest.setStation(fireStation.getStation());
				mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(fireStationTest))).andExpect(status().isOk())
						.andExpect(jsonPath("$[0].address", is(fireStation.getAddress())))
						.andExpect(jsonPath("$[0].station", is(fireStation.getStation())));
			}
		}
	}