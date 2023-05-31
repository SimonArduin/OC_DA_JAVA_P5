package com.openclassrooms.safetynetalerts.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import org.json.JSONObject;

@RestController
public class FireStationController {

	private ArrayList<JSONObject> persons = new ArrayList<JSONObject>(Arrays.asList(
			new JSONObject().put("firstName", "John").put("lastName", "Boyd").put("address", "1509 Culver St")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-6512")
					.put("email", "jaboyd@email.com"),
			new JSONObject().put("firstName", "Jacob").put("lastName", "Boyd").put("address", "1509 Culver St")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-6513")
					.put("email", "drk@email.com"),
			new JSONObject().put("firstName", "Tenley").put("lastName", "Boyd").put("address", "1509 Culver St")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-6512")
					.put("email", "tenz@email.com"),
			new JSONObject().put("firstName", "Roger").put("lastName", "Boyd").put("address", "1509 Culver St")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-6512")
					.put("email", "jaboyd@email.com"),
			new JSONObject().put("firstName", "Felicia").put("lastName", "Boyd").put("address", "1509 Culver St")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-6544")
					.put("email", "jaboyd@email.com"),
			new JSONObject().put("firstName", "Jonanathan").put("lastName", "Marrack").put("address", "29 15th St")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-6513")
					.put("email", "drk@email.com"),
			new JSONObject().put("firstName", "Tessa").put("lastName", "Carman").put("address", "834 Binoc Ave")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-6512")
					.put("email", "tenz@email.com"),
			new JSONObject().put("firstName", "Peter").put("lastName", "Duncan").put("address", "644 Gershwin Cir")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-6512")
					.put("email", "jaboyd@email.com"),
			new JSONObject().put("firstName", "Foster").put("lastName", "Shepard").put("address", "748 Townings Dr")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-6544")
					.put("email", "jaboyd@email.com"),
			new JSONObject().put("firstName", "Tony").put("lastName", "Cooper").put("address", "112 Steppes Pl")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-6874")
					.put("email", "tcoop@ymail.com"),
			new JSONObject().put("firstName", "Lily").put("lastName", "Cooper").put("address", "489 Manchester St")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-9845")
					.put("email", "lily@email.com"),
			new JSONObject().put("firstName", "Sophia").put("lastName", "Zemicks").put("address", "892 Downing Ct")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-7878")
					.put("email", "soph@email.com"),
			new JSONObject().put("firstName", "Warren").put("lastName", "Zemicks").put("address", "892 Downing Ct")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-7512")
					.put("email", "ward@email.com"),
			new JSONObject().put("firstName", "Zach").put("lastName", "Zemicks").put("address", "892 Downing Ct")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-7512")
					.put("email", "zarc@email.com"),
			new JSONObject().put("firstName", "Reginold").put("lastName", "Walker").put("address", "908 73rd St")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-8547")
					.put("email", "reg@email.com"),
			new JSONObject().put("firstName", "Jamie").put("lastName", "Peters").put("address", "908 73rd St")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-7462")
					.put("email", "jpeter@email.com"),
			new JSONObject().put("firstName", "Ron").put("lastName", "Peters").put("address", "112 Steppes Pl")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-8888")
					.put("email", "jpeter@email.com"),
			new JSONObject().put("firstName", "Allison").put("lastName", "Boyd").put("address", "112 Steppes Pl")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-9888")
					.put("email", "aly@imail.com"),
			new JSONObject().put("firstName", "Brian").put("lastName", "Stelzer").put("address", "947 E. Rose Dr")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-7784")
					.put("email", "bstel@email.com"),
			new JSONObject().put("firstName", "Shawna").put("lastName", "Stelzer").put("address", "947 E. Rose Dr")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-7784")
					.put("email", "ssanw@email.com"),
			new JSONObject().put("firstName", "Kendrik").put("lastName", "Stelzer").put("address", "947 E. Rose Dr")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-7784")
					.put("email", "bstel@email.com"),
			new JSONObject().put("firstName", "Clive").put("lastName", "Ferguson").put("address", "748 Townings Dr")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-6741")
					.put("email", "clivfd@ymail.com"),
			new JSONObject().put("firstName", "Eric").put("lastName", "Cadigan").put("address", "951 LoneTree Rd")
					.put("city", "Culver").put("zip", "97451").put("phone", "841-874-7458")
					.put("email", "gramps@email.com")));

	private ArrayList<JSONObject> fireStations = new ArrayList<JSONObject>(
			Arrays.asList(new JSONObject().put("address", "1509 Culver St").put("station", "3"),
					new JSONObject().put("address", "29 15th St").put("station", "2"),
					new JSONObject().put("address", "834 Binoc Ave").put("station", "3"),
					new JSONObject().put("address", "644 Gershwin Cir").put("station", "1"),
					new JSONObject().put("address", "748 Townings Dr").put("station", "3"),
					new JSONObject().put("address", "112 Steppes Pl").put("station", "3"),
					new JSONObject().put("address", "489 Manchester St").put("station", "4"),
					new JSONObject().put("address", "892 Downing Ct").put("station", "2"),
					new JSONObject().put("address", "908 73rd St").put("station", "1"),
					new JSONObject().put("address", "112 Steppes Pl").put("station", "4"),
					new JSONObject().put("address", "947 E. Rose Dr").put("station", "1"),
					new JSONObject().put("address", "748 Townings Dr").put("station", "3"),
					new JSONObject().put("address", "951 LoneTree Rd").put("station", "2")));

