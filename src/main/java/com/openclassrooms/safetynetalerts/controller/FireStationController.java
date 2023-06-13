package com.openclassrooms.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.service.FireStationService;

import java.util.List;
import java.util.Optional;

@RestController
@EnableWebMvc
public class FireStationController {

	@Autowired
	FireStationService fireStationService;

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
