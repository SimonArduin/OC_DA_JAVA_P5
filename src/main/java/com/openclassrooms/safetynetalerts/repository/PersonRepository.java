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
		 * extract all fire station data
		 */
		ArrayList<Object> personData = new ArrayList<Object>(data.get("firestations"));
		/*
		 * add all fire stations to the list of fire stations
		 */
		for(Object o : personData) {
			Person person = objectMapper.convertValue(o, Person.class);
			persons.add(person);
		}
	}

	public boolean delete(Person person) {
		boolean isInDB = false;
		int i = 0;
		while (i < persons.size() && !isInDB) {
			Person personInDB = persons.get(i);
			if (personInDB.equals(person)) {
				isInDB = true;
				persons.remove(personInDB);
				break;
			}
			i++;
		}
		return isInDB;
	}

	public List<Person> findAll() {
		return persons;
	}

	public List<Person> findByFirstName(String firstName) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (firstName.equals(person.getFirstName()))
				result.add(person);
		}
		return result;
	}

	public List<Person> findByLastName(String lastName) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (lastName.equals(person.getLastName()))
				result.add(person);
		}
		return result;
	}

	public List<Person> findByAddress(String address) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (address.equals(person.getAddress()))
				result.add(person);
		}
		return result;
	}

	public List<Person> findByCity(String city) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (city.equals(person.getCity()))
				result.add(person);
		}
		return result;
	}

	public List<Person> findByZip(int zip) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (zip == person.getZip())
				result.add(person);
		}
		return result;
	}

	public List<Person> findByPhone(String phone) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (phone.equals(person.getPhone()))
				result.add(person);
		}
		return result;
	}

	public List<Person> findByEmail(String email) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (email.equals(person.getEmail()))
				result.add(person);
		}
		return result;
	}

	public boolean save(Person person) {
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
		if (!isInDB)
			persons.add(person);
		return !isInDB;
	}

}