	private ArrayList<JSONObject> medicalRecords = new ArrayList<JSONObject>(Arrays.asList(
			new JSONObject().put("firstName", "John").put("lastName", "Boyd").put("birthdate", "03/06/1984")
					.put("medications", new JSONObject().put("aznol", "350mg").put("hydrapermazol", "100mg"))
					.put("allergies", new ArrayList<String>().add("nillacilan")),
			new JSONObject().put("firstName", "Jacob").put("lastName", "Boyd").put("birthdate", "03/06/1989")
					.put("medications",
							new JSONObject().put("pharmacol", "5000mg").put("terazine", "10mg").put("noznazol",
									"250mg"))
					.put("allergies", new ArrayList<String>()),
			new JSONObject().put("firstName", "Tenley").put("lastName", "Boyd").put("birthdate", "02/18/2012")
					.put("medications", new JSONObject()).put("allergies", new ArrayList<String>().add("peanut")),
			new JSONObject().put("firstName", "Roger").put("lastName", "Boyd").put("birthdate", "09/06/2017")
					.put("medications", new JSONObject()).put("allergies", new ArrayList<String>()),
			new JSONObject().put("firstName", "Felicia").put("lastName", "Boyd").put("birthdate", "01/08/1986")
					.put("medications", new JSONObject().put("tetracyclaz", "650mg"))
					.put("allergies", new ArrayList<String>().add("xilliathal")),
			new JSONObject().put("firstName", "Jonanathan").put("lastName", "Marrack").put("birthdate", "01/03/1989")
					.put("medications", new JSONObject()).put("allergies", new ArrayList<String>()),
			new JSONObject().put("firstName", "Tessa").put("lastName", "Carman").put("birthdate", "02/18/2012")
					.put("medications", new JSONObject()).put("allergies", new ArrayList<String>()),
			new JSONObject().put("firstName", "Peter").put("lastName", "Duncan").put("birthdate", "09/06/2000")
					.put("medications", new JSONObject()).put("allergies", new ArrayList<String>().add("shellfish")),
			new JSONObject().put("firstName", "Foster").put("lastName", "Shepard").put("birthdate", "01/08/1980")
					.put("medications", new JSONObject()).put("allergies", new ArrayList<String>()),
			new JSONObject().put("firstName", "Tony").put("lastName", "Cooper").put("birthdate", "03/06/1994")
					.put("medications", new JSONObject().put("hydrapermazol", "300mg").put("dodoxadin", "30mg"))
					.put("allergies", new ArrayList<String>().add("shellfish")),
			new JSONObject().put("firstName", "Lily").put("lastName", "Cooper").put("birthdate", "03/06/1994")
					.put("medications", new JSONObject()).put("allergies", new ArrayList<String>()),
			new JSONObject().put("firstName", "Sophia").put("lastName", "Zemicks").put("birthdate", "03/06/1988")
					.put("medications",
							new JSONObject().put("aznol", "60mg").put("hydrapermazol", "900mg")
									.put("pharmacol", "5000mg").put("terazine", "500mg"))
					.put("allergies", new ArrayList<String>(Arrays.asList("peanut", "shellfish", "aznol"))),
			new JSONObject().put("firstName", "Warren").put("lastName", "Zemicks").put("birthdate", "03/06/1985")
					.put("medications", new JSONObject()).put("allergies", new ArrayList<String>()),
			new JSONObject().put("firstName", "Zach").put("lastName", "Zemicks").put("birthdate", "03/06/2017")
					.put("medications", new JSONObject()).put("allergies", new ArrayList<String>()),
			new JSONObject().put("firstName", "Reginold").put("lastName", "Walker").put("birthdate", "08/30/1979")
					.put("medications", new JSONObject()).put("thradox", "700mg")
					.put("allergies", new ArrayList<String>().add("illisoxian")),
			new JSONObject().put("firstName", "Jamie").put("lastName", "Peters").put("birthdate", "03/06/1982")
					.put("medications", new JSONObject()).put("allergies", new ArrayList<String>()),
			new JSONObject().put("firstName", "Ron").put("lastName", "Peters").put("birthdate", "04/06/1965")
					.put("medications", new JSONObject()).put("allergies", new ArrayList<String>()),
			new JSONObject().put("firstName", "Allison").put("lastName", "Boyd").put("birthdate", "03/15/1965")
					.put("medications", new JSONObject().put("aznol", "200mg"))
					.put("allergies", new ArrayList<String>().add("nillacilan")),
			new JSONObject().put("firstName", "Brian").put("lastName", "Stelzer").put("birthdate", "12/06/1975")
					.put("medications", new JSONObject().put("ibupurin", "200mg").put("hydrapermazol", "400mg"))
					.put("allergies", new ArrayList<String>().add("nillacilan")),
			new JSONObject().put("firstName", "Shawna").put("lastName", "Stelzer").put("birthdate", "07/08/1980")
					.put("medications", new JSONObject()).put("allergies", new ArrayList<String>()),
			new JSONObject().put("firstName", "Kendrik").put("lastName", "Stelzer").put("birthdate", "03/06/2014")
					.put("medications", new JSONObject().put("noxidian", "100mg").put("pharmacol", "2500mg"))
					.put("allergies", new ArrayList<String>()),
			new JSONObject().put("firstName", "Clive").put("lastName", "Ferguson").put("birthdate", "03/06/1994")
					.put("medications", new JSONObject()).put("allergies", new ArrayList<String>()),
			new JSONObject().put("firstName", "Eric").put("lastName", "Cadigan").put("birthdate", "08/06/1945").put(
					"medications",
					new JSONObject().put("tradoxidine", "400mg").put("allergies", new ArrayList<String>()))));

