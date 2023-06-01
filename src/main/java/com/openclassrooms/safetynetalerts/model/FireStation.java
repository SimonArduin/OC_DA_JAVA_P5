package com.openclassrooms.safetynetalerts.model;

public class FireStation {

	private String address;
	private int stationNumber;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}

	public FireStation(String address, int stationNumber) {
		super();
		this.address = address;
		this.stationNumber = stationNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this.address.equals(((FireStation) o).getAddress()) && this.stationNumber == ((FireStation) o).getStationNumber())
			return true;
		else
			return false;
	}
}
