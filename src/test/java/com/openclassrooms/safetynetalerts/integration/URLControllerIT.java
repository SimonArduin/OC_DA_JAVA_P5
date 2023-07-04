package com.openclassrooms.safetynetalerts.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.openclassrooms.safetynetalerts.controller.FireStationController;
import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.repository.DataBase;

@SpringBootTest
@AutoConfigureMockMvc
public class URLControllerIT {

	@Autowired
	FireStationController fireStationController = new FireStationController();;

	@Autowired
	private MockMvc mockMvc;

	final FireStation fireStation = new FireStation("address", "station");
	final FireStation fireStationNotInDB = new FireStation("addresOther", "stationOther");
	final ArrayList<FireStation> fireStationList = new ArrayList<FireStation>(Arrays.asList(fireStation));
	final ArrayList<String> stationsList = new ArrayList<String>(Arrays.asList(fireStation.getStation()));

	final Person person = new Person("firstName", "lastName", fireStation.getAddress(), "city", "zip", "phone",
			"email");
	final Person personChild = new Person("firstNameChild", "lastNameChild", person.getAddress(), person.getCity(),
			person.getZip(), person.getPhone(), person.getEmail());
	final ArrayList<Person> personList = new ArrayList<Person>(Arrays.asList(person, personChild));

	final ArrayList<String> medications = new ArrayList<String>(Arrays.asList("medication 1", "medication 2"));
	final ArrayList<String> allergies = new ArrayList<String>(Arrays.asList("allergy 1", "allergy 2"));
	final MedicalRecord medicalRecord = new MedicalRecord(person.getFirstName(), person.getLastName(), "06/06/1966",
			medications, allergies);
	final MedicalRecord medicalRecordChild = new MedicalRecord(personChild.getFirstName(), personChild.getLastName(),
			"06/06/2006", medications, allergies);
	final ArrayList<MedicalRecord> medicalRecordList = new ArrayList<MedicalRecord>(
			Arrays.asList(medicalRecord, medicalRecordChild));

	final int numberOfAdults = 1;
	final int numberOfChildren = 1;

	@BeforeEach
	public void setUpPerTest() {
		DataBase.setDataBase(fireStationList, personList, medicalRecordList);
	}

	@Nested
	class childAlertURLTests {

