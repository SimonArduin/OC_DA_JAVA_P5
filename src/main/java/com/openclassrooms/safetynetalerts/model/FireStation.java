package com.openclassrooms.safetynetalerts.model;

public class FireStation {

	private String address;
	private String station;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String stationNumber) {
		this.station = stationNumber;
	}

	public FireStation(String address, String stationNumber) {
		super();
		this.address = address;
		this.station = stationNumber;
	}

	public FireStation() {
	}

	@Override
	public boolean equals(Object o) {
		if (o != null) {
			try {
				if (this.address != null || this.station != null) {
					if (this.address != null && this.address != ((FireStation) o).getAddress())
						return false;
					if (this.station != null && this.station != ((FireStation) o).getStation())
						return false;
					return true;
				}
				if (((FireStation) o).getAddress() == null && ((FireStation) o).getStation() == null)
					return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
}
