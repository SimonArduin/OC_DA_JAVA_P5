package com.openclassrooms.safetynetalerts.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.openclassrooms.safetynetalerts.model.Person;

public class PersonRepository {

	private ArrayList<Person> persons = new ArrayList<Person>(Arrays.asList(
			new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"),
			new Person("Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6513", "drk@email.com"),
			new Person("Tenley", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "tenz@email.com"),
			new Person("Roger", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"),
			new Person("Felicia", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6544", "jaboyd@email.com"),
			new Person("Jonanathan", "Marrack", "29 15th St", "Culver", 97451, "841-874-6513", "drk@email.com"),
			new Person("Tessa", "Carman", "834 Binoc Ave", "Culver", 97451, "841-874-6512", "tenz@email.com"),
			new Person("Peter", "Duncan", "644 Gershwin Cir", "Culver", 97451, "841-874-6512", "jaboyd@email.com"),
			new Person("Foster", "Shepard", "748 Townings Dr", "Culver", 97451, "841-874-6544", "jaboyd@email.com"),
			new Person("Tony", "Cooper", "112 Steppes Pl", "Culver", 97451, "841-874-6874", "tcoop@ymail.com"),
			new Person("Lily", "Cooper", "489 Manchester St", "Culver", 97451, "841-874-9845", "lily@email.com"),
			new Person("Sophia", "Zemicks", "892 Downing Ct", "Culver", 97451, "841-874-7878", "soph@email.com"),
			new Person("Warren", "Zemicks", "892 Downing Ct", "Culver", 97451, "841-874-7512", "ward@email.com"),
			new Person("Zach", "Zemicks", "892 Downing Ct", "Culver", 97451, "841-874-7512", "zarc@email.com"),
			new Person("Reginold", "Walker", "908 73rd St", "Culver", 97451, "841-874-8547", "reg@email.com"),
			new Person("Jamie", "Peters", "908 73rd St", "Culver", 97451, "841-874-7462", "jpeter@email.com"),
			new Person("Ron", "Peters", "112 Steppes Pl", "Culver", 97451, "841-874-8888", "jpeter@email.com"),
			new Person("Allison", "Boyd", "112 Steppes Pl", "Culver", 97451, "841-874-9888", "aly@imail.com"),
			new Person("Brian", "Stelzer", "947 E. Rose Dr", "Culver", 97451, "841-874-7784", "bstel@email.com"),
			new Person("Shawna", "Stelzer", "947 E. Rose Dr", "Culver", 97451, "841-874-7784", "ssanw@email.com"),
			new Person("Kendrik", "Stelzer", "947 E. Rose Dr", "Culver", 97451, "841-874-7784", "bstel@email.com"),
			new Person("Clive", "Ferguson", "748 Townings Dr", "Culver", 97451, "841-874-6741", "clivfd@ymail.com"),
			new Person("Eric", "Cadigan", "951 LoneTree Rd", "Culver", 97451, "841-874-7458", "gramps@email.com")));

	public void resetDataBase() {
		persons = new ArrayList<Person>(Arrays.asList(
				new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"),
				new Person("Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6513", "drk@email.com"),
				new Person("Tenley", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "tenz@email.com"),
				new Person("Roger", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"),
				new Person("Felicia", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6544", "jaboyd@email.com"),
				new Person("Jonanathan", "Marrack", "29 15th St", "Culver", 97451, "841-874-6513", "drk@email.com"),
				new Person("Tessa", "Carman", "834 Binoc Ave", "Culver", 97451, "841-874-6512", "tenz@email.com"),
				new Person("Peter", "Duncan", "644 Gershwin Cir", "Culver", 97451, "841-874-6512", "jaboyd@email.com"),
				new Person("Foster", "Shepard", "748 Townings Dr", "Culver", 97451, "841-874-6544", "jaboyd@email.com"),
				new Person("Tony", "Cooper", "112 Steppes Pl", "Culver", 97451, "841-874-6874", "tcoop@ymail.com"),
				new Person("Lily", "Cooper", "489 Manchester St", "Culver", 97451, "841-874-9845", "lily@email.com"),
				new Person("Sophia", "Zemicks", "892 Downing Ct", "Culver", 97451, "841-874-7878", "soph@email.com"),
				new Person("Warren", "Zemicks", "892 Downing Ct", "Culver", 97451, "841-874-7512", "ward@email.com"),
				new Person("Zach", "Zemicks", "892 Downing Ct", "Culver", 97451, "841-874-7512", "zarc@email.com"),
				new Person("Reginold", "Walker", "908 73rd St", "Culver", 97451, "841-874-8547", "reg@email.com"),
				new Person("Jamie", "Peters", "908 73rd St", "Culver", 97451, "841-874-7462", "jpeter@email.com"),
				new Person("Ron", "Peters", "112 Steppes Pl", "Culver", 97451, "841-874-8888", "jpeter@email.com"),
				new Person("Allison", "Boyd", "112 Steppes Pl", "Culver", 97451, "841-874-9888", "aly@imail.com"),
				new Person("Brian", "Stelzer", "947 E. Rose Dr", "Culver", 97451, "841-874-7784", "bstel@email.com"),
				new Person("Shawna", "Stelzer", "947 E. Rose Dr", "Culver", 97451, "841-874-7784", "ssanw@email.com"),
				new Person("Kendrik", "Stelzer", "947 E. Rose Dr", "Culver", 97451, "841-874-7784", "bstel@email.com"),
				new Person("Clive", "Ferguson", "748 Townings Dr", "Culver", 97451, "841-874-6741", "clivfd@ymail.com"),
				new Person("Eric", "Cadigan", "951 LoneTree Rd", "Culver", 97451, "841-874-7458", "gramps@email.com")));
	}

	public boolean delete(Person person) {
		boolean isInDB = false;
		int i = 0;
		while (i < persons.size() && !isInDB) {
			Person personInDB = persons.get(i);
			if (personInDB.equals(person)) {
				isInDB = true;
				persons.remove(personInDB);
				break;
			}
			i++;
		}
		return isInDB;
	}

	public List<Person> findAll() {
		return persons;
	}

	public List<Person> findByFirstName(String firstName) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (firstName.equals(person.getFirstName()))
				result.add(person);
		}
		return result;
	}

	public List<Person> findByLastName(String lastName) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (lastName.equals(person.getLastName()))
				result.add(person);
		}
		return result;
	}

	public List<Person> findByAddress(String address) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (address.equals(person.getAddress()))
				result.add(person);
		}
		return result;
	}

	public List<Person> findByCity(String city) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (city.equals(person.getCity()))
				result.add(person);
		}
		return result;
	}

	public List<Person> findByZip(int zip) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (zip == person.getZip())
				result.add(person);
		}
		return result;
	}

	public List<Person> findByPhone(String phone) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (phone.equals(person.getPhone()))
				result.add(person);
		}
		return result;
	}

	public List<Person> findByEmail(String email) {
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (email.equals(person.getEmail()))
				result.add(person);
		}
		return result;
	}

	public boolean save(Person person) {
		boolean isInDB = false;
		int i = 0;
		while (i < persons.size() && !isInDB) {
			Person personInDB = persons.get(i);
			if (personInDB.equals(person)) {
				isInDB = true;
				break;
			}
			i++;
		}
		if (!isInDB)
			persons.add(person);
		return !isInDB;
	}

}
