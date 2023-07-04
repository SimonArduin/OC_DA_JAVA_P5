package com.openclassrooms.safetynetalerts.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.repository.PersonRepository;
import com.openclassrooms.safetynetalerts.service.PersonService;

@SpringBootTest(classes = PersonService.class)
public class PersonServiceTest {

	@Autowired
	PersonService personService;

	@MockBean
	PersonRepository personRepository;

	final Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone",
			"email");
	final Person emptyPerson = new Person();
	final ArrayList<Person> personList = new ArrayList<Person>(Arrays.asList(person));

	@BeforeEach
	private void SetUp() {
		Mockito.when(personRepository.get(any(Person.class))).thenReturn(personList);
		Mockito.when(personRepository.save(any(Person.class))).thenReturn(person);
		Mockito.when(personRepository.delete(any(Person.class))).thenReturn(person);
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class getPersonTests {

		@Test
		public void getPersonTest() {
			assertEquals(personList, personService.getPerson(person));
			verify(personRepository, Mockito.times(1)).get(any(Person.class));
		}

		@Test
		public void getPersonTestIfNotInDB() {
			Mockito.when(personRepository.get(any(Person.class))).thenReturn(null);
			assertEquals(null, personService.getPerson(person));
			verify(personRepository, Mockito.times(1)).get(any(Person.class));
		}

		@Test
		public void getPersonTestIfEmpty() {
			Mockito.when(personRepository.get(any(Person.class))).thenReturn(null);
			assertEquals(null, personService.getPerson(emptyPerson));
			verify(personRepository, Mockito.times(1)).get(any(Person.class));
		}

		@Test
		public void getPersonTestIfNull() {
			Mockito.when(personRepository.get(null)).thenReturn(null);
			assertEquals(null, personService.getPerson(null));
			verify(personRepository, Mockito.times(1)).get(null);
		}
	}

	@Nested
	class putPersonTests {

		@Test
		public void putPersonTest() {
			assertEquals(person, personService.putPerson(person));
			verify(personRepository, Mockito.times(1)).get(any(Person.class));
			verify(personRepository, Mockito.times(1)).delete(any(Person.class));
			verify(personRepository, Mockito.times(1)).save(any(Person.class));
		}

		@Test
		public void putPersonTestIfNotInDB() {
			Mockito.when(personRepository.get(any(Person.class))).thenReturn(null);
			assertEquals(null, personService.putPerson(person));
			verify(personRepository, Mockito.times(1)).get(any(Person.class));
			verify(personRepository, Mockito.times(0)).delete(any(Person.class));
			verify(personRepository, Mockito.times(0)).save(any(Person.class));
		}

		@Test
		public void putPersonTestIfEmpty() {
			Mockito.when(personRepository.get(any(Person.class))).thenReturn(null);
			assertEquals(null, personService.putPerson(emptyPerson));
			verify(personRepository, Mockito.times(0)).get(any(Person.class));
			verify(personRepository, Mockito.times(0)).delete(any(Person.class));
			verify(personRepository, Mockito.times(0)).save(any(Person.class));
		}

		@Test
		public void putPersonTestIfNull() {
			Mockito.when(personRepository.get(null)).thenReturn(null);
			assertEquals(null, personService.putPerson(null));
			verify(personRepository, Mockito.times(0)).get(any(Person.class));
			verify(personRepository, Mockito.times(0)).delete(any(Person.class));
			verify(personRepository, Mockito.times(0)).save(any(Person.class));
		}
	}

	@Nested
	class postPersonTests {

		@Test
		public void postPersonTest() {
			assertEquals(person, personService.postPerson(person));
			verify(personRepository, Mockito.times(1)).save(any(Person.class));
		}

		@Test
		public void postPersonTestIfAlreadyInDB() {
			Mockito.when(personRepository.save(any(Person.class))).thenReturn(null);
			assertEquals(null, personService.postPerson(person));
			verify(personRepository, Mockito.times(1)).save(any(Person.class));
		}

		@Test
		public void postPersonTestIfEmpty() {
			Mockito.when(personRepository.save(any(Person.class))).thenReturn(null);
			assertEquals(null, personService.postPerson(emptyPerson));
			verify(personRepository, Mockito.times(1)).save(any(Person.class));
		}

		@Test
		public void postPersonTestIfNull() {
			Mockito.when(personRepository.save(null)).thenReturn(null);
			assertEquals(null, personService.postPerson(null));
			verify(personRepository, Mockito.times(1)).save(null);
		}
	}

	@Nested
	class deletePersonTests {

		@Test
		public void deletePersonTest() {
			assertEquals(person, personService.deletePerson(person));
			verify(personRepository, Mockito.times(1)).delete(any(Person.class));
		}

		@Test
		public void deletePersonTestIfNotInDB() {
			Mockito.when(personRepository.delete(any(Person.class))).thenReturn(null);
			assertEquals(null, personService.deletePerson(person));
			verify(personRepository, Mockito.times(1)).delete(any(Person.class));
		}

		@Test
		public void deletePersonTestIfEmpty() {
			Mockito.when(personRepository.delete(any(Person.class))).thenReturn(null);
			assertEquals(null, personService.deletePerson(emptyPerson));
			verify(personRepository, Mockito.times(1)).delete(any(Person.class));
		}

		@Test
		public void deletePersonTestIfNull() {
			Mockito.when(personRepository.delete(null)).thenReturn(null);
			assertEquals(null, personService.deletePerson(null));
			verify(personRepository, Mockito.times(1)).delete(null);
		}
	}
}
