package com.openclassrooms.safetynetalerts.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.openclassrooms.safetynetalerts.dto.ChildAlertURLInfo;
import com.openclassrooms.safetynetalerts.dto.FireURLInfo;
import com.openclassrooms.safetynetalerts.dto.FloodStationsURLInfo;
import com.openclassrooms.safetynetalerts.dto.PersonInfoURLPerson;
import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.FireStationService;
import com.openclassrooms.safetynetalerts.service.MedicalRecordService;
import com.openclassrooms.safetynetalerts.service.PersonService;

@RestController
@EnableWebMvc
public class UrlController {

	@Autowired
	FireStationService fireStationService;

	@Autowired
	PersonService personService;

	@Autowired
	MedicalRecordService medicalRecordService;
	
	int ageMaxChild = 18;

	@GetMapping(value = "/childAlert", params = "address")
	public ChildAlertURLInfo childAlertURL(@RequestParam(value = "address") String address) {

		/**
		 * Read - Get info on children living at a certain address and a list of all
		 * adults living with them
		 * 
		 * @param - A String corresponding to the address
		 * @return - A ChildAlertURLInfo object
		 */

		ChildAlertURLInfo result = new ChildAlertURLInfo();
		Person personToSearch = new Person();

		// get persons at the address
		personToSearch.setAddress(address);
		ArrayList<Person> persons = new ArrayList<Person>(personService.getPerson(personToSearch));
		for (Person person : persons) {
			// get medical record of a person
			MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(person);
			// split between children and adults
			int age = medicalRecord.calculateAge();
			if (age <= ageMaxChild)
				result.addChild(person, age);
			else
				result.addAdult(person);
		}
		return result;
	}

	@GetMapping(value = "/phoneAlert", params = "firestation")
	public List<String> phoneAlertURL(@RequestParam(value = "firestation") String firestation) {

		/**
		 * Read - Get the phone numbers of every person corresponding to the station
		 * number
		 * 
		 * @param - A String corresponding to the station number
		 * @return - A List<String> containing phone numbers
		 */

		List<String> result = new ArrayList<String>();
		FireStation fireStationToSearch = new FireStation();
		Person personToSearch = new Person();

		// get fire stations
		fireStationToSearch.setStation(firestation);
		ArrayList<FireStation> fireStations = new ArrayList<FireStation>(
				fireStationService.getFireStation(fireStationToSearch));
		for (FireStation fireStationInDB : fireStations) {
			// get persons
			personToSearch.setAddress(fireStationInDB.getAddress());
			ArrayList<Person> persons = new ArrayList<Person>(personService.getPerson(personToSearch));
			for (Person personInDB : persons) {
				result.add(personInDB.getPhone());
			}
		}
		return result;
	}

	@GetMapping(value = "/fire", params = "address")
	public FireURLInfo fireURL(@RequestParam(value = "address") String address) {

		/**
		 * Read - Get info on every resident of the corresponding address
		 * 
		 * @param - A String corresponding to the address
		 * @return - A FireURLInfo object
		 */

		FireURLInfo result = new FireURLInfo();
		FireStation fireStationToSearch = new FireStation();
		Person personToSearch = new Person();

		// get fire station
		fireStationToSearch.setAddress(address);
		ArrayList<FireStation> fireStations = new ArrayList<FireStation>(
				fireStationService.getFireStation(fireStationToSearch));
		if (!fireStations.isEmpty()) {
			result.setFireStationNumber(fireStations.get(0).getStation());
		}
		// get persons
		personToSearch.setAddress(address);
		ArrayList<Person> persons = new ArrayList<Person>(personService.getPerson(personToSearch));
		for (Person person : persons) {
			MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(person);
			result.addPerson(person, medicalRecord);
		}
		return result;
	}

	@GetMapping(value = "flood/stations", params = "stations")
	public List<FloodStationsURLInfo> floodStationsURL(@RequestParam(value = "stations") List<String> stations) {

		/**
		 * Read - Get a list of residents corresponding to a list of fire stations
		 * 
		 * @param - A List<String> corresponding to the station numbers
		 * @return - A FloodStationsURLInfo object
		 */

		ArrayList<FloodStationsURLInfo> result = new ArrayList<FloodStationsURLInfo>();
		FireStation fireStationToSearch = new FireStation();
		Person personToSearch = new Person();

		for (String station : stations) {
			// get fire stations
			fireStationToSearch.setStation(station);
			ArrayList<FireStation> fireStations = new ArrayList<FireStation>(
					fireStationService.getFireStation(fireStationToSearch));
			// get homes
			for (FireStation fireStation : fireStations) {
				FloodStationsURLInfo home = new FloodStationsURLInfo();
				home.setFireStationNumber(fireStation.getStation());
				// get residents
				personToSearch.setAddress(fireStation.getAddress());
				ArrayList<Person> persons = new ArrayList<Person>(personService.getPerson(personToSearch));
				for (Person person : persons) {
					MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(person);
					home.addResident(person, medicalRecord);
				}
				result.add(home);
			}
		}
		return result;
	}

	@GetMapping(value = "personInfo", params = { "firstName", "lastName" })
	public List<PersonInfoURLPerson> personInfoURL(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName) {

		/**
		 * Read - Get info on a person
		 * 
		 * @param - Two String corresponding to the first and last name of the person
		 * @return - A List<personInfoURLPerson> object
		 */

		ArrayList<PersonInfoURLPerson> result = new ArrayList<PersonInfoURLPerson>();
		Person personToSearch = new Person();
		personToSearch.setFirstName(firstName);
		personToSearch.setLastName(lastName);
		// get persons
		ArrayList<Person> persons = new ArrayList<Person>(personService.getPerson(personToSearch));
		// get medical record
		for (Person person : persons) {
			MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(person);
			result.add(new PersonInfoURLPerson(person, medicalRecord));
		}
		return result;
	}

	@GetMapping(value = "communityEmail", params = "city")
	public List<String> communityEmailURL(@RequestParam(value = "city") String city) {

		/**
		 * Read - Get info on a person
		 * 
		 * @param - Two String corresponding to the first and last name of the person
		 * @return - A List<personInfoURLPerson> object
		 */

		ArrayList<String> result = new ArrayList<String>();
		Person personToSearch = new Person();
		personToSearch.setCity(city);
		// get persons
		ArrayList<Person> persons = new ArrayList<Person>(personService.getPerson(personToSearch));
		// get emails
		for (Person person : persons) {
			result.add(person.getEmail());
		}
		return result;
	}
}
