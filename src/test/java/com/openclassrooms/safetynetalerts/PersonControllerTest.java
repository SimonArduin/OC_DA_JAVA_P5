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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

	final Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone",
			"email");
	final Person personOnlyName = new Person(person.getFirstName(), person.getLastName());

	@BeforeEach
	private void setUp() {
		Mockito.when(personService.deletePerson(person)).thenReturn(person);
		Mockito.when(personService.putPerson(any(Person.class))).thenReturn(person);
		Mockito.when(personService.postPerson(any(Person.class))).thenReturn(person);
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class putPersonTests {

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
		public void putPersonTestIfNotInDB() throws Exception {
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

		@Test
		public void putPersonTestIfEmptyParams() throws Exception {
			Mockito.when(personService.putPerson(any(Person.class))).thenReturn(new Person());
			mockMvc.perform(
					put(String.format("/person?firstName=%s&lastName=%s&address=%s&city=%s&zip=%s&phone=%s&email=%s",
							null, null, null, null, null, null, null)))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(nullValue())))
					.andExpect(jsonPath("lastName", is(nullValue()))).andExpect(jsonPath("address", is(nullValue())))
					.andExpect(jsonPath("city", is(nullValue()))).andExpect(jsonPath("zip", is(nullValue())))
					.andExpect(jsonPath("phone", is(nullValue()))).andExpect(jsonPath("email", is(nullValue())));
			verify(personService, Mockito.times(1)).putPerson(any(Person.class));
		}

		@Test
		public void putPersonTestIfNoParams() throws Exception {
			Mockito.when(personService.putPerson(any(Person.class))).thenReturn(new Person());
			mockMvc.perform(
					put(String.format("/person")))
					.andExpect(status().is(400));
			verify(personService, Mockito.times(0)).putPerson(any(Person.class));
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
			mockMvc.perform(put(String.format("/person?firstName=%s&lastName=%s&address=%s&zip=%s",
					person.getFirstName(), person.getLastName(), person.getAddress(), person.getZip())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(person.getFirstName())))
					.andExpect(jsonPath("lastName", is(person.getLastName())))
					.andExpect(jsonPath("address", is(person.getAddress())))
					.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
					.andExpect(jsonPath("phone", is(person.getPhone())))
					.andExpect(jsonPath("email", is(person.getEmail())));
			verify(personService, Mockito.times(1)).putPerson(any(Person.class));
		}
	}

	@Nested
	class postPersonTests {

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
		public void postPersonTestIfNotInDB() throws Exception {
			Mockito.when(personService.postPerson(any(Person.class))).thenReturn(new Person());
			mockMvc.perform(
					post(String.format("/person?firstName=%s&lastName=%s&address=%s&city=%s&zip=%s&phone=%s&email=%s",
							person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(),
							person.getZip(), person.getPhone(), person.getEmail())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(nullValue())))
					.andExpect(jsonPath("lastName", is(nullValue()))).andExpect(jsonPath("address", is(nullValue())))
					.andExpect(jsonPath("city", is(nullValue()))).andExpect(jsonPath("zip", is(nullValue())))
					.andExpect(jsonPath("phone", is(nullValue()))).andExpect(jsonPath("email", is(nullValue())));
			verify(personService, Mockito.times(1)).postPerson(any(Person.class));
		}

		@Test
		public void postPersonTestIfEmptyParams() throws Exception {
			Mockito.when(personService.postPerson(any(Person.class))).thenReturn(new Person());
			mockMvc.perform(
					post(String.format("/person?firstName=%s&lastName=%s&address=%s&city=%s&zip=%s&phone=%s&email=%s",
							null, null, null, null, null, null, null)))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(nullValue())))
					.andExpect(jsonPath("lastName", is(nullValue()))).andExpect(jsonPath("address", is(nullValue())))
					.andExpect(jsonPath("city", is(nullValue()))).andExpect(jsonPath("zip", is(nullValue())))
					.andExpect(jsonPath("phone", is(nullValue()))).andExpect(jsonPath("email", is(nullValue())));
			verify(personService, Mockito.times(1)).postPerson(any(Person.class));
		}

		@Test
		public void postPersonTestIfNoParams() throws Exception {
			mockMvc.perform(
					post(String.format("/person")))
					.andExpect(status().is(400));
			verify(personService, Mockito.times(0)).postPerson(any(Person.class));
		}

		@Test
		public void postPersonTestIfOnlyName() throws Exception {
			Mockito.when(personService.postPerson(any(Person.class))).thenReturn(personOnlyName);
			mockMvc.perform(post(
					String.format("/person?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(person.getFirstName())))
					.andExpect(jsonPath("lastName", is(person.getLastName())))
					.andExpect(jsonPath("address", is(nullValue()))).andExpect(jsonPath("city", is(nullValue())))
					.andExpect(jsonPath("zip", is(nullValue()))).andExpect(jsonPath("phone", is(nullValue())))
					.andExpect(jsonPath("email", is(nullValue())));
			verify(personService, Mockito.times(1)).postPerson(any(Person.class));
		}

		@Test
		public void postPersonTestIfOnlyAddress() throws Exception {
			mockMvc.perform(post(String.format("/person?firstName=%s&lastName=%s&address=%s", person.getFirstName(),
					person.getLastName(), person.getAddress()))).andExpect(status().isOk())
					.andExpect(jsonPath("firstName", is(person.getFirstName())))
					.andExpect(jsonPath("lastName", is(person.getLastName())))
					.andExpect(jsonPath("address", is(person.getAddress())))
					.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
					.andExpect(jsonPath("phone", is(person.getPhone())))
					.andExpect(jsonPath("email", is(person.getEmail())));
			verify(personService, Mockito.times(1)).postPerson(any(Person.class));
		}

		@Test
		public void postPersonTestIfOnlyCity() throws Exception {
			mockMvc.perform(post(String.format("/person?firstName=%s&lastName=%s&city=%s", person.getFirstName(),
					person.getLastName(), person.getCity()))).andExpect(status().isOk())
					.andExpect(jsonPath("firstName", is(person.getFirstName())))
					.andExpect(jsonPath("lastName", is(person.getLastName())))
					.andExpect(jsonPath("address", is(person.getAddress())))
					.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
					.andExpect(jsonPath("phone", is(person.getPhone())))
					.andExpect(jsonPath("email", is(person.getEmail())));
			verify(personService, Mockito.times(1)).postPerson(any(Person.class));
		}

		@Test
		public void postPersonTestIfOnlyZip() throws Exception {
			mockMvc.perform(post(String.format("/person?firstName=%s&lastName=%s&zip=%s", person.getFirstName(),
					person.getLastName(), person.getZip()))).andExpect(status().isOk())
					.andExpect(jsonPath("firstName", is(person.getFirstName())))
					.andExpect(jsonPath("lastName", is(person.getLastName())))
					.andExpect(jsonPath("address", is(person.getAddress())))
					.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
					.andExpect(jsonPath("phone", is(person.getPhone())))
					.andExpect(jsonPath("email", is(person.getEmail())));
			verify(personService, Mockito.times(1)).postPerson(any(Person.class));
		}

		@Test
		public void postPersonTestIfOnlyPhone() throws Exception {
			mockMvc.perform(post(String.format("/person?firstName=%s&lastName=%s&phone=%s", person.getFirstName(),
					person.getLastName(), person.getPhone()))).andExpect(status().isOk())
					.andExpect(jsonPath("firstName", is(person.getFirstName())))
					.andExpect(jsonPath("lastName", is(person.getLastName())))
					.andExpect(jsonPath("address", is(person.getAddress())))
					.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
					.andExpect(jsonPath("phone", is(person.getPhone())))
					.andExpect(jsonPath("email", is(person.getEmail())));
			verify(personService, Mockito.times(1)).postPerson(any(Person.class));
		}

		@Test
		public void postPersonTestIfOnlyEmail() throws Exception {
			mockMvc.perform(post(String.format("/person?firstName=%s&lastName=%s&email=%s", person.getFirstName(),
					person.getLastName(), person.getEmail()))).andExpect(status().isOk())
					.andExpect(jsonPath("firstName", is(person.getFirstName())))
					.andExpect(jsonPath("lastName", is(person.getLastName())))
					.andExpect(jsonPath("address", is(person.getAddress())))
					.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
					.andExpect(jsonPath("phone", is(person.getPhone())))
					.andExpect(jsonPath("email", is(person.getEmail())));
			verify(personService, Mockito.times(1)).postPerson(any(Person.class));
		}

		@Test
		public void postPersonTestIfAddressAndZip() throws Exception {
			mockMvc.perform(post(String.format("/person?firstName=%s&lastName=%s&address=%s&zip=%s",
					person.getFirstName(), person.getLastName(), person.getAddress(), person.getZip())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(person.getFirstName())))
					.andExpect(jsonPath("lastName", is(person.getLastName())))
					.andExpect(jsonPath("address", is(person.getAddress())))
					.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
					.andExpect(jsonPath("phone", is(person.getPhone())))
					.andExpect(jsonPath("email", is(person.getEmail())));
			verify(personService, Mockito.times(1)).postPerson(any(Person.class));
		}
	}

	@Nested
	class deletePersonTests {

		/**
		 * Delete - Removes a person from the database
		 * 
		 * @param - An optional String corresponding to the address of the fire station
		 *          and an optional int corresponding to the new fire station number
		 */

		@Test
		public void deletePerson() throws Exception {
			mockMvc.perform(delete(
					String.format("/person?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(person.getFirstName())))
					.andExpect(jsonPath("lastName", is(person.getLastName())))
					.andExpect(jsonPath("address", is(person.getAddress())))
					.andExpect(jsonPath("city", is(person.getCity()))).andExpect(jsonPath("zip", is(person.getZip())))
					.andExpect(jsonPath("phone", is(person.getPhone())))
					.andExpect(jsonPath("email", is(person.getEmail())));
			verify(personService, Mockito.times(1)).deletePerson(any(Person.class));
		}

		@Test
		public void deletePersonTestIfNotInDB() throws Exception {
			Mockito.when(personService.deletePerson(any(Person.class))).thenReturn(new Person());
			mockMvc.perform(
					delete(String.format("/person?firstName=%s&lastName=%s&address=%s&city=%s&zip=%s&phone=%s&email=%s",
							person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(),
							person.getZip(), person.getPhone(), person.getEmail())))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(nullValue())))
					.andExpect(jsonPath("lastName", is(nullValue()))).andExpect(jsonPath("address", is(nullValue())))
					.andExpect(jsonPath("city", is(nullValue()))).andExpect(jsonPath("zip", is(nullValue())))
					.andExpect(jsonPath("phone", is(nullValue()))).andExpect(jsonPath("email", is(nullValue())));
			verify(personService, Mockito.times(1)).deletePerson(any(Person.class));
		}

		@Test
		public void deletePersonTestIfEmptyParams() throws Exception {
			Mockito.when(personService.deletePerson(any(Person.class))).thenReturn(new Person());
			mockMvc.perform(
					delete(String.format("/person?firstName=%s&lastName=%s&address=%s&city=%s&zip=%s&phone=%s&email=%s",
							null, null, null, null, null, null, null)))
					.andExpect(status().isOk()).andExpect(jsonPath("firstName", is(nullValue())))
					.andExpect(jsonPath("lastName", is(nullValue()))).andExpect(jsonPath("address", is(nullValue())))
					.andExpect(jsonPath("city", is(nullValue()))).andExpect(jsonPath("zip", is(nullValue())))
					.andExpect(jsonPath("phone", is(nullValue()))).andExpect(jsonPath("email", is(nullValue())));
			verify(personService, Mockito.times(1)).deletePerson(any(Person.class));
		}

		@Test
		public void deletePersonTestIfNoParams() throws Exception {
			mockMvc.perform(
					delete(String.format("/person")))
					.andExpect(status().is(400));
			verify(personService, Mockito.times(0)).deletePerson(any(Person.class));
		}
	}
}
