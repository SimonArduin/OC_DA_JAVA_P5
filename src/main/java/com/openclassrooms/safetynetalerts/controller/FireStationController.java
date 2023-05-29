package com.openclassrooms.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.service.FireStationService;

@RestController
public class FireStationController {
	
	@Autowired
	private FireStationService fireStationService;
	
	/**
	 * Read - Get all fire stations
	 * @return - An Iterable object of FireStation full filled
	 */
	@GetMapping("/firestation")
	public Iterable<FireStation> getFireStations() {
		return fireStationService.getFireStations();
	}
}
