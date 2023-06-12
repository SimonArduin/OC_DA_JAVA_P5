package com.openclassrooms.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.PersonService;
import java.util.Optional;

@RestController
@EnableWebMvc
public class PersonController {

	@Autowired
	PersonService personService;

	/**
	 * Put - Changes the fields of a person in the database
	 * 
	 * @param - Two String corresponding to the name of the person Four
	 *          Optional<String> and an Optional<Integer> corresponding to the info
	 *          of the person
	 * @return - A Person corresponding to the modified person
	 */
	@PutMapping("/person")
	public Person putPerson(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "address") Optional<String> address,
			@RequestParam(value = "city") Optional<String> city, @RequestParam(value = "zip") Optional<String> zip,
			@RequestParam(value = "phone") Optional<String> phone,
			@RequestParam(value = "email") Optional<String> email) {
		Person person = new Person(firstName, lastName);
		if (address.isPresent())
			person.setAddress(address.get());
		if (city.isPresent())
			person.setCity(city.get());
		if (zip.isPresent())
			person.setZip(zip.get());
		if (phone.isPresent())
			person.setPhone(phone.get());
		if (email.isPresent())
			person.setEmail(email.get());
		return personService.putPerson(person);
	}

	/**
	 * Post - Adds a new person to the database
	 * 
	 * @param - Two String corresponding to the name of the person Four
	 *          Optional<String> and an Optional<Integer> corresponding to the info
	 *          of the person
	 * @return - A Person corresponding to the added person
	 */
	@PostMapping("/person")
	public Person postPerson(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "address") Optional<String> address,
			@RequestParam(value = "city") Optional<String> city, @RequestParam(value = "zip") Optional<String> zip,
			@RequestParam(value = "phone") Optional<String> phone,
			@RequestParam(value = "email") Optional<String> email) {
		Person person = new Person(firstName, lastName);
		if (address.isPresent())
			person.setAddress(address.get());
		if (city.isPresent())
			person.setCity(city.get());
		if (zip.isPresent())
			person.setZip(zip.get());
		if (phone.isPresent())
			person.setPhone(phone.get());
		if (email.isPresent())
			person.setEmail(email.get());
		return personService.postPerson(person);
	}

	/**
	 * Delete - Removes a person from the database
	 * 
	 * @param - Two String objects corresponding to the first and last name of the
	 *          person
	 * @return - A List<Person> of all removed persons
	 */
	@DeleteMapping("/person")
	public Person deletePerson(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName) {
		return personService.deletePerson(new Person(firstName, lastName));
	}
}
