package com.openclassrooms.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;

public class FloodStationsURLDto {

	/*
	 * Collects the informations to be returned at flood/stations?stations=<a list
	 * of station_numbers>
	 */

	private List<FloodStationsURLHome> homes = new ArrayList<FloodStationsURLHome>();

	public List<FloodStationsURLHome> getHomes() {
		return homes;
	}

	public void addHome(FloodStationsURLHome home) {
		this.homes.add(home);
	}
}