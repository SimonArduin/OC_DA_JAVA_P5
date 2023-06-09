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
import java.util.List;
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
		if (address.isPresent() && city.isPresent() && zip.isPresent() && phone.isPresent() && email.isPresent())
			return personService.putPerson(
					new Person(firstName, lastName, address.get(), city.get(), zip.get(), phone.get(), email.get()));
		else {
			Person result = new Person(firstName, lastName);
			if (address.isPresent())
				result = personService.putPersonAddress(firstName, lastName, address.get());
			if (city.isPresent())
				result = personService.putPersonCity(firstName, lastName, city.get());
			if (zip.isPresent())
				result = personService.putPersonZip(firstName, lastName, zip.get());
			if (phone.isPresent())
				result = personService.putPersonPhone(firstName, lastName, phone.get());
			if (email.isPresent())
				result = personService.putPersonEmail(firstName, lastName, email.get());
			return result;
		}
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
		String resultAddress = new String();
		String resultCity = new String();
		String resultZip = new String();
		String resultPhone = new String();
		String resultEmail = new String();
		if (address.isPresent())
			resultAddress = address.get();
		if (city.isPresent())
			resultCity = city.get();
		if (zip.isPresent())
			resultZip = zip.get();
		if (phone.isPresent())
			resultPhone = phone.get();
		if (email.isPresent())
			resultEmail = email.get();
		return personService.postPerson(
				new Person(firstName, lastName, resultAddress, resultCity, resultZip, resultPhone, resultEmail));
	}

	/**
	 * Delete - Removes a person from the database
	 * 
	 * @param - Two String objects corresponding to the first and last name of the
	 *          person
	 * @return - A List<Person> of all removed persons
	 */
	@DeleteMapping("/person")
	public List<Person> deletePerson(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName) {
		return personService.deletePerson(firstName, lastName);
	}
}
