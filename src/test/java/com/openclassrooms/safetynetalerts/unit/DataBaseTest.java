package com.openclassrooms.safetynetalerts.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.repository.DataBase;

@SpringBootTest(classes = DataBase.class)
public class DataBaseTest {

	DataBase dataBase;

	final FireStation fireStation = new FireStation("address", "station");
	final FireStation fireStationOther = new FireStation("addressOther", "stationOther");
	final FireStation emptyFireStation = new FireStation();
	final ArrayList<FireStation> fireStationList = new ArrayList<FireStation>(Arrays.asList(fireStation));
	final ArrayList<FireStation> emptyFireStationList = new ArrayList<FireStation>();

	final Person person = new Person("firstName", "lastName", fireStation.getAddress(), "city", "zip", "phone",
			"email");
	final Person personOther = new Person("firstNameOther", "lastNameOther", fireStationOther.getAddress(), "cityOther", "zipOther", "phoneOther",
					"email");
	final Person emptyPerson = new Person();
	final ArrayList<Person> personList = new ArrayList<Person>(Arrays.asList(person));
	final ArrayList<Person> emptyPersonList = new ArrayList<Person>();

	final ArrayList<String> medications = new ArrayList<String>(Arrays.asList("medication 1", "medication 2"));
	final ArrayList<String> allergies = new ArrayList<String>(Arrays.asList("allergy 1", "allergy 2"));
	final MedicalRecord medicalRecord = new MedicalRecord("firstName", "lastName", "06/06/1966", medications,
			allergies);
	final MedicalRecord medicalRecordOther = new MedicalRecord("firstNameOther", "lastNameOther", "06/06/1996", null,
			null);
	final MedicalRecord emptyMedicalRecord = new MedicalRecord();
	final ArrayList<MedicalRecord> medicalRecordList = new ArrayList<MedicalRecord>(Arrays.asList(medicalRecord));
	final ArrayList<MedicalRecord> emptyMedicalRecordList = new ArrayList<MedicalRecord>();

	@BeforeEach
	void setUpPerTest() {
		dataBase = DataBase.setDataBase(fireStationList, personList, medicalRecordList);
	}

	@Test
	void contextLoads() {
	}

	@Nested
	class FireStationTest {

		@Nested
		class getFireStationsTests {

			@Test
			void getFireStationsTest() {
				assertEquals(fireStationList, dataBase.getFireStations());
			}

			@Test
			void getFireStationsByFireStationTest() {
				assertEquals(fireStationList, dataBase.getFireStations(fireStation));
			}

			@Test
			void getFireStationsByFireStationTestIfNotInDB() {
				assertEquals(emptyFireStationList, dataBase.getFireStations(fireStationOther));
			}

			@Test
			void getFireStationsByFireStationTestIfEmpty() {
				assertEquals(emptyFireStationList, dataBase.getFireStations(emptyFireStation));
			}

			@Test
			void getFireStationsByFireStationTestIfNull() {
				assertEquals(emptyFireStationList, dataBase.getFireStations(null));
			}
		}

		@Nested
		class addFireStationTests {

			@Test
			void addFireStationByFireStationTest() {
				assertEquals(fireStationOther, dataBase.addFireStation(fireStationOther));
			}

			@Test
			void addFireStationByFireStationTestIfAlreadyInDB() {
				assertEquals(emptyFireStation, dataBase.addFireStation(fireStation));
			}

			@Test
			void addFireStationByFireStationTestIfEmpty() {
				assertEquals(emptyFireStation, dataBase.addFireStation(emptyFireStation));
			}

			@Test
			void addFireStationByFireStationTestIfNull() {
				assertEquals(emptyFireStation, dataBase.addFireStation(null));
			}
		}

		@Nested
		class removeFireStationTests {

			@Test
			void removeFireStationByFireStationTest() {
				assertEquals(fireStation, dataBase.removeFireStation(fireStation));
			}

			@Test
			void removeFireStationByFireStationTestIfNotInDB() {
				assertEquals(emptyFireStation, dataBase.removeFireStation(fireStationOther));
			}

			@Test
			void removeFireStationByFireStationTestIfEmpty() {
				assertEquals(emptyFireStation, dataBase.removeFireStation(emptyFireStation));
			}

			@Test
			void removeFireStationByFireStationTestIfNull() {
				assertEquals(emptyFireStation, dataBase.removeFireStation(null));
			}
		}
	}

	@Nested
	class PersonTest {

		@Nested
		class getPersonsTests {

