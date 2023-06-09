package com.openclassrooms.safetynetalerts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

import java.util.List;

@RestController
@EnableWebMvc
@RequestMapping(path = "/firestation")
public class FireStationController {
	
	private static Logger logger = LoggerFactory.getLogger(FireStationController.class);

	@Autowired
	private FireStationService fireStationService;

	@Autowired
	private PersonService personService;

	@Autowired
	private MedicalRecordService medicalRecordService;

	/**
	 * Put - Changes the fields of a fireStation in the database
	 * 
	 * @param - A FireStation containing the new information
	 * 
	 *          If one of this fireStation's field is empty, the field of the
	 *          fireStation in the database will remain the same
	 * @return - A FireStation corresponding to the modified fireStation
	 */
	@PutMapping
	public ResponseEntity<FireStation> putFireStation(@RequestBody FireStation fireStation) {
		logger.debug(String.format("call of putFireStation, args : %s", fireStation));
		if (fireStation == null) {
			logger.error(String.format("bad request on /firestation PUT, args : %s", fireStation));
			return ResponseEntity.badRequest().build();
		}
		FireStation putFireStation = fireStationService.putFireStation(fireStation);
		if (putFireStation == null || putFireStation.isEmpty()) {
			logger.error(String.format("no firestation found on /firestation PUT, args : %s", fireStation));
			return ResponseEntity.notFound().build();
		}
		logger.info(String.format("successful request on /firestation PUT, args : %s", fireStation));
		return ResponseEntity.created(null).body(putFireStation);
	}

	/**
	 * Post - Adds a new fireStation to the database
	 * 
	 * @param - A FireStation corresponding to the new fireStation
	 * @return - A FireStation corresponding to the added fireStation
	 */
	@PostMapping
	public ResponseEntity<FireStation> postFireStation(@RequestBody FireStation fireStation) {
		logger.debug(String.format("call of postFireStation, args : %s", fireStation));
		if (fireStation == null) {
			logger.error(String.format("bad request on /firestation POST, args : %s", fireStation));
			return ResponseEntity.badRequest().build();
		}
		FireStation postedFireStation = fireStationService.postFireStation(fireStation);
		if (postedFireStation == null || postedFireStation.isEmpty()) {
			logger.error(String.format("conflict on /firestation POST, args : %s", fireStation));
			return ResponseEntity.status(409).build();
		}
		logger.info(String.format("successful request on /firestation POST, args : %s", fireStation));
		return ResponseEntity.created(null).body(postedFireStation);
	}

	/**
	 * Delete - Removes a fireStation from the database
	 * 
	 * @param - A FireStation corresponding to the fireStation to be deleted
	 * @return - A FireStation corresponding to the deleted fireStation
	 */
	@DeleteMapping
	public ResponseEntity<List<FireStation>> deleteFireStation(@RequestBody FireStation fireStation) {
		logger.debug(String.format("call of deleteFireStation, args : %s", fireStation));
		if (fireStation == null) {
			logger.error(String.format("bad request on /firestation DELETE, args : %s", fireStation));
			return ResponseEntity.badRequest().build();
		}
		List<FireStation> deletedFireStation = fireStationService.deleteFireStation(fireStation);
		if (deletedFireStation == null || deletedFireStation.isEmpty()) {
			logger.error(String.format("no firestation found on /firestation DELETE, args : %s", fireStation));
			return ResponseEntity.notFound().build();
		}
		logger.info(String.format("successful request on /firestation DELETE, args : %s", fireStation));
		return ResponseEntity.ok().body(deletedFireStation);
	}

	@GetMapping(params = "stationNumber")
	public ResponseEntity<FireStationURLDto> fireStationURL(String stationNumber) {

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
		
		logger.debug(String.format("call of fireStationURL, args : %s", stationNumber));

		if (stationNumber == null) {
			logger.error(String.format("bad request on /firestation GET, args : %s", stationNumber));
			return ResponseEntity.badRequest().build();
		}

		FireStationURLDto result = new FireStationURLDto();
		FireStation fireStationToSearch = new FireStation();

		// get fire stations
		fireStationToSearch.setStation(stationNumber);
		List<FireStation> fireStations = fireStationService.getFireStation(fireStationToSearch);
		logger.debug(String.format("fireStations found in fireStationURL : %s", fireStations));
		if (fireStations == null || fireStations.isEmpty()) {
			logger.error(String.format("no firestations found on /firestation GET, args : %s", stationNumber));
			return ResponseEntity.notFound().build();
		}
		// for every person covered by each fire station
		for (FireStation fireStation : fireStations) {
			Person personToSearch = new Person();
			personToSearch.setAddress(fireStation.getAddress());
			List<Person> persons = personService.getPerson(personToSearch);
			logger.debug(String.format("persons found for fireStation %s in fireStationURL : %s", fireStation, persons));
			if (persons != null && !persons.isEmpty())
				for (Person personInFireStation : persons) {
					// get first name, last name, address and phone number of the resident
					result.addPerson(new FireStationURLPerson(personInFireStation));
					// get medical record of the resident
					MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(personInFireStation);
					logger.debug(String.format("medicalRecord found for person %s in fireStationURL : %s", personInFireStation, medicalRecord));
					// count as adult or child
					if (medicalRecord != null && !medicalRecord.isAdult())
						result.addChild();
					else
						result.addAdult();
					logger.debug(String.format("current result for fireStationURL : %s", result));
				}
		}
		logger.info(String.format("successful request on /firestation GET, args : %s", stationNumber));
		return ResponseEntity.ok().body(result);
	}
}
