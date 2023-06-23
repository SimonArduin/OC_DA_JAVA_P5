package com.openclassrooms.safetynetalerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	public List<Person> getPerson(Person person) {
		return personRepository.get(person);
	}

	/*
	 * Updates the content of the specified person
	 * 
	 * If several persons have the same identifier as the specified person,
	 * only one of them will remain, and its fields will be updated
	 * 
	 * @param - A Person representing containing the new information
	 * 
	 * @return - true if the person was correctly saved
	 */

	public Person putPerson(Person person) {
		if (person == null)
			return null;
		boolean isInDB = false;
		Person personToPut = new Person();
		if (person.getFirstName() != null && person.getLastName() != null) {
			List<Person> personsInDB = personRepository.get(person);
			if (personsInDB == null)
				return null;
			for (Person personInDB : personsInDB) {
				if (personInDB.equals(person)) {
					isInDB = true;
					personToPut = personInDB;
					personRepository.delete(personInDB);
				}
			}
		}
		if (isInDB) {
			personToPut.update(person);
			return personRepository.save(personToPut);
		} else
			return null;
	}

	public Person postPerson(Person person) {
		return personRepository.save(person);
	}

	public Person deletePerson(Person person) {
		return personRepository.delete(person);
	}
}
