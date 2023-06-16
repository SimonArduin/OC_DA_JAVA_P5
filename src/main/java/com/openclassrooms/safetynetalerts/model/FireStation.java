package com.openclassrooms.safetynetalerts.model;

import java.lang.reflect.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@JsonIgnore
	public Object[] getAllFields() {
		Field[] fields = FireStation.class.getDeclaredFields();
		Object[] result = new Object[fields.length];
		try {
			for (int i = 0; i < fields.length; i++)
				result[i] = fields[i].get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@JsonIgnore
	public void setAllFields(Object[] objects) {
		if (objects != null) {
			Field[] fields = FireStation.class.getDeclaredFields();
			if (fields.length == objects.length)
				try {
					for (int i = 0; i < fields.length; i++) {
						if (objects[i] != null && objects[i].getClass().equals(fields[i].getType()))
							fields[i].set(this, objects[i]);
						else
							fields[i].set(this, null);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	public FireStation() {
	}

	@JsonIgnore
	public boolean isEmpty() {
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].get(this) != null)
					return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
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