		@Test
		public void childAlertURLTest() throws Exception {
			mockMvc.perform(get(String.format("/childAlert?address=%s", person.getAddress())))
					.andExpect(status().isOk())

					.andExpect(jsonPath("children.[0].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("children.[0].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("children.[0].age", is(medicalRecordChild.calculateAge())))

					.andExpect(jsonPath("children.[1]").doesNotExist())

					.andExpect(jsonPath("adults.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("adults.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("adults.[0].address", is(person.getAddress())))
					.andExpect(jsonPath("adults.[0].city", is(person.getCity())))
					.andExpect(jsonPath("adults.[0].zip", is(person.getZip())))
					.andExpect(jsonPath("adults.[0].phone", is(person.getPhone())))
					.andExpect(jsonPath("adults.[0].email", is(person.getEmail())))

					.andExpect(jsonPath("adults.[1]").doesNotExist());
		}

		@Test
		public void childAlertURLTestIfNoPersons() throws Exception {
			
			DataBase.setDataBase(fireStationList, null, medicalRecordList);
			
			mockMvc.perform(get(String.format("/childAlert?address=%s", person.getAddress())))
					.andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void childAlertURLTestIfNoMedicalRecords() throws Exception {
			
			DataBase.setDataBase(fireStationList, personList, null);
			
			mockMvc.perform(get(String.format("/childAlert?address=%s", person.getAddress())))
					.andExpect(status().isOk())

					.andExpect(jsonPath("children").isEmpty())

					.andExpect(jsonPath("adults.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("adults.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("adults.[0].address", is(person.getAddress())))
					.andExpect(jsonPath("adults.[0].city", is(person.getCity())))
					.andExpect(jsonPath("adults.[0].zip", is(person.getZip())))
					.andExpect(jsonPath("adults.[0].phone", is(person.getPhone())))
					.andExpect(jsonPath("adults.[0].email", is(person.getEmail())))

					.andExpect(jsonPath("adults.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("adults.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("adults.[1].address", is(personChild.getAddress())))
					.andExpect(jsonPath("adults.[1].city", is(personChild.getCity())))
					.andExpect(jsonPath("adults.[1].zip", is(personChild.getZip())))
					.andExpect(jsonPath("adults.[1].phone", is(personChild.getPhone())))
					.andExpect(jsonPath("adults.[1].email", is(personChild.getEmail())))

					.andExpect(jsonPath("adults.[2]").doesNotExist());
		}

		@Test
		public void childAlertURLTestIfNoParams() throws Exception {
			mockMvc.perform(get("/childAlert")).andExpect(status().isBadRequest())
					.andExpect(jsonPath("$").doesNotExist());
			;
		}

	}

	@Nested
	class phoneAlertURLTests {

		@Test
		public void phoneAlertURLTest() throws Exception {
			mockMvc.perform(get(String.format("/phoneAlert?firestation=%s", fireStation.getStation())))
					.andExpect(status().isOk())

					.andExpect(jsonPath("phones.[0]", is(person.getPhone())))
					.andExpect(jsonPath("phones.[1]", is(personChild.getPhone())))
					.andExpect(jsonPath("phones.[2]").doesNotExist());
		}

		@Test
		public void phoneAlertURLTestIfNoFireStation() throws Exception {
			
			DataBase.setDataBase(null, personList, medicalRecordList);
			
			mockMvc.perform(get(String.format("/phoneAlert?firestation=%s", fireStation.getStation())))
					.andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void phoneAlertURLTestIfNoPersons() throws Exception {
			
			DataBase.setDataBase(fireStationList, null, medicalRecordList);
			
			mockMvc.perform(get(String.format("/phoneAlert?firestation=%s", fireStation.getStation())))
					.andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void phoneAlertURLTestIfNoParams() throws Exception {
			mockMvc.perform(get("/phoneAlert")).andExpect(status().isBadRequest())
					.andExpect(jsonPath("$").doesNotExist());
			;
		}

	}

	@Nested
	class fireURLTests {

		@Test
		public void fireURLTest() throws Exception {
			mockMvc.perform(get(String.format("/fire?address=%s", fireStation.getAddress()))).andExpect(status().isOk())

					.andExpect(jsonPath("persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("persons.[0].address", is(person.getAddress())))
					.andExpect(jsonPath("persons.[0].phone", is(person.getPhone())))
					.andExpect(jsonPath("persons.[0].age", is(medicalRecord.calculateAge())))
					.andExpect(jsonPath("persons.[0].medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("persons.[0].allergies", is(medicalRecord.getAllergies())))

					.andExpect(jsonPath("persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("persons.[1].address", is(personChild.getAddress())))
					.andExpect(jsonPath("persons.[1].phone", is(personChild.getPhone())))
					.andExpect(jsonPath("persons.[1].age", is(medicalRecordChild.calculateAge())))
					.andExpect(jsonPath("persons.[1].medications", is(medicalRecordChild.getMedications())))
					.andExpect(jsonPath("persons.[1].allergies", is(medicalRecordChild.getAllergies())))

					.andExpect(jsonPath("persons.[2]").doesNotExist())

					.andExpect(jsonPath("fireStationNumber", is(fireStation.getStation())));
		}

		@Test
		public void fireURLTestIfNoFireStation() throws Exception {
			
			DataBase.setDataBase(null, personList, medicalRecordList);
			
			mockMvc.perform(get(String.format("/fire?address=%s", fireStation.getAddress())))
					.andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void fireURLTestIfNoPersons() throws Exception {
			
			DataBase.setDataBase(fireStationList, null, medicalRecordList);
			
			mockMvc.perform(get(String.format("/fire?address=%s", fireStation.getAddress())))
					.andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void fireURLTestIfNoMedicalRecords() throws Exception {
			
			DataBase.setDataBase(fireStationList, personList, null);
			
			mockMvc.perform(get(String.format("/fire?address=%s", fireStation.getAddress()))).andExpect(status().isOk())

					.andExpect(jsonPath("persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("persons.[0].address", is(person.getAddress())))
					.andExpect(jsonPath("persons.[0].phone", is(person.getPhone())))
					.andExpect(jsonPath("persons.[0].age").doesNotExist())
					.andExpect(jsonPath("persons.[0].medications").isEmpty())
					.andExpect(jsonPath("persons.[0].allergies").isEmpty())

					.andExpect(jsonPath("persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("persons.[1].address", is(personChild.getAddress())))
					.andExpect(jsonPath("persons.[1].phone", is(personChild.getPhone())))
					.andExpect(jsonPath("persons.[1].age").doesNotExist())
					.andExpect(jsonPath("persons.[1].medications").isEmpty())
					.andExpect(jsonPath("persons.[1].allergies").isEmpty())

					.andExpect(jsonPath("persons.[2]").doesNotExist())

					.andExpect(jsonPath("fireStationNumber", is(fireStation.getStation())));
		}

		@Test
		public void fireURLTestIfNoParams() throws Exception {
			mockMvc.perform(get("/fire")).andExpect(status().isBadRequest()).andExpect(jsonPath("$").doesNotExist());
			;
		}

	}

	@Nested
	class floodStationsTests {

		@Test
		public void floodStationsTest() throws Exception {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.addAll("stations", stationsList);
			mockMvc.perform(get("/flood/stations").params(params)).andExpect(status().isOk())

					.andExpect(jsonPath("homes.[0].persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("homes.[0].persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("homes.[0].persons.[0].phone", is(person.getPhone())))
					.andExpect(jsonPath("homes.[0].persons.[0].age", is(medicalRecord.calculateAge())))
					.andExpect(jsonPath("homes.[0].persons.[0].medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("homes.[0].persons.[0].allergies", is(medicalRecord.getAllergies())))

					.andExpect(jsonPath("homes.[0].persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("homes.[0].persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("homes.[0].persons.[1].phone", is(personChild.getPhone())))
					.andExpect(jsonPath("homes.[0].persons.[1].age", is(medicalRecordChild.calculateAge())))
					.andExpect(jsonPath("homes.[0].persons.[1].medications", is(medicalRecordChild.getMedications())))
					.andExpect(jsonPath("homes.[0].persons.[1].allergies", is(medicalRecordChild.getAllergies())))

					.andExpect(jsonPath("homes.[0].persons.[2]").doesNotExist())

					.andExpect(jsonPath("homes.[0].fireStationNumber", is(fireStation.getStation())))

					.andExpect(jsonPath("homes.[1].fireStationNumber").doesNotExist());
		}

		@Test
		public void floodStationsTestIfNoFireStation() throws Exception {
			
			DataBase.setDataBase(null, personList, medicalRecordList);
			
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.addAll("stations", stationsList);
			mockMvc.perform(get("/flood/stations").params(params)).andExpect(status().isNotFound())
					.andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void floodStationsTestIfNoPersons() throws Exception {
			
			DataBase.setDataBase(fireStationList, null, medicalRecordList);
			
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.addAll("stations", stationsList);
			mockMvc.perform(get("/flood/stations").params(params)).andExpect(status().isOk())

					.andExpect(jsonPath("homes.[0].persons").isEmpty())

					.andExpect(jsonPath("homes.[0].fireStationNumber", is(fireStation.getStation())))

					.andExpect(jsonPath("homes.[1].fireStationNumber").doesNotExist());
		}

		@Test
		public void floodStationsTestIfNoMedicalRecords() throws Exception {
			
			DataBase.setDataBase(fireStationList, personList, null);
			
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.addAll("stations", stationsList);
			mockMvc.perform(get("/flood/stations").params(params)).andExpect(status().isOk())

					.andExpect(jsonPath("homes.[0].persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("homes.[0].persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("homes.[0].persons.[0].phone", is(person.getPhone())))
					.andExpect(jsonPath("homes.[0].persons.[0].age").doesNotExist())
					.andExpect(jsonPath("homes.[0].persons.[0].medications").isEmpty())
					.andExpect(jsonPath("homes.[0].persons.[0].allergies").isEmpty())

					.andExpect(jsonPath("homes.[0].persons.[1].firstName", is(personChild.getFirstName())))
					.andExpect(jsonPath("homes.[0].persons.[1].lastName", is(personChild.getLastName())))
					.andExpect(jsonPath("homes.[0].persons.[1].phone", is(personChild.getPhone())))
					.andExpect(jsonPath("homes.[0].persons.[1].age").doesNotExist())
					.andExpect(jsonPath("homes.[0].persons.[1].medications").isEmpty())
					.andExpect(jsonPath("homes.[0].persons.[1].allergies").isEmpty())

					.andExpect(jsonPath("homes.[0].persons.[2]").doesNotExist())

					.andExpect(jsonPath("homes.[0].fireStationNumber", is(fireStation.getStation())))

					.andExpect(jsonPath("homes.[1].fireStationNumber").doesNotExist());
		}

		@Test
		public void floodStationsTestIfNoParams() throws Exception {
			mockMvc.perform(get("/flood/stations")).andExpect(status().isBadRequest())
					.andExpect(jsonPath("$").doesNotExist());
			;
		}

	}

	@Nested
	class personInfoURLTests {

		@Test
		public void personInfoURLTest() throws Exception {
			mockMvc.perform(get(
					String.format("/personInfo?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
					.andExpect(status().isOk())

					.andExpect(jsonPath("persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("persons.[0].age", is(medicalRecord.calculateAge())))
					.andExpect(jsonPath("persons.[0].address", is(person.getAddress())))
					.andExpect(jsonPath("persons.[0].medications", is(medicalRecord.getMedications())))
					.andExpect(jsonPath("persons.[0].allergies", is(medicalRecord.getAllergies())))
					.andExpect(jsonPath("persons.[1]").doesNotExist());
		}

		@Test
		public void personInfoURLTestIfNoPersons() throws Exception {
			
			DataBase.setDataBase(fireStationList, null, medicalRecordList);
			
			mockMvc.perform(get(
					String.format("/personInfo?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
					.andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void personInfoURLTestIfNoMedicalRecords() throws Exception {
			
			DataBase.setDataBase(fireStationList, personList, null);
			
			mockMvc.perform(get(
					String.format("/personInfo?firstName=%s&lastName=%s", person.getFirstName(), person.getLastName())))
					.andExpect(status().isOk())

					.andExpect(jsonPath("persons.[0].firstName", is(person.getFirstName())))
					.andExpect(jsonPath("persons.[0].lastName", is(person.getLastName())))
					.andExpect(jsonPath("persons.[0].age").doesNotExist())
					.andExpect(jsonPath("persons.[0].address", is(person.getAddress())))
					.andExpect(jsonPath("persons.[0].medications").isEmpty())
					.andExpect(jsonPath("persons.[0].allergies").isEmpty())
					.andExpect(jsonPath("persons.[1]").doesNotExist());
		}

		@Test
		public void personInfoURLTestIfNoParams() throws Exception {
			mockMvc.perform(get("/personInfo")).andExpect(status().isBadRequest())
					.andExpect(jsonPath("$").doesNotExist());
			;
		}

	}

	@Nested
	class communityEmailURLTests {

		@Test
		public void communityEmailURLTest() throws Exception {
			mockMvc.perform(get(String.format("/communityEmail?city=%s", person.getCity())))
					.andExpect(status().isOk()).andExpect(jsonPath("emails.[0]", is(person.getEmail())))
					.andExpect(jsonPath("emails.[1]", is(personChild.getEmail())))
					.andExpect(jsonPath("emails.[2]").doesNotExist());
		}

		@Test
		public void communityEmailURLTestIfNoPersons() throws Exception {
			
			DataBase.setDataBase(fireStationList, null, medicalRecordList);

			mockMvc.perform(get(String.format("/communityEmail?city=%s", person.getCity())))
					.andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
		}

		@Test
		public void communityEmailURLTestIfNoParams() throws Exception {
			mockMvc.perform(get("/communityEmail")).andExpect(status().isBadRequest())
					.andExpect(jsonPath("$").doesNotExist());
			;
		}

	}
}