package com.openclassrooms.safetynetalerts.controller;

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

	/*
	 * Collects all the informations to be returned at fire?address=<address>
	 */
	public class FireURLInfo {
		private List<FireURLPerson> persons = new ArrayList<FireURLPerson>();
		private String fireStationNumber;

		public List<FireURLPerson> getPersons() {
			return persons;
		}

		public void setPersons(List<FireURLPerson> persons) {
			this.persons = persons;
		}

		public void addPerson(Person person, MedicalRecord medicalRecord) {
			this.persons.add(new FireURLPerson(person, medicalRecord));
		}

		public void addPerson(Person person) {
			this.persons.add(new FireURLPerson(person, new MedicalRecord()));
		}

		public String getFireStationNumber() {
			return fireStationNumber;
		}

		public void setFireStationNumber(String fireStationNumber) {
			this.fireStationNumber = fireStationNumber;
		}
	}

	/*
	 * Collects the informations about a specific person to be returned at
	 * fireStation?stationNumber=<station_number>
	 */
	public class FireURLPerson {

		// return first name, last name, address and phone number of the resident
		private String firstName;
		private String lastName;
		private String address;
		private String phone;
		private int age;
		private List<String> medications;
		private List<String> allergies;

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

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public List<String> getMedications() {
			return medications;
		}

		public void setMedications(List<String> medications) {
			this.medications = medications;
		}

		public List<String> getAllergies() {
			return allergies;
		}

		public void setAllergies(List<String> allergies) {
			this.allergies = allergies;
		}

		public FireURLPerson(Person person, MedicalRecord medicalRecord) {
			super();
			this.firstName = person.getFirstName();
			this.lastName = person.getLastName();
			this.address = person.getAddress();
			this.phone = person.getPhone();
			this.age = medicalRecord.getAge();
			this.medications = medicalRecord.getMedications();
			this.allergies = medicalRecord.getAllergies();
		}
	}

	/*
	 * Collects the informations about a specific home to be returned at
	 * flood/stations?stations=<a list of station_numbers>
	 */
	public class FloodStationsURLInfo {
		private List<FloodStationsURLPerson> persons = new ArrayList<FloodStationsURLPerson>();
		private String fireStationNumber;

		public List<FloodStationsURLPerson> getPersons() {
			return persons;
		}

		public void setPersons(List<FloodStationsURLPerson> persons) {
			this.persons = persons;
		}

		public String getFireStationNumber() {
			return fireStationNumber;
		}

		public void setFireStationNumber(String station) {
			this.fireStationNumber = station;
		}

		public void addResident(Person person, MedicalRecord medicalRecord) {
			this.persons.add(new FloodStationsURLPerson(person, medicalRecord));
		}

		public void addResident(Person person) {
			addResident(person, new MedicalRecord());
		}
	}

	/*
	 * Collects the informations about a specific person to be returned at
	 * flood/stations?stations=<a list of station_numbers>
	 */
	public class FloodStationsURLPerson {

		private String firstName;
		private String lastName;
		private String phone;
		private int age;
		private List<String> medications;
		private List<String> allergies;

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

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public List<String> getMedications() {
			return medications;
		}

		public void setMedications(List<String> medications) {
			this.medications = medications;
		}

		public List<String> getAllergies() {
			return allergies;
		}

		public void setAllergies(List<String> allergies) {
			this.allergies = allergies;
		}

		public FloodStationsURLPerson(Person person, MedicalRecord medicalRecord) {
			super();
			this.firstName = person.getFirstName();
			this.lastName = person.getLastName();
			this.phone = person.getPhone();
			this.age = medicalRecord.getAge();
			this.medications = medicalRecord.getMedications();
			this.allergies = medicalRecord.getAllergies();
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
				MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(personInFireStation);
				// count as adult or child
				if (medicalRecord.getAge() > 18)
					result.addAdult();
				else
					result.addChild();
			}
		}
		return result;
	}

	/**
	 * Read - Get info on children living at a certain address and a list of all
	 * adults living with them
	 * 
	 * @param - A String corresponding to the address
	 * @return - A ChildAlertURLInfo object
	 */

	@GetMapping(value = "/childAlert", params = "address")
	public ChildAlertURLInfo ChildAlertURL(@RequestParam(value = "address") String address) {
		ChildAlertURLInfo result = new ChildAlertURLInfo();
		Person personToSearch = new Person();

		// get persons at the address
		personToSearch.setAddress(address);
		ArrayList<Person> persons = new ArrayList<Person>(personService.getPerson(personToSearch));
		for (Person person : persons) {
			// get medical record of a person
			MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(person);
			// split between children and adults
			int age = medicalRecord.getAge();
			if (age < 18)
				result.addChild(person, age);
			else
				result.addAdult(person);
		}
		return result;
	}

	/**
	 * Read - Get the phone numbers of every person corresponding to the station
	 * number
	 * 
	 * @param - A String corresponding to the station number
	 * @return - A List<String> containing phone numbers
	 */

	@GetMapping(value = "/phoneAlert", params = "firestation")
	public List<String> PhoneAlertURL(@RequestParam(value = "firestation") String firestation) {
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

	/**
	 * Read - Get info on every resident of the corresponding address
	 * 
	 * @param - A String corresponding to the address
	 * @return - A FireURLInfo object
	 */

	@GetMapping(value = "/fire", params = "address")
	public FireURLInfo FireURL(@RequestParam(value = "address") String address) {
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

	/**
	 * Read - Get a list of residents corresponding to a list of fire stations
	 * 
	 * @param - A List<String> corresponding to the station numbers
	 * @return - A FloodStationsURLInfo object
	 */

	@GetMapping(value = "flood/stations", params = "stations")
	public List<FloodStationsURLInfo> FloodStationsURL(@RequestParam(value = "stations") List<String> stations) {
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
}
