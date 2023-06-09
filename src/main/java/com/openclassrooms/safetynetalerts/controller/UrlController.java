package com.openclassrooms.safetynetalerts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.openclassrooms.safetynetalerts.dto.ChildAlertURLDto;
import com.openclassrooms.safetynetalerts.dto.CommunityEmailURLDto;
import com.openclassrooms.safetynetalerts.dto.FireURLDto;
import com.openclassrooms.safetynetalerts.dto.FloodStationsURLDto;
import com.openclassrooms.safetynetalerts.dto.FloodStationsURLHome;
import com.openclassrooms.safetynetalerts.dto.PersonInfoURLDto;
import com.openclassrooms.safetynetalerts.dto.PersonInfoURLPerson;
import com.openclassrooms.safetynetalerts.dto.PhoneAlertURLDto;
import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.FireStationService;
import com.openclassrooms.safetynetalerts.service.MedicalRecordService;
import com.openclassrooms.safetynetalerts.service.PersonService;

@RestController
@EnableWebMvc
public class URLController {

	private static Logger logger = LoggerFactory.getLogger(URLController.class);

	@Autowired
	private FireStationService fireStationService;

	@Autowired
	private PersonService personService;

	@Autowired
	private MedicalRecordService medicalRecordService;

	@GetMapping(value = "/childAlert", params = "address")
	public ResponseEntity<ChildAlertURLDto> childAlertURL(@RequestParam(value = "address") String address) {

		/**
		 * Read - Get info on children living at a certain address and a list of all
		 * adults living with them
		 * 
		 * @param - A String corresponding to the address
		 * @return - A ChildAlertURLInfo object
		 */

		logger.debug(String.format("call of childAlertURL, args : %s", address));

		if (address == null) {
			logger.error(String.format("bad request on /childAlert, args : %s", address));
			return ResponseEntity.badRequest().build();
		}

		ChildAlertURLDto result = new ChildAlertURLDto();
		Person personToSearch = new Person();

		// get persons at the address
		personToSearch.setAddress(address);
		List<Person> persons = personService.getPerson(personToSearch);
		logger.debug(String.format("persons found in childAlertURL : %s", persons));
		if (persons == null) {
			logger.error(String.format("no persons found on /childAlert, args : %s", address));
			return ResponseEntity.notFound().build();
		}
		for (Person person : persons) {
			// get medical record of a person
			MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(person);
			logger.debug(
					String.format("medicalRecord found for person %s in childAlertURL : %s", person, medicalRecord));
			// split between children and adults
			if (medicalRecord != null && !medicalRecord.isAdult())
				result.addChild(person, medicalRecord.calculateAge());
			else
				result.addAdult(person);
			logger.debug(String.format("current result for childAlertURL : %s", result));
		}
		logger.info(String.format("successful request on /childAlert, args : %s", address));
		return ResponseEntity.ok().body(result);

	}

	@GetMapping(value = "/phoneAlert", params = "firestation")
	public ResponseEntity<PhoneAlertURLDto> phoneAlertURL(@RequestParam(value = "firestation") String firestation) {

		/**
		 * Read - Get the phone numbers of every person corresponding to the station
		 * number
		 * 
		 * @param - A String corresponding to the station number
		 * @return - A List<String> containing phone numbers
		 */

		logger.debug(String.format("call of phoneAlertURL, args : %s", firestation));

		if (firestation == null) {
			logger.error(String.format("bad request on /phoneAlert, args : %s", firestation));
			return ResponseEntity.badRequest().build();
		}

		PhoneAlertURLDto result = new PhoneAlertURLDto();
		FireStation fireStationToSearch = new FireStation();
		Person personToSearch = new Person();

		// get fire stations
		fireStationToSearch.setStation(firestation);
		List<FireStation> fireStations = fireStationService.getFireStation(fireStationToSearch);
		logger.debug(String.format("fireStations found in phoneAlertURL : %s", fireStations));
		if (fireStations == null) {
			logger.error(String.format("no firestations found on /phoneAlert, args : %s", firestation));
			return ResponseEntity.notFound().build();
		}
		for (FireStation fireStationInDB : fireStations) {
			// get persons
			personToSearch.setAddress(fireStationInDB.getAddress());
			List<Person> persons = personService.getPerson(personToSearch);
			logger.debug(
					String.format("persons found for fireStation %s in phoneAlertURL : %s", fireStationInDB, persons));
			if (persons != null)
				for (Person personInDB : persons) {
					if (personInDB.getPhone() != null)
						result.addPhone(personInDB.getPhone());
					logger.debug(String.format("current result for phoneAlertURL : %s", result));
				}
		}
		if (result.getPhones().isEmpty()) {
			logger.error(String.format("no phones found on /phoneAlert, args : %s", firestation));
			return ResponseEntity.notFound().build();
		}
		logger.info(String.format("successful request on /phoneAlert, args : %s", firestation));
		return ResponseEntity.ok().body(result);
	}

