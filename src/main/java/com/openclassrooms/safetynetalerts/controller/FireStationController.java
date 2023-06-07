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
import java.util.Arrays;
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
	@GetMapping("/firestation")
	public Object getFireStation(@RequestParam(value = "stationNumber") Optional<Integer> stationNumber) {
		FireStationURLInfo result = new FireStationURLInfo();

		// if a station number is given, return info on residents
		if (stationNumber.isPresent()) {
			ArrayList<FireStation> fireStations = new ArrayList<FireStation>(
					fireStationService.getFireStation(stationNumber.get()));
			for (FireStation fireStation : fireStations) {
				ArrayList<Person> persons = new ArrayList<Person>(
						personService.getPersonByAddress(fireStation.getAddress()));
				for (Person person : persons) {
					// return first name, last name, address and phone number of the resident
					result.addPerson(new FireStationURLPerson(person));
					// get medical record of the patient
					MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByName(person.getFirstName(),
							person.getLastName());
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

		// is no station number is given, returns a list of all fire stations
		else {
			return new ArrayList<Object>(fireStationService.getAllFireStations());
		}
	}

	/**
	 * Put - Changes the station number of a firestation in the database
	 * 
	 * @param - A String corresponding to the address of the fire station and an
	 *          optional int corresponding to the new fire station number
	 */
	@PutMapping("/firestation")
	public FireStation putFireStation(@RequestParam(value = "address") String address,
			@RequestParam(value = "stationNumber") int stationNumber) {
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
			@RequestParam(value = "stationNumber", defaultValue = "0") int stationNumber) {
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
			@RequestParam(value = "stationNumber") Optional<Integer> stationNumber) {
		if (address.isPresent() && stationNumber.isPresent()) {
			return fireStationService.deleteFireStation(new FireStation(address.get(), stationNumber.get()));
		} else {
			if (address.isPresent()) {
				return fireStationService.deleteFireStation(address.get());
			}
			if (stationNumber.isPresent()) {
				return fireStationService.deleteFireStation(stationNumber.get());
			} else
				return new ArrayList<FireStation>(Arrays.asList(new FireStation()));
		}
	}
}
