package com.openclassrooms.safetynetalerts.model;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jsoniter.annotation.JsonIgnore;


public class FireStation {

	@JsonIgnore
	private static Logger logger = LoggerFactory.getLogger(FireStation.class);

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

	@JsonIgnore
	public boolean isEmpty() {
		if (this.address == null && this.station == null)
			return true;
		return false;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null) {
			if (!object.getClass().equals(FireStation.class))
				return false;
			try {
				if (this.isEmpty()) {
					if (((FireStation) object).isEmpty())
						return true;
					else
						return false;
				} else {
					Field[] fields = FireStation.class.getDeclaredFields();
					for (int i = 0; i < fields.length; i++)
						if (fields[i].get(this) != null
								&& !fields[i].get(this).equals(fields[i].get(((FireStation) object))))
							return false;
					return true;
				}
			} catch (Exception e) {
				logger.error("exception in FireStation.equals()");
				e.printStackTrace();
			}
		}
		return false;
	}

	public void update(FireStation fireStation) {
		if (fireStation != null) {
			if (fireStation.getAddress() != null)
				this.address = fireStation.getAddress();
			if (fireStation.getStation() != null)
				this.station = fireStation.getStation();
		}
	}
}
