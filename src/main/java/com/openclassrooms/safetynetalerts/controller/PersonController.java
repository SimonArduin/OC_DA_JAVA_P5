package com.openclassrooms.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.PersonService;

@RestController
@EnableWebMvc
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService personService;

	/**
	 * Put - Changes the fields of a person in the database
	 * 
	 * @param - A Person containing the new information
	 * 
	 *          If one of this person's field is empty, the field of the person in
	 *          the database will remain the same
	 * @return - A Person corresponding to the modified person
	 */
	@PutMapping
	public ResponseEntity<Person> putPerson(@RequestBody Person person) {
		if (person == null)
			return ResponseEntity.badRequest().build();
		Person putPerson = personService.putPerson(person);
		if (putPerson == null || putPerson.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.created(null).body(putPerson);
	}

	/**
	 * Post - Adds a new person to the database
	 * 
	 * @param - A Person corresponding to the new person
	 * @return - A Person corresponding to the added person
	 */
	@PostMapping
	public ResponseEntity<Person> postPerson(@RequestBody Person person) {
		if (person == null)
			return ResponseEntity.badRequest().build();
		Person postedPerson = personService.postPerson(person);
		if (postedPerson == null || postedPerson.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.created(null).body(postedPerson);
	}

	/**
	 * Delete - Removes a person from the database
	 * 
	 * @param - A Person corresponding to the person to be deleted
	 * @return - A Person corresponding to the deleted person
	 */
	@DeleteMapping
	public ResponseEntity<Person> deletePerson(@RequestBody Person person) {
		if (person == null)
			return ResponseEntity.badRequest().build();
		Person deletedPerson = personService.deletePerson(person);
		if (deletedPerson == null || deletedPerson.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(deletedPerson);
	}
}
