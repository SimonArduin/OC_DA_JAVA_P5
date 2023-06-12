package com.openclassrooms.safetynetalerts.model;

import java.lang.reflect.Field;

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
		if (!object.getClass().equals(Person.class))
			return false;
		if (((Person) object) != null) {
			try {
				if (this.isEmpty()) {
					if (((Person) object).isEmpty())
						return true;
					else
						return false;
				} else {
					if (this.firstName != null && this.lastName != null) {
						if (this.firstName.equals(((Person) object).getFirstName())
								&& this.lastName.equals(((Person) object).getLastName()))
							return true;
						return false;
					} else {
						Field[] fields = this.getClass().getDeclaredFields();
						for (int i = 0; i < fields.length; i++)
							if (fields[i].get(this) != null
									&& !fields[i].get(this).equals(fields[i].get(((Person) object))))
								return false;
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
