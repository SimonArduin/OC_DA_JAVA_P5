package com.openclassrooms.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;

public class PhoneAlertURLDto {

	/*
	 * Collects all the informations to be returned at /phoneAlert?firestation=<station_number>
	 */
	
	private List<String> phones = new ArrayList<String>();

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}
	
	public void addPhone(String phone) {
		this.phones.add(phone);
	}
}