			@Test
			void getPersonsTest() {
				assertEquals(personList, dataBase.getPersons());
			}

			@Test
			void getPersonsByPersonTest() {
				assertEquals(personList, dataBase.getPersons(person));
			}

			@Test
			void getPersonsByPersonTestIfNotInDB() {
				assertEquals(emptyPersonList, dataBase.getPersons(personOther));
			}

			@Test
			void getPersonsByPersonTestIfEmpty() {
				assertEquals(emptyPersonList, dataBase.getPersons(emptyPerson));
			}

			@Test
			void getPersonsByPersonTestIfNull() {
				assertEquals(emptyPersonList, dataBase.getPersons(null));
			}
		}

		@Nested
		class addPersonTests {

			@Test
			void addPersonByPersonTest() {
				assertEquals(personOther, dataBase.addPerson(personOther));
			}

			@Test
			void addPersonByPersonTestIfAlreadyInDB() {
				assertEquals(emptyPerson, dataBase.addPerson(person));
			}

			@Test
			void addPersonByPersonTestIfEmpty() {
				assertEquals(emptyPerson, dataBase.addPerson(emptyPerson));
			}

			@Test
			void addPersonByPersonTestIfNull() {
				assertEquals(emptyPerson, dataBase.addPerson(null));
			}
		}

		@Nested
		class removePersonTests {

			@Test
			void removePersonByPersonTest() {
				assertEquals(person, dataBase.removePerson(person));
			}

			@Test
			void removePersonByPersonTestIfNotInDB() {
				assertEquals(emptyPerson, dataBase.removePerson(personOther));
			}

			@Test
			void removePersonByPersonTestIfEmpty() {
				assertEquals(emptyPerson, dataBase.removePerson(emptyPerson));
			}

			@Test
			void removePersonByPersonTestIfNull() {
				assertEquals(emptyPerson, dataBase.removePerson(null));
			}
		}
	}

	@Nested
	class MedicalRecordTest {

		@Nested
		class getMedicalRecordsTests {

			@Test
			void getMedicalRecordsTest() {
				assertEquals(medicalRecordList, dataBase.getMedicalRecords());
			}

			@Test
			void getMedicalRecordsByMedicalRecordTest() {
				assertEquals(medicalRecord, dataBase.getMedicalRecords(medicalRecord));
			}

			@Test
			void getMedicalRecordsByMedicalRecordTestIfNotInDB() {
				assertEquals(emptyMedicalRecord, dataBase.getMedicalRecords(medicalRecordOther));
			}

			@Test
			void getMedicalRecordsByMedicalRecordTestIfEmpty() {
				assertEquals(emptyMedicalRecord, dataBase.getMedicalRecords(emptyMedicalRecord));
			}

			@Test
			void getMedicalRecordsByMedicalRecordTestIfNull() {
				assertEquals(emptyMedicalRecord, dataBase.getMedicalRecords(null));
			}
		}

		@Nested
		class addMedicalRecordTests {

			@Test
			void addMedicalRecordByMedicalRecordTest() {
				assertEquals(medicalRecordOther, dataBase.addMedicalRecord(medicalRecordOther));
			}

			@Test
			void addMedicalRecordByMedicalRecordTestIfAlreadyInDB() {
				assertEquals(emptyMedicalRecord, dataBase.addMedicalRecord(medicalRecord));
			}

			@Test
			void addMedicalRecordByMedicalRecordTestIfEmpty() {
				assertEquals(emptyMedicalRecord, dataBase.addMedicalRecord(emptyMedicalRecord));
			}

			@Test
			void addMedicalRecordByMedicalRecordTestIfNull() {
				assertEquals(emptyMedicalRecord, dataBase.addMedicalRecord(null));
			}
		}

		@Nested
		class removeMedicalRecordTests {

			@Test
			void removeMedicalRecordByMedicalRecordTest() {
				assertEquals(medicalRecord, dataBase.removeMedicalRecord(medicalRecord));
			}

			@Test
			void removeMedicalRecordByMedicalRecordTestIfNotInDB() {
				assertEquals(emptyMedicalRecord, dataBase.removeMedicalRecord(medicalRecordOther));
			}

			@Test
			void removeMedicalRecordByMedicalRecordTestIfEmpty() {
				assertEquals(emptyMedicalRecord, dataBase.removeMedicalRecord(emptyMedicalRecord));
			}

			@Test
			void removeMedicalRecordByMedicalRecordTestIfNull() {
				assertEquals(emptyMedicalRecord, dataBase.removeMedicalRecord(null));
			}
		}
	}
}
