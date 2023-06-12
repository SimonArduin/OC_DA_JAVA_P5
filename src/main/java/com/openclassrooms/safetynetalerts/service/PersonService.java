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

	public Person putPerson(Person person) {
		boolean isInDB = false;
		for (Person personInDB : personRepository.get(person)) {
			if (personInDB.equals(person)) {
				isInDB = true;
				personRepository.delete(personInDB);
			}
		}
		if (isInDB)
			return personRepository.save(person);
		else
			return new Person();
	}

	public Person postPerson(Person person) {
		return personRepository.save(person);
	}

	public Person deletePerson(Person person) {
		return personRepository.delete(person);
	}
}
