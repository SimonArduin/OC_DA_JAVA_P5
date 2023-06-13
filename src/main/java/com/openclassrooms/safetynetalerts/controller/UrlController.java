package com.openclassrooms.safetynetalerts.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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

	/*
	 * Collects all the informations to be returned at
	 * fireStation?stationNumber=<station_number>
	 */
	public class FireStationURLInfo {
		private List<FireStationURLPerson> persons = new ArrayList<FireStationURLPerson>();
		private Integer numberOfAdults = 0;
		private Integer numberOfChildren = 0;

		public List<FireStationURLPerson> getPersons() {
			return persons;
		}

		public void setPersons(List<FireStationURLPerson> persons) {
			this.persons = persons;
		}

		public void addPerson(FireStationURLPerson person) {
			this.persons.add(person);
		}

		public Integer getNumberOfAdults() {
			return numberOfAdults;
		}

		public void setNumberOfAdults(Integer numberOfAdults) {
			this.numberOfAdults = numberOfAdults;
		}

		public void addAdult() {
			this.numberOfAdults++;
		}

		public Integer getNumberOfChildren() {
			return numberOfChildren;
		}

		public void setNumberOfChildren(Integer numberOfChildren) {
			this.numberOfChildren = numberOfChildren;
		}

		public void addChild() {
			this.numberOfChildren++;
		}
	}

	/*
	 * Collects the informations about a specific person to be returned at
	 * fireStation?stationNumber=<station_number>
	 */
	public class FireStationURLPerson {

		// return first name, last name, address and phone number of the resident
		private String firstName;
		private String lastName;
		private String address;
		private String phone;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public FireStationURLPerson(Person person) {
			super();
			this.firstName = person.getFirstName();
			this.lastName = person.getLastName();
			this.address = person.getAddress();
			this.phone = person.getPhone();
		}
	}

	/*
	 * Collects all the informations to be returned at childAlert?address=<address>
	 */
	public class ChildAlertURLInfo {
		private List<ChildAlertURLChild> children = new ArrayList<ChildAlertURLChild>();
		private List<Person> adults = new ArrayList<Person>();

		public List<ChildAlertURLChild> getChildren() {
			return children;
		}

		public void setChildren(List<ChildAlertURLChild> children) {
			this.children = children;
		}

		public void addChild(Person person, int age) {
			this.children.add(new ChildAlertURLChild(person, age));
		}

		public List<Person> getAdults() {
			return adults;
		}

		public void setAdults(List<Person> adults) {
			this.adults = adults;
		}

		public void addAdult(Person person) {
			this.adults.add(person);
		}
	}

	/*
	 * Collects the informations about a specific child to be returned at
	 * childAlert?address=<address>
	 */
	public class ChildAlertURLChild {
		private String firstName;
		private String lastName;
		private int age;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public ChildAlertURLChild(Person person, int age) {
			super();
			this.firstName = person.getFirstName();
			this.lastName = person.getLastName();
			this.age = age;
		}
	}

	/**
	 * Read - Get info on residents covered by a certain fire station or get all
	 * fire stations
	 * 
	 * @param - An int corresponding to the fire station number
	 * @return - An Iterable object of info on residents or of FireStation full
	 *         filled
	 */
	@GetMapping(value = "/firestation", params = "stationNumber")
	public FireStationURLInfo FireStationURL(@RequestParam(value = "stationNumber") String stationNumber) {
		FireStationURLInfo result = new FireStationURLInfo();
		FireStation fireStationToSearch = new FireStation();

		// get fire stations
		fireStationToSearch.setStation(stationNumber);
		ArrayList<FireStation> fireStations = new ArrayList<FireStation>(
				fireStationService.getFireStation(fireStationToSearch));
		// for every person covered by each fire station
		for (FireStation fireStation : fireStations) {
			Person personToSearch = new Person();
			personToSearch.setAddress(fireStation.getAddress());
			ArrayList<Person> persons = new ArrayList<Person>(personService.getPerson(personToSearch));
			for (Person personInFireStation : persons) {
				// get first name, last name, address and phone number of the resident
				result.addPerson(new FireStationURLPerson(personInFireStation));
				// get medical record of the resident
				MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(
						new MedicalRecord(personInFireStation.getFirstName(), personInFireStation.getLastName()));
				// get birthdate
				if (medicalRecord != null && medicalRecord.getBirthdate() != null) {
					LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(),
							DateTimeFormatter.ofPattern("MM/dd/yyyy"));
					// count as adult or child
					if (Period.between(birthdate, LocalDate.now()).getYears() > 18)
						result.addAdult();
					else
						result.addChild();
				} else {
					// if no birthdate, count as adult
					result.addAdult();
				}
			}
		}
		return result;
	}

	@GetMapping(value = "/childAlert", params = "address")
	public ChildAlertURLInfo ChildAlertURL(@RequestParam(value = "address") String address) {
		ChildAlertURLInfo result = new ChildAlertURLInfo();
		Person personToSearch = new Person();

		// get persons at the address
		personToSearch.setAddress(address);
		ArrayList<Person> persons = new ArrayList<Person>(personService.getPerson(personToSearch));
		for (Person person : persons) {
			// get medical record of a person
			MedicalRecord medicalRecord = medicalRecordService
					.getMedicalRecord(new MedicalRecord(person.getFirstName(), person.getLastName()));
			// get birthdate
			if (medicalRecord != null && medicalRecord.getBirthdate() != null) {
				LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(),
						DateTimeFormatter.ofPattern("MM/dd/yyyy"));
				// split between children and adults
				int age = Period.between(birthdate, LocalDate.now()).getYears();
				if (age > 18)
					result.addAdult(person);
				else
					result.addChild(person, age);
			}
		}
		return result;
	}
}
