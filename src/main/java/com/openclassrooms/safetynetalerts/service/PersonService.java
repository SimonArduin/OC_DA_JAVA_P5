package com.openclassrooms.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;

	public List<Person> getPersonByAddress(String address) {
		return personRepository.findByAddress(address);
	}

	public Person putPerson(Person person) {
		// TODO Auto-generated method stub
		// if field is empty, don't modify
		return null;
	}

	public Person putPersonAddress(String firstName, String lastName, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public Person putPersonCity(String firstName, String lastName, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public Person putPersonZip(String firstName, String lastName, Integer integer) {
		// TODO Auto-generated method stub
		return null;
	}

	public Person putPersonPhone(String firstName, String lastName, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public Person putPersonEmail(String firstName, String lastName, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public Person postPerson(Person person) {
		return personRepository.save(person);
	}

	public List<Person> deletePerson(String firstName, String lastName) {
		ArrayList<Person> result = new ArrayList<Person>();
		for(Person person : personRepository.findByFullName(firstName, lastName)) {
			if(personRepository.delete(person) != null)
				result.add(person);
		}
		return result;
	}
}
