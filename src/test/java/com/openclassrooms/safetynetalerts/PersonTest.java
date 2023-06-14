package com.openclassrooms.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.model.Person;

@SpringBootTest(classes = Person.class)
public class PersonTest {

	Person person;
	Person personOther;
	Person personTest;

	@BeforeEach
	void setUpPerTest() {
		person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
		personOther = new Person("otherFirstName", "otherLastName", "otherAddress", "otherCity", "otherZip",
				"otherPhone", "otherEmail");
		personTest = new Person();
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class isEmptyTests {

		@Test
		void isEmptyTest() {
			assertFalse(person.isEmpty());
		}

		@Test
		void isEmptyTestIfEmpty() {
			assertTrue(personTest.isEmpty());
		}

		@Test
		void isEmptyTestIfOnlyAddress() {
			personTest.setAddress(person.getAddress());
			assertFalse(personTest.isEmpty());
		}
	}

	@Nested
	class equalsTests {

		@Test
		void equalsTest() {
			assertTrue(person.equals(person));
		}

		@Test
		void equalsTestIfOtherPerson() {
			assertFalse(person.equals(personOther));
		}

		@Test
		void equalsTestIfOnlyName() {
			personTest.setFirstName(person.getFirstName());
			personTest.setLastName(person.getLastName());
			assertTrue(personTest.equals(person));
		}

		@Test
		void equalsTestIfOnlyNameAndOtherPerson() {
			personTest.setFirstName(person.getFirstName());
			personTest.setLastName(person.getLastName());
			assertFalse(personTest.equals(personOther));
		}

		@Test
		void equalsTestIfFirstNull() {
			assertFalse(personTest.equals(person));
		}

		@Test
		void equalsTestIfSecondNull() {
			assertFalse(person.equals(personTest));
		}

		@Test
		void equalsTestIfBothNull() {
			assertTrue(personTest.equals(personTest));
		}

		@Test
		void equalsTestIfOnlyFirstName() {
			personTest.setFirstName(person.getFirstName());
			assertTrue(personTest.equals(person));
		}

		@Test
		void equalsTestIfOnlyFirstNameAndOtherPerson() {
			personTest.setFirstName(person.getFirstName());
			assertFalse(personTest.equals(personOther));
		}

		@Test
		void equalsTestIfOnlyLastName() {
			personTest.setLastName(person.getLastName());
			assertTrue(personTest.equals(person));
		}

		@Test
		void equalsTestIfOnlyLastNameAndOtherPerson() {
			personTest.setLastName(person.getLastName());
			assertFalse(personTest.equals(personOther));
		}

		@Test
		void equalsTestIfOnlyAddress() {
			personTest.setAddress(person.getAddress());
			assertTrue(personTest.equals(person));
		}

		@Test
		void equalsTestIfOnlyAddressAndOtherPerson() {
			personTest.setAddress(person.getAddress());
			assertFalse(personTest.equals(personOther));
		}

		@Test
		void equalsTestIfOnlyCity() {
			personTest.setCity(person.getCity());
			assertTrue(personTest.equals(person));
		}

		@Test
		void equalsTestIfOnlyCityAndOtherPerson() {
			personTest.setCity(person.getCity());
			assertFalse(personTest.equals(personOther));
		}

		@Test
		void equalsTestIfOnlyZip() {
			personTest.setZip(person.getZip());
			assertTrue(personTest.equals(person));
		}

		@Test
		void equalsTestIfOnlyZipAndOtherPerson() {
			personTest.setZip(person.getZip());
			assertFalse(personTest.equals(personOther));
		}

		@Test
		void equalsTestIfOnlyPhone() {
			personTest.setPhone(person.getPhone());
			assertTrue(personTest.equals(person));
		}

		@Test
		void equalsTestIfOnlyPhoneAndOtherPerson() {
			personTest.setPhone(person.getPhone());
			assertFalse(personTest.equals(personOther));
		}

		@Test
		void equalsTestIfOnlyEmail() {
			personTest.setEmail(person.getEmail());
			assertTrue(personTest.equals(person));
		}

		@Test
		void equalsTestIfOnlyEmailAndOtherPerson() {
			personTest.setEmail(person.getEmail());
			assertFalse(personTest.equals(personOther));
		}

		@Test
		void equalsTestIfAddressAndEmail() {
			personTest.setAddress(person.getAddress());
			personTest.setEmail(person.getEmail());
			assertTrue(personTest.equals(person));
		}

		@Test
		void equalsTestIfAddressAndEmailAndOtherPerson() {
			personTest.setAddress(person.getAddress());
			personTest.setEmail(person.getEmail());
			assertFalse(personTest.equals(personOther));
		}

		@Test
		void equalsTestIfAddressAndEmailAndOtherPersonEmail() {
			personTest.setAddress(person.getAddress());
			personTest.setEmail(personOther.getEmail());
			assertFalse(personTest.equals(personOther));
		}
	}

	@Nested
	class updateTests {

		@Test
		void updateTest() {
			personTest.setFirstName(person.getFirstName());
			personTest.setLastName(person.getLastName());
			personTest.setAddress(personOther.getAddress());
			personTest.setEmail(personOther.getEmail());
			assertTrue(person.update(personTest));
			assertEquals(personTest.getAddress(), person.getAddress());
			assertEquals(personTest.getEmail(), person.getEmail());
		}

		@Test
		void updateTestIfFirstNull() {
			Person personBefore = personTest;
			assertFalse(personTest.update(person));
			assertEquals(personBefore, personTest);
		}

		@Test
		void updateTestIfSecondNull() {
			Person personBefore = person;
			assertFalse(person.update(personTest));
			assertEquals(personBefore, person);
		}

		@Test
		void updateTestIfSame() {
			Person personBefore = person;
			personTest = person;
			assertTrue(person.update(personTest));
			assertEquals(personBefore, person);
		}

		@Test
		void updateTestIfNotSameName() {
			Person personBefore = person;
			assertFalse(person.update(personOther));
			assertEquals(personBefore, person);
		}
	}
}
