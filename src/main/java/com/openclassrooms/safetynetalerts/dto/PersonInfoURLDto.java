package com.openclassrooms.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;

public class PersonInfoURLDto {
	
	private List<PersonInfoURLPerson> persons = new ArrayList<PersonInfoURLPerson>();

	public List<PersonInfoURLPerson> getPersons() {
		return persons;
	}

	public void setPersons(List<PersonInfoURLPerson> persons) {
		this.persons = persons;
	}
	
	public void addPerson(PersonInfoURLPerson person) {
		this.persons.add(person);
	}

}