	@GetMapping(value = "fire", params = "address")
	public ResponseEntity<FireURLDto> fireURL(@RequestParam(value = "address") String address) {

		/**
		 * Read - Get info on every resident of the corresponding address
		 * 
		 * @param - A String corresponding to the address
		 * @return - A FireURLInfo object
		 */

		logger.debug(String.format("call of fireURL, args : %s", address));

		if (address == null) {
			logger.error(String.format("bad request on /fire, args : %s", address));
			return ResponseEntity.badRequest().build();
		}

		FireURLDto result = new FireURLDto();
		FireStation fireStationToSearch = new FireStation();
		Person personToSearch = new Person();

		// get fire station
		fireStationToSearch.setAddress(address);
		List<FireStation> fireStations = fireStationService.getFireStation(fireStationToSearch);
		logger.debug(String.format("fireStations found in fireURL : %s", fireStations));
		if (fireStations == null) {
			logger.error(String.format("no firestations found on /fire, args : %s", address));
			return ResponseEntity.notFound().build();
		}
		result.setFireStationNumber(fireStations.get(0).getStation());
		logger.debug(String.format("current result for fireURL : %s", result));
		// get persons
		personToSearch.setAddress(address);
		List<Person> persons = personService.getPerson(personToSearch);
		logger.debug(String.format("persons found for fireStation %s in fireURL : %s", fireStations.get(0), persons));
		if (persons == null || persons.isEmpty()) {
			logger.error(String.format("no persons found on /fire, args : %s", address));
			return ResponseEntity.notFound().build();
		}
		for (Person person : persons) {
			MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(person);
			logger.debug(String.format("medicalRecord found for person %s in fireURL : %s", person, medicalRecord));
			result.addPerson(person, medicalRecord);
			logger.debug(String.format("current result for fireURL : %s", result));
		}
		logger.info(String.format("successful request on /fire, args : %s", address));
		return ResponseEntity.ok().body(result);
	}

	@GetMapping(value = "flood/stations", params = "stations")
	public ResponseEntity<FloodStationsURLDto> floodStationsURL(
			@RequestParam(value = "stations") List<String> stations) {

		/**
		 * Read - Get a list of residents corresponding to a list of fire stations
		 * 
		 * @param - A List<String> corresponding to the station numbers
		 * @return - A FloodStationsURLInfo object
		 */

		logger.debug(String.format("call of floodStationsURL, args : %s", stations));

		if (stations == null || stations.isEmpty()) {
			logger.error(String.format("bad request on /flood/stations, args : %s", stations));
			return ResponseEntity.badRequest().build();
		}

		FloodStationsURLDto result = new FloodStationsURLDto();
		FireStation fireStationToSearch = new FireStation();
		Person personToSearch = new Person();

		for (String station : stations) {
			// get fire stations
			fireStationToSearch.setStation(station);
			List<FireStation> fireStations = fireStationService.getFireStation(fireStationToSearch);
			logger.debug(String.format("fireStations found in floodStationsURL : %s", fireStations));
			if (fireStations != null) {
				// get homes
				for (FireStation fireStation : fireStations) {
					FloodStationsURLHome home = new FloodStationsURLHome();
					home.setFireStationNumber(fireStation.getStation());
					// get residents
					personToSearch.setAddress(fireStation.getAddress());
					List<Person> persons = personService.getPerson(personToSearch);
					logger.debug(String.format("persons found for fireStation %s in floodStationsURL : %s", fireStation,
							persons));
					if (persons != null)
						for (Person person : persons) {
							MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(person);
							logger.debug(String.format("medicalRecord found for person %s in floodStationsURL : %s",
									person, medicalRecord));
							home.addResident(person, medicalRecord);
							logger.debug(String.format("current home for fireStation %s for floodStationsURL : %s",
									fireStation, result));
						}
					result.addHome(home);
					logger.debug(String.format("current result for floodStationsURL : %s", result));
				}
			}
		}
		if (result.getHomes().isEmpty()) {
			logger.error(String.format("no homes found on /flood/stations, args : %s", stations));
			return ResponseEntity.notFound().build();
		}
		logger.info(String.format("successful request on /flood/stations, args : %s", stations));
		return ResponseEntity.ok().body(result);
	}

