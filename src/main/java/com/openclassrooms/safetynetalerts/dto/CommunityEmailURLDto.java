package com.openclassrooms.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;

public class CommunityEmailURLDto {
	
	private List<String> emails = new ArrayList<String>();

	public List<String> getEmails() {
		return emails;
	}
	
	public void addEmail(String email) {
		this.emails.add(email);
	}

}
