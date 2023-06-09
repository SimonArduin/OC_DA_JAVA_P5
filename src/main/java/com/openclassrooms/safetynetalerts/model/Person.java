package com.openclassrooms.safetynetalerts.model;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jsoniter.annotation.JsonIgnore;


public class Person {

	@JsonIgnore
	private static Logger logger = LoggerFactory.getLogger(Person.class);

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
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Person() {
	}

	@JsonIgnore
	public boolean isEmpty() {
		if (firstName == null && lastName == null && address == null && city == null && zip == null && phone == null
				&& email == null)
			return true;
		return false;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null) {
			if (!object.getClass().equals(Person.class))
				return false;
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
						Field[] fields = Person.class.getDeclaredFields();
						for (int i = 0; i < fields.length; i++)
							if (fields[i].get(this) != null
									&& !fields[i].get(this).equals(fields[i].get(((Person) object))))
								return false;
						return true;
					}
				}
			} catch (Exception e) {
				logger.error("exception in Person.equals()");
				e.printStackTrace();
			}
		}
		return false;
	}

	public void update(Person person) {
		if (person != null) {
			if (person.getFirstName() != null)
				this.firstName = person.getFirstName();
			if (person.getLastName() != null)
				this.lastName = person.getLastName();
			if (person.getAddress() != null)
				this.address = person.getAddress();
			if (person.getCity() != null)
				this.city = person.getCity();
			if (person.getZip() != null)
				this.zip = person.getZip();
			if (person.getPhone() != null)
				this.phone = person.getPhone();
			if (person.getEmail() != null)
				this.email = person.getEmail();
		}
	}
}
