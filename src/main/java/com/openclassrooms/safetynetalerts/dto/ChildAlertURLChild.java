package com.openclassrooms.safetynetalerts.dto;

import com.openclassrooms.safetynetalerts.model.Person;

public class ChildAlertURLChild {

	/*
	 * Collects the informations about a specific child to be returned at
	 * childAlert?address=<address>
	 */

	private String firstName;
	private String lastName;
	private int age;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public ChildAlertURLChild(Person person, int age) {
		super();
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.age = age;
	}
}