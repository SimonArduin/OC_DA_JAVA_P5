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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.safetynetalerts.controller.PersonController;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.PersonService;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

	@Autowired
	PersonController personController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	PersonService personService;

	static Person person = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
			"jaboyd@email.com");
	static Person personOnlyName = new Person("John", "Boyd");

	@BeforeEach
	private void setUp() {
		Mockito.when(personService.deletePerson(anyString(), anyString()))
				.thenReturn(new ArrayList<Person>(Arrays.asList(person)));
		Mockito.when(personService.putPerson(any(Person.class))).thenReturn(person);
		Mockito.when(personService.postPerson(any(Person.class))).thenReturn(person);
	}

	@Test
	void contextLoads() {
	}

	/**
	 * Put - Changes the station number of a person in the database
	 * 
	 * @param - A String corresponding to the address of the fire station and an
	 *          optional int corresponding to the new fire station number
	 */
	@Test
	public void putPersonTest() throws Exception {
		mockMvc.perform(
				put(String.format("/person?firstName=%s&lastName=%s&address=%s&city=%s&zip=%s&phone=%s&email=%s",
						person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(),
						person.getZip(), person.getPhone(), person.getEmail())))
				.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(person.getFirstName())))
				.andExpect(jsonPath("lastName", is(person.getLastName())))
				.andExpect(jsonPath("address", is(person.getAddress())))
				.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
				.andExpect(jsonPath("phone", is(person.getPhone())))
				.andExpect(jsonPath("email", is(person.getEmail())));
		verify(personService, Mockito.times(1)).putPerson(any(Person.class));
	}

	@Test
	public void putPersonTestIfOnlyName() throws Exception {
		Mockito.when(personService.putPerson(any(Person.class))).thenReturn(personOnlyName);
		mockMvc.perform(
				put(String.format("/person?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
				.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(person.getFirstName())))
				.andExpect(jsonPath("lastName", is(person.getLastName())))
				.andExpect(jsonPath("address", is(nullValue()))).andExpect(jsonPath("city", is(nullValue())))
				.andExpect(jsonPath("zip", is(nullValue()))).andExpect(jsonPath("phone", is(nullValue())))
				.andExpect(jsonPath("email", is(nullValue())));
		verify(personService, Mockito.times(1)).putPerson(any(Person.class));
	}

	@Test
	public void putPersonTestIfOnlyAddress() throws Exception {
		mockMvc.perform(put(String.format("/person?firstName=%s&lastName=%s&address=%s", person.getFirstName(),
				person.getLastName(), person.getAddress()))).andExpect(status().isOk())
				.andExpect(jsonPath("firstName", is(person.getFirstName())))
				.andExpect(jsonPath("lastName", is(person.getLastName())))
				.andExpect(jsonPath("address", is(person.getAddress())))
				.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
				.andExpect(jsonPath("phone", is(person.getPhone())))
				.andExpect(jsonPath("email", is(person.getEmail())));
		verify(personService, Mockito.times(1)).putPerson(any(Person.class));
	}

	@Test
	public void putPersonTestIfOnlyCity() throws Exception {
		mockMvc.perform(put(String.format("/person?firstName=%s&lastName=%s&city=%s", person.getFirstName(),
				person.getLastName(), person.getCity()))).andExpect(status().isOk())
				.andExpect(jsonPath("firstName", is(person.getFirstName())))
				.andExpect(jsonPath("lastName", is(person.getLastName())))
				.andExpect(jsonPath("address", is(person.getAddress())))
				.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
				.andExpect(jsonPath("phone", is(person.getPhone())))
				.andExpect(jsonPath("email", is(person.getEmail())));
		verify(personService, Mockito.times(1)).putPerson(any(Person.class));
	}

	@Test
	public void putPersonTestIfOnlyZip() throws Exception {
		mockMvc.perform(put(String.format("/person?firstName=%s&lastName=%s&zip=%s", person.getFirstName(),
				person.getLastName(), person.getZip()))).andExpect(status().isOk())
				.andExpect(jsonPath("firstName", is(person.getFirstName())))
				.andExpect(jsonPath("lastName", is(person.getLastName())))
				.andExpect(jsonPath("address", is(person.getAddress())))
				.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
				.andExpect(jsonPath("phone", is(person.getPhone())))
				.andExpect(jsonPath("email", is(person.getEmail())));
		verify(personService, Mockito.times(1)).putPerson(any(Person.class));
	}

	@Test
	public void putPersonTestIfOnlyPhone() throws Exception {
		mockMvc.perform(put(String.format("/person?firstName=%s&lastName=%s&phone=%s", person.getFirstName(),
				person.getLastName(), person.getPhone()))).andExpect(status().isOk())
				.andExpect(jsonPath("firstName", is(person.getFirstName())))
				.andExpect(jsonPath("lastName", is(person.getLastName())))
				.andExpect(jsonPath("address", is(person.getAddress())))
				.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
				.andExpect(jsonPath("phone", is(person.getPhone())))
				.andExpect(jsonPath("email", is(person.getEmail())));
		verify(personService, Mockito.times(1)).putPerson(any(Person.class));
	}

	@Test
	public void putPersonTestIfOnlyEmail() throws Exception {
		mockMvc.perform(put(String.format("/person?firstName=%s&lastName=%s&email=%s", person.getFirstName(),
				person.getLastName(), person.getEmail()))).andExpect(status().isOk())
				.andExpect(jsonPath("firstName", is(person.getFirstName())))
				.andExpect(jsonPath("lastName", is(person.getLastName())))
				.andExpect(jsonPath("address", is(person.getAddress())))
				.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
				.andExpect(jsonPath("phone", is(person.getPhone())))
				.andExpect(jsonPath("email", is(person.getEmail())));
		verify(personService, Mockito.times(1)).putPerson(any(Person.class));
	}

	@Test
	public void putPersonTestIfAddressAndZip() throws Exception {
		mockMvc.perform(put(String.format("/person?firstName=%s&lastName=%s&address=%s&zip=%s", person.getFirstName(),
				person.getLastName(), person.getAddress(), person.getZip()))).andExpect(status().isOk())
				.andExpect(jsonPath("firstName", is(person.getFirstName())))
				.andExpect(jsonPath("lastName", is(person.getLastName())))
				.andExpect(jsonPath("address", is(person.getAddress())))
				.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
				.andExpect(jsonPath("phone", is(person.getPhone())))
				.andExpect(jsonPath("email", is(person.getEmail())));
		verify(personService, Mockito.times(1)).putPerson(any(Person.class));
	}

	@Test
	public void putPersonTestIfError() throws Exception {
		Mockito.when(personService.putPerson(any(Person.class))).thenReturn(new Person());
		mockMvc.perform(
				put(String.format("/person?firstName=%s&lastName=%s&address=%s&city=%s&zip=%s&phone=%s&email=%s",
						person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(),
						person.getZip(), person.getPhone(), person.getEmail())))
				.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(nullValue())))
				.andExpect(jsonPath("lastName", is(nullValue()))).andExpect(jsonPath("address", is(nullValue())))
				.andExpect(jsonPath("city", is(nullValue()))).andExpect(jsonPath("zip", is(nullValue())))
				.andExpect(jsonPath("phone", is(nullValue()))).andExpect(jsonPath("email", is(nullValue())));
		verify(personService, Mockito.times(1)).putPerson(any(Person.class));
	}

	/**
	 * Post - Adds a new person to the database
	 * 
	 * @param - A String corresponding to the address of the fire station and an int
	 *          corresponding to the fire station number
	 */
	@Test
	public void postPersonTest() throws Exception {
		mockMvc.perform(
				post(String.format("/person?firstName=%s&lastName=%s&address=%s&city=%s&zip=%s&phone=%s&email=%s",
						person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(),
						person.getZip(), person.getPhone(), person.getEmail())))
				.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(person.getFirstName())))
				.andExpect(jsonPath("lastName", is(person.getLastName())))
				.andExpect(jsonPath("address", is(person.getAddress())))
				.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
				.andExpect(jsonPath("phone", is(person.getPhone())))
				.andExpect(jsonPath("email", is(person.getEmail())));
		verify(personService, Mockito.times(1)).postPerson(any(Person.class));
	}

	@Test
	public void postPersonTestIfOnlyName() throws Exception {
		Mockito.when(personService.postPerson(any(Person.class))).thenReturn(personOnlyName);
		mockMvc.perform(
				post(String.format("/person?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
				.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(person.getFirstName())))
				.andExpect(jsonPath("lastName", is(person.getLastName())))
				.andExpect(jsonPath("address", is(nullValue()))).andExpect(jsonPath("city", is(nullValue())))
				.andExpect(jsonPath("zip", is(nullValue()))).andExpect(jsonPath("phone", is(nullValue())))
				.andExpect(jsonPath("email", is(nullValue())));
		verify(personService, Mockito.times(1)).postPerson(any(Person.class));
	}

	@Test
	public void postPersonTestIfError() throws Exception {
		Mockito.when(personService.postPerson(any(Person.class))).thenReturn(new Person());
		mockMvc.perform(
				post(String.format("/person?firstName=%s&lastName=%s&address=%s&city=%s&zip=%s&phone=%s&email=%s",
						person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(),
						person.getZip(), person.getPhone(), person.getEmail())))
				.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(nullValue())))
				.andExpect(jsonPath("lastName", is(nullValue()))).andExpect(jsonPath("address", is(nullValue()))).andExpect(jsonPath("city", is(nullValue())))
				.andExpect(jsonPath("zip", is(nullValue()))).andExpect(jsonPath("phone", is(nullValue())))
				.andExpect(jsonPath("email", is(nullValue())));
		verify(personService, Mockito.times(1)).postPerson(any(Person.class));
	}

	/**
	 * Delete - Removes a person from the database
	 * 
	 * @param - An optional String corresponding to the address of the fire station
	 *          and an optional int corresponding to the new fire station number
	 */

	@Test
	public void deletePerson() throws Exception {
		mockMvc.perform(
				delete(String.format("/person?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].firstName", is(person.getFirstName())))
				.andExpect(jsonPath("$[0].lastName", is(person.getLastName())))
				.andExpect(jsonPath("$[0].address", is(person.getAddress())))
				.andExpect(jsonPath("$[0].city", is(person.getCity())))
				.andExpect(jsonPath("$[0].zip", is(person.getZip())))
				.andExpect(jsonPath("$[0].phone", is(person.getPhone())))
				.andExpect(jsonPath("$[0].email", is(person.getEmail())));
		verify(personService, Mockito.times(1)).deletePerson(anyString(), anyString());
	}

	@Test
	public void deletePersonIfError() throws Exception {
		Mockito.when(personService.deletePerson(anyString(), anyString()))
				.thenReturn(new ArrayList<Person>(Arrays.asList(new Person())));
		mockMvc.perform(
				delete(String.format("/person?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].firstName", is(nullValue())))
				.andExpect(jsonPath("$[0].lastName", is(nullValue())))
				.andExpect(jsonPath("$[0].address", is(nullValue()))).andExpect(jsonPath("$[0].city", is(nullValue())))
				.andExpect(jsonPath("$[0].zip", is(nullValue()))).andExpect(jsonPath("$[0].phone", is(nullValue())))
				.andExpect(jsonPath("$[0].email", is(nullValue())));
		verify(personService, Mockito.times(1)).deletePerson(anyString(), anyString());
	}
}
