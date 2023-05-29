package com.openclassrooms.safetynetalerts;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.service.FireStationService;

@SpringBootApplication
public class SafetynetalertsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetynetalertsApplication.class, args);
	}
	
	/*@Bean
	CommandLineRunner runner(FireStationService fireStationService) {
		return args -> {
			// read data.json and write to database
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<FireStation>> typeReference = new TypeReference<List<FireStation>>() {};
			InputStream inputStream = TypeReference.class.getResourceAsStream("data.json");
			try {
				List<FireStation> fireStations = mapper.readValue(inputStream, typeReference);
				fireStationService.saveFireStation(fireStations);
				System.out.println("FireStations saved");
			} catch (IOException e) {
				System.out.println("Unable to save FireStations : " + e.getMessage());
			}
		}
	}*/

}
