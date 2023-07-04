package com.openclassrooms.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;

import com.openclassrooms.safetynetalerts.model.Person;

public class ChildAlertURLDto {

	/*
	 * Collects all the informations to be returned at childAlert?address=<address>
	 */

	private List<ChildAlertURLChild> children = new ArrayList<ChildAlertURLChild>();
	private List<Person> adults = new ArrayList<Person>();

	public List<ChildAlertURLChild> getChildren() {
		return children;
	}

	public void addChild(Person person, int age) {
		this.children.add(new ChildAlertURLChild(person, age));
	}

	public List<Person> getAdults() {
		return adults;
	}

	public void addAdult(Person person) {
		this.adults.add(person);
	}
}