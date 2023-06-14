package com.openclassrooms.safetynetalerts;

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
	final ArrayList<Person> emptyList = new ArrayList<Person>();

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
			Mockito.when(personRepository.get(any(Person.class))).thenReturn(emptyList);
			assertEquals(emptyList, personService.getPerson(person));
			verify(personRepository, Mockito.times(1)).get(any(Person.class));
		}
	}

	@Nested
	class putPersonTests {

		@Test
		public void putPersonTest() {
			assertEquals(person, personService.putPerson(person));
			verify(personRepository, Mockito.times(1)).get(any(Person.class));
			verify(personRepository, Mockito.times(1)).delete(person);
			verify(personRepository, Mockito.times(1)).save(person);
		}

		@Test
		public void putPersonTestIfNotInDB() {
			Mockito.when(personRepository.get(any(Person.class))).thenReturn(emptyList);
			assertEquals(emptyPerson, personService.putPerson(person));
			verify(personRepository, Mockito.times(1)).get(any(Person.class));
			verify(personRepository, Mockito.times(0)).delete(person);
			verify(personRepository, Mockito.times(0)).save(person);
		}
	}

	@Nested
	class postPersonTests {

		@Test
		public void postPersonTest() {
			assertEquals(person, personService.postPerson(person));
			verify(personRepository, Mockito.times(1)).save(person);
		}

		@Test
		public void postPersonTestIfAlreadyInDB() {
			Mockito.when(personRepository.save(any(Person.class))).thenReturn(emptyPerson);
			assertEquals(emptyPerson, personService.postPerson(person));
			verify(personRepository, Mockito.times(1)).save(person);
		}
	}

	@Nested
	class deletePersonTests {

		@Test
		public void deletePersonTest() {
			assertEquals(person, personService.deletePerson(person));
			verify(personRepository, Mockito.times(1)).delete(person);
		}

		@Test
		public void deletePersonTestIfNotInDB() {
			Mockito.when(personRepository.delete(any(Person.class))).thenReturn(emptyPerson);
			assertEquals(emptyPerson, personService.deletePerson(person));
			verify(personRepository, Mockito.times(1)).delete(person);
		}
	}
}
