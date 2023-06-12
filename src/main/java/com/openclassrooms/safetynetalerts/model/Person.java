package com.openclassrooms.safetynetalerts.model;

public class Person {

	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String zip;
	private String phone;
	private String email;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Person(String firstName, String lastName, String address, String city, String zip, String phone,
			String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
	}

	public Person(String firstName, String lastName) {
		this("John", "Boyd", null, null, null, null, null);
	}

	public Person() {
	}

	@Override
	public boolean equals(Object o) {
		if (o != null) {
			try {
				if (this.firstName != null || this.lastName != null) {
					if (this.firstName != null && this.firstName != ((Person) o).getFirstName())
						return false;
					if (this.lastName != null && this.lastName != ((Person) o).getLastName())
						return false;
					return true;
				}
				if (((Person) o).getFirstName() == null && ((Person) o).getLastName() == null)
					return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
}
