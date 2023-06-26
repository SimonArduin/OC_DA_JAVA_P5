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
import com.openclassrooms.safetynetalerts.controller.PersonController;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.repository.DataBase;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonIT {

	@Autowired
	PersonController personController = new PersonController();;

	@Autowired
	private MockMvc mockMvc;

	final Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
	final Person personChild = new Person("firstNameChild", "lastNameChild", person.getAddress(), person.getCity(),
			person.getZip(), person.getPhone(), person.getEmail());
	final Person personNotInDB = new Person("firstNameOther", "lastNameOther", "addressOther", "cityOther", "zipOther",
			"phoneOther", "emailOther");
	Person personTest;
	final ArrayList<Person> personList = new ArrayList<Person>(Arrays.asList(person, personChild));

	@BeforeEach
	public void setUpPerTest() {
		DataBase.setDataBase(null, personList, null);
		personTest = new Person();
	}

	@Nested
	class putPersonTests {

		@Test
		public void putPersonTest() throws Exception {
			mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(person))).andExpect(status().isCreated())
					.andExpect(jsonPath("firstName", is(person.getFirstName())))
					.andExpect(jsonPath("lastName", is(person.getLastName())))
					.andExpect(jsonPath("address", is(person.getAddress())))
					.andExpect(jsonPath("city", is(person.getCity())))
					.andExpect(jsonPath("zip", is(person.getZip())))
					.andExpect(jsonPath("phone", is(person.getPhone())))
					.andExpect(jsonPath("email", is(person.getEmail())));
		}

		@Test
		public void putPersonTestIfNotInDB() throws Exception {
			mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(personNotInDB))).andExpect(status().isNotFound())
					.andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void putPersonTestIfNoRequestBody() throws Exception {
			mockMvc.perform(put("/person")).andExpect(status().isBadRequest()).andExpect(jsonPath("$").doesNotExist());
			;
		}
	}

	@Nested
	class postPersonTests {

		@Test
		public void postPerson() throws Exception {
			mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(personNotInDB))).andExpect(status().isCreated())
			.andExpect(jsonPath("firstName", is(personNotInDB.getFirstName())))
			.andExpect(jsonPath("lastName", is(personNotInDB.getLastName())))
			.andExpect(jsonPath("address", is(personNotInDB.getAddress())))
			.andExpect(jsonPath("city", is(personNotInDB.getCity())))
			.andExpect(jsonPath("zip", is(personNotInDB.getZip())))
			.andExpect(jsonPath("phone", is(personNotInDB.getPhone())))
			.andExpect(jsonPath("email", is(personNotInDB.getEmail())));
		}

		@Test
		public void postPersonTestIfAlreadyInDB() throws Exception {
			mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(person))).andExpect(status().isConflict())
					.andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void postPersonTestIfNoRequestBody() throws Exception {
			mockMvc.perform(post("/person")).andExpect(status().isBadRequest()).andExpect(jsonPath("$").doesNotExist());
			;
		}
	}

	@Nested
	class deletePersonTests {

		@Test
		public void deletePerson() throws Exception {
			mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(person))).andExpect(status().isOk())
			.andExpect(jsonPath("firstName", is(person.getFirstName())))
			.andExpect(jsonPath("lastName", is(person.getLastName())))
			.andExpect(jsonPath("address", is(person.getAddress())))
			.andExpect(jsonPath("city", is(person.getCity())))
			.andExpect(jsonPath("zip", is(person.getZip())))
			.andExpect(jsonPath("phone", is(person.getPhone())))
			.andExpect(jsonPath("email", is(person.getEmail())));
		}

		@Test
		public void deletePersonIfNotInDB() throws Exception {
			mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(personNotInDB))).andExpect(status().isNotFound());
		}

		@Test
		public void deletePersonIfNoRequestBody() throws Exception {
			mockMvc.perform(delete(String.format("/person"))).andExpect(status().isBadRequest());
		}

		@Test
		public void deletePersonIfOnlyFirstNameAndLastName() throws Exception {
			personTest.setFirstName(person.getFirstName());
			personTest.setLastName(person.getLastName());
			mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(personTest))).andExpect(status().isOk())
			.andExpect(jsonPath("firstName", is(person.getFirstName())))
			.andExpect(jsonPath("lastName", is(person.getLastName())))
			.andExpect(jsonPath("address", is(person.getAddress())))
			.andExpect(jsonPath("city", is(person.getCity())))
			.andExpect(jsonPath("zip", is(person.getZip())))
			.andExpect(jsonPath("phone", is(person.getPhone())))
			.andExpect(jsonPath("email", is(person.getEmail())));
		}
	}
}