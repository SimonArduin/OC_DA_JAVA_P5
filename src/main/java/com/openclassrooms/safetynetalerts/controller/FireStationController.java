package com.openclassrooms.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.openclassrooms.safetynetalerts.dto.FireStationURLDto;
import com.openclassrooms.safetynetalerts.dto.FireStationURLPerson;
import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.FireStationService;
import com.openclassrooms.safetynetalerts.service.MedicalRecordService;
import com.openclassrooms.safetynetalerts.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@EnableWebMvc
@RequestMapping(path = "/firestation")
public class FireStationController {

	@Autowired
	private FireStationService fireStationService;

	@Autowired
	private PersonService personService;

	@Autowired
	private MedicalRecordService medicalRecordService;

	private int ageMaxChild = 18;

	/**
	 * Put - Changes the station number of a fireStation in the database
	 * 
	 * @param - A String corresponding to the address of the fireStation
	 * 
	 *          A String corresponding to the new fireStation number
	 * @return - A FireStation corresponding to the modified fireStation
	 */
	@PutMapping
	public FireStation putFireStation(@RequestParam(value = "address") String address,
			@RequestParam(value = "stationNumber") String stationNumber) {
		return fireStationService.putFireStation(new FireStation(address, stationNumber));
	}

	/**
	 * Post - Adds a new fire station to the database
	 * 
	 * @param - A String corresponding to the address of the fireStation
	 * 
	 *          A String corresponding to the new fireStation number
	 * @return - A FireStation corresponding to the new fireStation
	 */
	@PostMapping
	public FireStation postFireStation(@RequestParam(value = "address") String address,
			@RequestParam(value = "stationNumber") String stationNumber) {
		return fireStationService.postFireStation(new FireStation(address, stationNumber));
	}

	/**
	 * Delete - Removes a firestation from the database
	 * 
	 * @param - An optional String corresponding to the address of the fire station
	 *          and an optional int corresponding to the new fire station number
	 * @return - A FireStation corresponding to the removed fireStation
	 */
	@DeleteMapping
	public List<FireStation> deleteFireStation(@RequestParam(value = "address") Optional<String> address,
			@RequestParam(value = "stationNumber") Optional<String> stationNumber) {
		FireStation fireStation = new FireStation();
		if (address.isPresent())
			fireStation.setAddress(address.get());
		if (stationNumber.isPresent())
			fireStation.setStation(stationNumber.get());
		return fireStationService.deleteFireStation(fireStation);
	}

	@GetMapping(params = "stationNumber")
	public FireStationURLDto fireStationURL(String stationNumber) {

		/**
		 * Read - Get info on residents covered by a certain fireStation
		 * 
		 * @param - A String corresponding to the fire station number
		 * @return - A FireStationURLDto containing :
		 * 
		 *         -- a List<FireStationURLPerson> containing all persons covered by the
		 *         fireStation
		 * 
		 *         -- an int corresponding to the number of adults covered by the
		 *         fireStation
		 * 
		 *         -- an int corresponding to the number of children covered by the
		 *         fireStation
		 */

		FireStationURLDto result = new FireStationURLDto();
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
				if (medicalRecord.calculateAge() > ageMaxChild)
					result.addAdult();
				else
					result.addChild();
			}
		}
		return result;
	}
}
