package com.openclassrooms.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.repository.DataBase;
import com.openclassrooms.safetynetalerts.repository.PersonRepository;

@SpringBootTest(classes = PersonRepository.class)
public class PersonRepositoryTest {
	
	DataBase dataBase = mock(DataBase.class);

	PersonRepository personRepository = new PersonRepository(dataBase);
		
	final Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
	final ArrayList<Person> persons = new ArrayList<Person>(Arrays.asList(person));
	
	@BeforeEach
	private void setUpPerTest() {
		Mockito.when(dataBase.getPersons()).thenReturn(persons);
		Mockito.when(dataBase.getPersons(any(Person.class))).thenReturn(persons);
		Mockito.when(dataBase.addPerson(any(Person.class))).thenReturn(person);
		Mockito.when(dataBase.removePerson(any(Person.class))).thenReturn(person);
	}

	@Test
	void contextLoads() {
	}
	
	@Test
	public void deleteTest() {
		assertEquals(person, personRepository.delete(person));
		verify(dataBase, Mockito.times(1)).removePerson(any(Person.class));
	}
	
	@Test
	public void deleteTestIfNotInDB() {
		Mockito.when(dataBase.removePerson(any(Person.class))).thenReturn(new Person());
		assertEquals(new Person(), personRepository.delete(person));
		verify(dataBase, Mockito.times(1)).removePerson(any(Person.class));
	}

	@Test
	public void getAllTest() {
		ArrayList<Person> result = new ArrayList<Person>(personRepository.getAll());
		verify(dataBase, Mockito.times(1)).getPersons();
		assertEquals(persons.size(), result.size());
		assertTrue(result.contains(person));
	}

	@Test
	public void getTest() {
		ArrayList<Person> result = new ArrayList<Person>(personRepository.get(person));
		verify(dataBase, Mockito.times(1)).getPersons(any(Person.class));
		for(Person personInResult : result)
			assertTrue(person.equals(personInResult));
		assertEquals(persons.size(), result.size());
		assertTrue(result.contains(person));
	}
	
	@Test
	public void getTestIfNotInDB() {
		Mockito.when(dataBase.getPersons(any(Person.class))).thenReturn(new ArrayList<Person>());
		assertEquals(new ArrayList<Person>(), personRepository.get(person));
		verify(dataBase, Mockito.times(1)).getPersons(any(Person.class));
	}
	
	@Test
	public void saveTest() {
		Mockito.when(dataBase.addPerson(any(Person.class))).thenReturn(person);
		assertEquals(person, personRepository.save(person));
		verify(dataBase, Mockito.times(1)).addPerson(any(Person.class));
	}

	@Test
	public void saveTestIfAlreadyInDB() {
		Mockito.when(dataBase.addPerson(any(Person.class))).thenReturn(new Person());
		verify(dataBase, Mockito.times(0)).addPerson(any(Person.class));
	}
}
