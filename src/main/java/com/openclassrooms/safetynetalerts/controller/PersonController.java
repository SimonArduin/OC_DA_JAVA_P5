package com.openclassrooms.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.PersonService;
import java.util.Optional;

@RestController
@EnableWebMvc
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService personService;

	/**
	 * Put - Changes the fields of a person in the database
	 * 
	 * @param - Two String corresponding to the name of the person
	 * 
	 *          An Optional<String> corresponding to the new address
	 * 
	 *          An Optional<String> corresponding to the new city
	 * 
	 *          An Optional<String> corresponding to the new zip
	 * 
	 *          An Optional<String> corresponding to the new phone
	 * 
	 *          An Optional<String> corresponding to the new email
	 * 
	 *          If one of the optional params has no value, the corresponding field
	 *          of the person will not be modified
	 * @return - A Person corresponding to the modified person
	 */
	@PutMapping
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
	 * 
	 *          An Optional<String> corresponding to the new address
	 * 
	 *          An Optional<String> corresponding to the new city
	 * 
	 *          An Optional<String> corresponding to the new zip
	 * 
	 *          An Optional<String> corresponding to the new phone
	 * 
	 *          An Optional<String> corresponding to the new email
	 * 
	 *          If one of the optional params has no value, the corresponding field
	 *          of the person will not be modified
	 * @return - A Person corresponding to the added person
	 */
	@PostMapping
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
	 * @return - A Person corresponding to the deleted person
	 */
	@DeleteMapping
	public Person deletePerson(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName) {
		return personService.deletePerson(new Person(firstName, lastName));
	}
}
