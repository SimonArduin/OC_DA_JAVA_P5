package com.openclassrooms.safetynetalerts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static Logger logger = LoggerFactory.getLogger(PersonController.class);

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
		logger.debug(String.format("call of putPerson, args : %s", person));
		if (person == null) {
			logger.error(String.format("bad request on /person PUT, args : %s", person));
			return ResponseEntity.badRequest().build();
		}
		Person putPerson = personService.putPerson(person);
		if (putPerson == null || putPerson.isEmpty()) {
			logger.error(String.format("no person found on /person PUT, args : %s", person));
			return ResponseEntity.notFound().build();
		}
		logger.info(String.format("successful request on /person PUT, args : %s", person));
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
		logger.debug(String.format("call of postPerson, args : %s", person));
		if (person == null) {
			logger.error(String.format("bad request on /person POST, args : %s", person));
			return ResponseEntity.badRequest().build();
		}
		Person postedPerson = personService.postPerson(person);
		if (postedPerson == null || postedPerson.isEmpty()) {
			logger.error(String.format("no person found on /person POST, args : %s", person));
			return ResponseEntity.status(409).build();
		}
		logger.info(String.format("successful request on /person POST, args : %s", person));
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
		logger.debug(String.format("call of deletePerson, args : %s", person));
		if (person == null) {
			logger.error(String.format("bad request on /person DELETE, args : %s", person));
			return ResponseEntity.badRequest().build();
		}
		Person deletedPerson = personService.deletePerson(person);
		if (deletedPerson == null || deletedPerson.isEmpty()) {
			logger.error(String.format("no person found on /person DELETE, args : %s", person));
			return ResponseEntity.notFound().build();
		}
		logger.info(String.format("successful request on /person DELETE, args : %s", person));
		return ResponseEntity.ok().body(deletedPerson);
	}
}
