package com.openclassrooms.safetynetalerts.repository;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.model.Person;

@Repository
public class PersonRepository {

	private ArrayList<Person> persons;

	public PersonRepository() {
		try {
			this.resetDataBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetDataBase() throws Exception {
		persons = new ArrayList<Person>();
		Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/data.json"));
		ObjectMapper objectMapper = new ObjectMapper();
		
		/*
		 * get all data in data.json
		 */
		Map<String, List<Object>> data = objectMapper.readValue(reader,
				   new TypeReference<Map<String,  List<Object>>>() { } );
		/*
		 * extract all person data
		 */
		ArrayList<Object> personData = new ArrayList<Object>(data.get("persons"));
		/*
		 * add all persons to the list of persons
		 */
		for(Object o : personData) {
			Person person = objectMapper.convertValue(o, Person.class);
			persons.add(person);
		}
	}

	public Person delete(Person person) {
		boolean isInDB = false;
		int i = 0;
		while (i < persons.size() && !isInDB) {
			Person personInDB = persons.get(i);
			if (personInDB.equals(person)) {
				isInDB = true;
				persons.remove(personInDB);
				return(personInDB);
			}
			i++;
		}
		return new Person();
	}

	public List<Person> getAll() {
		return persons;
	}
	
	public List<Person> get(Person person) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person personInDB : persons) {
			if (person.equals(personInDB))
				result.add(personInDB);
		}
		return result;
	}

	public Person save(Person person) {
		boolean isInDB = false;
		int i = 0;
		while (i < persons.size() && !isInDB) {
			Person personInDB = persons.get(i);
			if (personInDB.equals(person)) {
				isInDB = true;
				break;
			}
			i++;
		}
		if (!isInDB) {
			persons.add(person);
			return person;
		}
		return new Person();
	}

}
