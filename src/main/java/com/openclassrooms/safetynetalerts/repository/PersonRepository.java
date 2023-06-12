package com.openclassrooms.safetynetalerts.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.model.Person;

@Repository
public class PersonRepository {
	
	private DataBase dataBase;
	
	public PersonRepository() {
		this(DataBase.getDataBase());
	}
	
	public PersonRepository(DataBase dataBase) {
		this.dataBase = dataBase;
	}

	public Person delete(Person person) {
		return dataBase.removePerson(person);
	}

	public List<Person> getAll() {
		return dataBase.getPersons();
	}

	public List<Person> get(Person person) {
		return dataBase.getPersons(person);
	}
	
	public Person save(Person person) {
		return dataBase.addPerson(person);
	}

}
