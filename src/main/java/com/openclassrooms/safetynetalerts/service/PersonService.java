package com.openclassrooms.safetynetalerts.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.repository.PersonRepository;

@Service
public class PersonService {

	private static Logger logger = LoggerFactory.getLogger(PersonService.class);
	
	@Autowired
	private PersonRepository personRepository;
	
	public List<Person> getPerson(Person person) {
		logger.debug(String.format("call of getPerson, args : %s", person));
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
		logger.debug(String.format("call of putPerson, args : %s", person));
		if (person == null)
			return null;
		boolean isInDB = false;
		Person personToPut = new Person();
		if (person.getFirstName() != null && person.getLastName() != null) {
			List<Person> personsInDB = personRepository.get(person);
			logger.debug(String.format("persons found for person %s in personURL : %s", person,
					personsInDB));
			if (personsInDB == null)
				return null;
			for (Person personInDB : personsInDB) {
				if (personInDB.equals(person)) {
					isInDB = true;
					personToPut = personInDB;
					logger.debug(String.format("personToPut is %s", personToPut));
					personRepository.delete(personInDB);
					logger.debug(String.format("deleted person %s", personInDB));
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
		logger.debug(String.format("call of postPerson, args : %s", person));
		return personRepository.save(person);
	}

	public Person deletePerson(Person person) {
		logger.debug(String.format("call of deletePerson, args : %s", person));
		return personRepository.delete(person);
	}
}
