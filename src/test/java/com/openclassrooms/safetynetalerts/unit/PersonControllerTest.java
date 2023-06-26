package com.openclassrooms.safetynetalerts.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.safetynetalerts.controller.PersonController;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.PersonService;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

	@Autowired
	PersonController personController;

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
		public void putPerson() throws Exception {
			ResponseEntity<Person> result = personController.putPerson(person);
			assertEquals(HttpStatus.valueOf(201), result.getStatusCode());
			assertEquals(person, result.getBody());
			verify(personService, Mockito.times(1)).putPerson(any(Person.class));
		}

		@Test
		public void putPersonTestIfNotInDB() throws Exception {
			Mockito.when(personService.putPerson(any(Person.class))).thenReturn(null);
			ResponseEntity<Person> result = personController.putPerson(person);
			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(personService, Mockito.times(1)).putPerson(any(Person.class));
		}

		@Test
		public void putPersonTestIfEmptyBody() throws Exception {
			ResponseEntity<Person> result = personController.putPerson(null);
			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(personService, Mockito.times(0)).putPerson(any(Person.class));
		}
	}

	@Nested
	class postPersonTests {
		
		@Test
		public void postPerson() throws Exception {
			ResponseEntity<Person> result = personController.postPerson(person);
			assertEquals(HttpStatus.valueOf(201), result.getStatusCode());
			assertEquals(person, result.getBody());
			verify(personService, Mockito.times(1)).postPerson(any(Person.class));
		}

		@Test
		public void postPersonTestIfNotInDB() throws Exception {
			Mockito.when(personService.postPerson(any(Person.class))).thenReturn(null);
			ResponseEntity<Person> result = personController.postPerson(person);
			assertEquals(HttpStatus.valueOf(409), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(personService, Mockito.times(1)).postPerson(any(Person.class));
		}

		@Test
		public void postPersonTestIfEmptyBody() throws Exception {
			ResponseEntity<Person> result = personController.postPerson(null);
			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(personService, Mockito.times(0)).postPerson(any(Person.class));
		}
	}
	
	@Nested
	class deletePersonTests {

		@Test
		public void deletePerson() throws Exception {
			ResponseEntity<Person> result = personController.deletePerson(person);
			assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
			assertEquals(person, result.getBody());
			verify(personService, Mockito.times(1)).deletePerson(any(Person.class));
		}

		@Test
		public void deletePersonTestIfNotInDB() throws Exception {
			Mockito.when(personService.deletePerson(any(Person.class))).thenReturn(null);
			ResponseEntity<Person> result = personController.deletePerson(person);
			assertEquals(HttpStatus.valueOf(404), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(personService, Mockito.times(1)).deletePerson(any(Person.class));
		}

		@Test
		public void deletePersonTestIfEmptyBody() throws Exception {
			ResponseEntity<Person> result = personController.deletePerson(null);
			assertEquals(HttpStatus.valueOf(400), result.getStatusCode());
			assertEquals(null, result.getBody());
			verify(personService, Mockito.times(0)).deletePerson(any(Person.class));
		}
	}
}