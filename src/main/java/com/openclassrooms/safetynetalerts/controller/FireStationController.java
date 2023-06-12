package com.openclassrooms.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.FireStationService;
import com.openclassrooms.safetynetalerts.service.MedicalRecordService;
import com.openclassrooms.safetynetalerts.service.PersonService;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@EnableWebMvc
public class FireStationController {

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

		//get fire stations
		fireStationToSearch.setStation(stationNumber);
		ArrayList<FireStation> fireStations = new ArrayList<FireStation>(
				fireStationService.getFireStation(fireStationToSearch));
		//for every person covered by each fire station
		for (FireStation fireStation : fireStations) {
			Person personToSearch = new Person();
			personToSearch.setAddress(fireStation.getAddress());
			ArrayList<Person> persons = new ArrayList<Person>(
					personService.getPerson(personToSearch));
			for (Person personInFireStation : persons) {
				// get first name, last name, address and phone number of the resident
				result.addPerson(new FireStationURLPerson(personInFireStation));
				// get medical record of the patient
				MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(new MedicalRecord(personInFireStation.getFirstName(),
						personInFireStation.getLastName()));
				// get birthdate
				LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(),
						DateTimeFormatter.ofPattern("MM/dd/yyyy"));
				// count as adult or child
				if (Period.between(birthdate, LocalDate.now()).getYears() > 18)
					result.addAdult();
				else
					result.addChild();
			}
		}
		return result;
	}

	@GetMapping("/firestation")
	public List<FireStation> getAllFireStations() {
		return fireStationService.getAllFireStations();
	}

	/**
	 * Put - Changes the station number of a firestation in the database
	 * 
	 * @param - A String corresponding to the address of the fire station and an
	 *          optional int corresponding to the new fire station number
	 */
	@PutMapping("/firestation")
	public FireStation putFireStation(@RequestParam(value = "address") String address,
			@RequestParam(value = "stationNumber") String stationNumber) {
		return fireStationService.putFireStation(new FireStation(address, stationNumber));
	}

	/**
	 * Post - Adds a new firestation to the database
	 * 
	 * @param - A String corresponding to the address of the fire station and an int
	 *          corresponding to the fire station number
	 */
	@PostMapping("/firestation")
	public FireStation postFireStation(@RequestParam(value = "address") String address,
			@RequestParam(value = "stationNumber", defaultValue = "0") String stationNumber) {
		return fireStationService.postFireStation(new FireStation(address, stationNumber));
	}

	/**
	 * Delete - Removes a firestation from the database
	 * 
	 * @param - An optional String corresponding to the address of the fire station
	 *          and an optional int corresponding to the new fire station number
	 */
	@DeleteMapping("/firestation")
	public List<FireStation> deleteFireStation(@RequestParam(value = "address") Optional<String> address,
			@RequestParam(value = "stationNumber") Optional<String> stationNumber) {
		FireStation fireStation = new FireStation();
		if (address.isPresent())
			fireStation.setAddress(address.get());
		if (stationNumber.isPresent())
			fireStation.setStation(stationNumber.get());
		return fireStationService.deleteFireStation(fireStation);
	}
}
