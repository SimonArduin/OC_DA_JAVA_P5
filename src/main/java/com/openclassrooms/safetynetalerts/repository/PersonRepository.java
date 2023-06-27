package com.openclassrooms.safetynetalerts.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.model.Person;

@Repository
public class PersonRepository {

	private static Logger logger = LoggerFactory.getLogger(PersonRepository.class);
	
	private DataBase dataBase;
	
	public PersonRepository() {
		this(DataBase.getDataBase());
		logger.debug("call of PersonRepository()");
	}
	
	public PersonRepository(DataBase dataBase) {
		this.dataBase = dataBase;
		logger.debug(String.format("call of putPerson, args : %s", dataBase));
	}

	public Person delete(Person person) {
		logger.debug(String.format("call of delete, args : %s", person));
		return dataBase.removePerson(person);
	}

	public List<Person> get(Person person) {
		logger.debug(String.format("call of get, args : %s", person));
		return dataBase.getPersons(person);
	}
	
	public Person save(Person person) {
		logger.debug(String.format("call of save, args : %s", person));
		return dataBase.addPerson(person);
	}

}