	@GetMapping(value = "personInfo", params = { "firstName", "lastName" })
	public ResponseEntity<PersonInfoURLDto> personInfoURL(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName) {

		/**
		 * Read - Get info on a person
		 * 
		 * @param - Two String corresponding to the first and last name of the person
		 * @return - A List<personInfoURLPerson> object
		 */

		logger.debug(String.format("call of personInfoURL, args : %s, %s", firstName, lastName));

		if (firstName == null || lastName == null) {
			logger.error(String.format("bad request on /personInfo, args : %s, %s", firstName, lastName));
			return ResponseEntity.badRequest().build();
		}

		PersonInfoURLDto result = new PersonInfoURLDto();
		Person personToSearch = new Person();
		personToSearch.setFirstName(firstName);
		personToSearch.setLastName(lastName);
		// get persons
		List<Person> persons = personService.getPerson(personToSearch);
		logger.debug(String.format("persons found in personInfoURL : %s", persons));
		if (persons == null || persons.isEmpty()) {
			logger.error(String.format("no persons found on /personInfo, args : %s, %s", firstName, lastName));
			return ResponseEntity.notFound().build();
		}
		// get medical record
		for (Person person : persons) {
			MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(person);
			logger.debug(
					String.format("medicalRecord found for person %s in personInfoURL : %s", person, medicalRecord));
			result.addPerson(new PersonInfoURLPerson(person, medicalRecord));
			logger.debug(String.format("current result for personInfoURL : %s", result));
		}
		logger.info(String.format("successful request on /personInfo, args : %s, %s", firstName, lastName));
		return ResponseEntity.ok().body(result);
	}

	@GetMapping(value = "communityEmail", params = "city")
	public ResponseEntity<CommunityEmailURLDto> communityEmailURL(@RequestParam(value = "city") String city) {

		/**
		 * Read - Get info on a person
		 * 
		 * @param - Two String corresponding to the first and last name of the person
		 * @return - A List<personInfoURLPerson> object
		 */

		logger.debug(String.format("call of communityEmailURL, args : %s", city));

		if (city == null) {
			logger.error(String.format("bad request on /communityEmail, args : %s", city));
			return ResponseEntity.badRequest().build();
		}

		CommunityEmailURLDto result = new CommunityEmailURLDto();
		Person personToSearch = new Person();
		personToSearch.setCity(city);
		// get persons
		List<Person> persons = personService.getPerson(personToSearch);
		logger.debug(String.format("persons found in communityEmailURL : %s", persons));
		if (persons == null) {
			logger.error(String.format("no persons found on /communtiyEmail, args : %s", city));
			return ResponseEntity.notFound().build();
		}
		// get emails
		for (Person person : persons) {
			if (person.getEmail() != null)
				result.addEmail(person.getEmail());
			logger.debug(String.format("current result for communityEmailURL : %s", result));
		}
		if (result.getEmails().isEmpty()) {
			logger.error(String.format("no emails found on /communtiyEmail, args : %s", city));
			return ResponseEntity.notFound().build();
		}
		logger.info(String.format("successful request on /communityEmail, args : %s", city));
		return ResponseEntity.ok().body(result);
	}
}