	/**
	 * Read - Get info on residents covered by a certain fire station or get all
	 * fire stations
	 * 
	 * @param - An int corresponding to the fire station number
	 * @return - An Iterable object of info on residents or of FireStation full
	 *         filled
	 */
	@GetMapping("/firestation")
	public Iterable<String> getFireStations(@RequestParam(value = "stationNumber") Optional<Integer> stationNumber) {
		ArrayList<String> result = new ArrayList<String>();
		int numberOfAdults = 0;
		int numberOfChildren = 0;

		// if a station number is given, returns info on residents
		if (stationNumber.isPresent()) {
			for (JSONObject fireStation : fireStations) {
				if (fireStation.getInt("station") == stationNumber.get()) {
					for (JSONObject person : persons) {
						if (fireStation.getString("address").equals(person.getString("address"))) {
							// returns first name, last name, address and phone number of the resident
							result.add(new JSONObject().put("firstName", person.getString("firstName"))
									.put("lastName", person.getString("lastName"))
									.put("address", person.getString("address")).put("phone", person.getString("phone"))
									.toString());
							// get medical record of the resident
							for (JSONObject medicalRecord : medicalRecords) {
								if (medicalRecord.getString("firstName").equals(person.getString("firstName"))
										&& medicalRecord.getString("lastName").equals(person.getString("lastName"))) {
									//get birthdate
									LocalDate birthdate = LocalDate.parse(medicalRecord.getString("birthdate"),
											DateTimeFormatter.ofPattern("MM/dd/yyyy"));
									//count as adult or child
									if (Period.between(birthdate, LocalDate.now()).getYears() > 18)
										numberOfAdults++;
									else
										numberOfChildren++;
								}
							}
						}
					}
				}
			}
			result.add(String.format("numberOfAdults\":\"%s\",\"numberOfChildren\":\"%s\"", numberOfAdults,
					numberOfChildren));
		}

		// is no station number is given, returns a list of all fire stations
		else {
			for (JSONObject fireStation : fireStations)
				result.add(fireStation.toString());
			return result;
		}
		return result;
	}

	/**
	 * Put - Changes the station number of a firestation in the database
	 * 
	 * @param - A String corresponding to the address of the fire station
	 *  and an optional int corresponding to the new fire station number
	 */
	@PutMapping("/firestation")
	public void putFireStation(@RequestParam(value = "address") String address,
			@RequestParam(value = "stationNumber") Optional<Integer> stationNumber) {
		if (stationNumber.isPresent()) {
			for (JSONObject fireStation : fireStations) {
				if (fireStation.getString("address").equals(address)) {
					int i = fireStations.indexOf(fireStation);
					fireStation.remove("station");
					fireStation.put("station", stationNumber.get());
					fireStations.set(i, fireStation);
				}
			}
		}
	}

	/**
	 * Post - Adds a new firestation to the database
	 * 
	 * @param - A String corresponding to the address of the fire station
	 *  and an int corresponding to the fire station number
	 */
	@PostMapping("/firestation")
	public void postFireStation(@RequestParam(value = "address") String address,
			@RequestParam(value = "stationNumber", defaultValue = "0") int stationNumber) {
		fireStations.add(new JSONObject().put("address", address).put("station", stationNumber));
	}

	/**
	 * Delete - Removes a firestation from the database
	 * 
	 * @param - An optional String corresponding to the address of the fire station
	 *  and an optional int corresponding to the new fire station number
	 */
	@DeleteMapping("/firestation")
	public void deleteFireStation(@RequestParam(value = "address") Optional<String> address,
			@RequestParam(value = "stationNumber") Optional<Integer> stationNumber) {
		Iterator<JSONObject> ite = fireStations.iterator();
		while (ite.hasNext()) {
			JSONObject fireStation = ite.next();
			if (address.isPresent()) {
				if (fireStation.getString("address").equals(address.get())) {
					ite.remove();
				}
			} else {
				if (stationNumber.isPresent())
					if (fireStation.getInt("station") == stationNumber.get()) {
						ite.remove();
					}
			}
		}
	}
}
