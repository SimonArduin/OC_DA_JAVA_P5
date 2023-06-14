package com.openclassrooms.safetynetalerts;

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
	final ArrayList<FireStation> fireStationList = new ArrayList<FireStation>(Arrays.asList(fireStation));
	final ArrayList<FireStation> emptyFireStationList = new ArrayList<FireStation>();

	final Person person = new Person("firstName", "lastName", fireStation.getAddress(), "city", "zip", "phone",
			"email");
	final Person personOther = new Person("firstNameOther", "lastNameOther", fireStationOther.getAddress(), "cityOther", "zipOther", "phoneOther",
					"email");
	final ArrayList<Person> personList = new ArrayList<Person>(Arrays.asList(person));

	final ArrayList<String> medications = new ArrayList<String>(Arrays.asList("medication 1", "medication 2"));
	final ArrayList<String> allergies = new ArrayList<String>(Arrays.asList("allergy 1", "allergy 2"));
	final MedicalRecord medicalRecord = new MedicalRecord("firstName", "lastName", "06/06/1966", medications,
			allergies);
	final MedicalRecord medicalRecordOther = new MedicalRecord("firstNameOther", "lastNameOther", "06/06/1996", null,
			null);
	final ArrayList<MedicalRecord> medicalRecordList = new ArrayList<MedicalRecord>(Arrays.asList(medicalRecord));

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
				assertEquals(null, dataBase.getFireStations(fireStationOther));
			}

			@Test
			void getFireStationsByFireStationTestIfEmpty() {
				assertEquals(null, dataBase.getFireStations(new FireStation()));
			}

			@Test
			void getFireStationsByFireStationTestIfNull() {
				assertEquals(null, dataBase.getFireStations(null));
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
				assertEquals(null, dataBase.addFireStation(fireStation));
			}

			@Test
			void addFireStationByFireStationTestIfEmpty() {
				assertEquals(null, dataBase.addFireStation(new FireStation()));
			}

			@Test
			void addFireStationByFireStationTestIfNull() {
				assertEquals(null, dataBase.addFireStation(null));
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
				assertEquals(null, dataBase.removeFireStation(fireStationOther));
			}

			@Test
			void removeFireStationByFireStationTestIfEmpty() {
				assertEquals(null, dataBase.removeFireStation(new FireStation()));
			}

			@Test
			void removeFireStationByFireStationTestIfNull() {
				assertEquals(null, dataBase.removeFireStation(null));
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
				assertEquals(null, dataBase.getPersons(personOther));
			}

			@Test
			void getPersonsByPersonTestIfEmpty() {
				assertEquals(null, dataBase.getPersons(new Person()));
			}

			@Test
			void getPersonsByPersonTestIfNull() {
				assertEquals(null, dataBase.getPersons(null));
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
				assertEquals(null, dataBase.addPerson(person));
			}

			@Test
			void addPersonByPersonTestIfEmpty() {
				assertEquals(null, dataBase.addPerson(new Person()));
			}

			@Test
			void addPersonByPersonTestIfNull() {
				assertEquals(null, dataBase.addPerson(null));
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
				assertEquals(null, dataBase.removePerson(personOther));
			}

			@Test
			void removePersonByPersonTestIfEmpty() {
				assertEquals(null, dataBase.removePerson(new Person()));
			}

			@Test
			void removePersonByPersonTestIfNull() {
				assertEquals(null, dataBase.removePerson(null));
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
				assertEquals(null, dataBase.getMedicalRecords(medicalRecordOther));
			}

			@Test
			void getMedicalRecordsByMedicalRecordTestIfEmpty() {
				assertEquals(null, dataBase.getMedicalRecords(new MedicalRecord()));
			}

			@Test
			void getMedicalRecordsByMedicalRecordTestIfNull() {
				assertEquals(null, dataBase.getMedicalRecords(null));
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
				assertEquals(null, dataBase.addMedicalRecord(medicalRecord));
			}

			@Test
			void addMedicalRecordByMedicalRecordTestIfEmpty() {
				assertEquals(null, dataBase.addMedicalRecord(new MedicalRecord()));
			}

			@Test
			void addMedicalRecordByMedicalRecordTestIfNull() {
				assertEquals(null, dataBase.addMedicalRecord(null));
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
				assertEquals(null, dataBase.removeMedicalRecord(medicalRecordOther));
			}

			@Test
			void removeMedicalRecordByMedicalRecordTestIfEmpty() {
				assertEquals(null, dataBase.removeMedicalRecord(new MedicalRecord()));
			}

			@Test
			void removeMedicalRecordByMedicalRecordTestIfNull() {
				assertEquals(null, dataBase.removeMedicalRecord(null));
			}
		}
	}
}
