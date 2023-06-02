package com.openclassrooms.safetynetalerts.model;

public class Person {

	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private int zip;
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

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
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

	public Person(String firstName, String lastName, String address, String city, int zip, String phone, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		try {
			if (this.firstName.equals(((Person) o).getFirstName()) && this.lastName.equals(((Person) o).getLastName())
					&& this.address.equals(((Person) o).getAddress()) && this.city.equals(((Person) o).getCity())
					&& this.zip == ((Person) o).getZip() && this.phone.equals(((Person) o).getPhone())
					&& this.email.equals(((Person) o).getEmail()))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
