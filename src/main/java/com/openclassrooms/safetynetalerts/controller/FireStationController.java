package com.openclassrooms.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class FireStationController {

	@Autowired
	FireStationService fireStationService;

	@Autowired
	PersonService personService;

	@Autowired
	MedicalRecordService medicalRecordService;

	/**
	 * Read - Get info on residents covered by a certain fire station or get all
	 * fire stations
	 * 
	 * @param - An int corresponding to the fire station number
	 * @return - An Iterable object of info on residents or of FireStation full
	 *         filled
	 */
	@GetMapping("/firestation")
	public List<Object> getFireStations(@RequestParam(value = "stationNumber") Optional<Integer> stationNumber) {
		ArrayList<Object> result = new ArrayList<Object>();
		int numberOfAdults = 0;
		int numberOfChildren = 0;

		// if a station number is given, return info on residents
		if (stationNumber.isPresent()) {
			ArrayList<FireStation> fireStations = new ArrayList<FireStation>(
					fireStationService.getFireStation(stationNumber.get()));
			for (FireStation fireStation : fireStations) {
				ArrayList<Person> persons = new ArrayList<Person>(
						personService.getPersonByAddress(fireStation.getAddress()));
				for (Person person : persons) {
					// return first name, last name, address and phone number of the resident
					result.add(new String((String.format(
							"\"firstName\":\"%s\",\"lastName\":\"%s\",\"address\":\"%s\",\"phone\":\"%s\"",
							person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))));
					// get medical record of the patient
					MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByName(person.getFirstName(),
							person.getLastName());
					// get birthdate
					LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(),
							DateTimeFormatter.ofPattern("MM/dd/yyyy"));
					// count as adult or child
					if (Period.between(birthdate, LocalDate.now()).getYears() > 18)
						numberOfAdults++;
					else
						numberOfChildren++;
				}
			}
			// return number of adults and number of children
			result.add(new String((String.format("\"numberofadults\":\"%s\",\"numberofchildren\":\"%s\"",
					numberOfAdults, numberOfChildren))));
		}

		// is no station number is given, returns a list of all fire stations
		else {
			result = new ArrayList<Object>(fireStationService.getAllFireStations());
		}
		return result;
	}

	/**
	 * Put - Changes the station number of a firestation in the database
	 * 
	 * @param - A String corresponding to the address of the fire station and an
	 *          optional int corresponding to the new fire station number
	 */
	@PutMapping("/firestation")
	public void putFireStation(@RequestParam(value = "address") String address,
			@RequestParam(value = "stationNumber") int stationNumber) {
		fireStationService.putFireStation(new FireStation(address, stationNumber));
	}

	/**
	 * Post - Adds a new firestation to the database
	 * 
	 * @param - A String corresponding to the address of the fire station and an int
	 *          corresponding to the fire station number
	 */
	@PostMapping("/firestation")
	public void postFireStation(@RequestParam(value = "address") String address,
			@RequestParam(value = "stationNumber", defaultValue = "0") int stationNumber) {
		fireStationService.postFireStation(new FireStation(address, stationNumber));
	}

	/**
	 * Delete - Removes a firestation from the database
	 * 
	 * @param - An optional String corresponding to the address of the fire station
	 *          and an optional int corresponding to the new fire station number
	 */
	@DeleteMapping("/firestation")
	public void deleteFireStation(@RequestParam(value = "address") Optional<String> address,
			@RequestParam(value = "stationNumber") Optional<Integer> stationNumber) {
		if (address.isPresent())
			fireStationService.deleteFireStation(address.get());
		if (stationNumber.isPresent())
			fireStationService.deleteFireStation(stationNumber.get());
	}
}
