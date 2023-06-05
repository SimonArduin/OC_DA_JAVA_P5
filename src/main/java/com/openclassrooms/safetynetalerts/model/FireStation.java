package com.openclassrooms.safetynetalerts.model;

public class FireStation {

	private String address;
	private int station;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStation() {
		return station;
	}

	public void setStation(int stationNumber) {
		this.station = stationNumber;
	}

	public FireStation(String address, int stationNumber) {
		super();
		this.address = address;
		this.station = stationNumber;
	}

	public FireStation() {
	}

	@Override
	public boolean equals(Object o) {
		try {
		if (this.address.equals(((FireStation) o).getAddress()) && this.station == ((FireStation) o).getStation())
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
