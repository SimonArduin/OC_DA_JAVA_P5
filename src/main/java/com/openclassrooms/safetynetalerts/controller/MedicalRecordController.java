package com.openclassrooms.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.service.MedicalRecordService;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@EnableWebMvc
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

	@Autowired
	private MedicalRecordService medicalRecordService;

	/**
	 * Put - Changes the fields of a medicalRecord in the database
	 * 
	 * @param - Two String corresponding to the name of the medicalRecord
	 * 
	 *          An Optional<String> corresponding to the new birthdate
	 * 
	 *          An Optional<ArrayList<String>> corresponding to the new medications
	 * 
	 *          An Optional<ArrayList<String>> corresponding to the new allergies
	 * 
	 *          If one of the optional params has no value, the corresponding field
	 *          of the medicalRecord will not be modified
	 * @return - A MedicalRecord corresponding to the modified medicalRecord
	 */
	@PutMapping
	public MedicalRecord putMedicalRecord(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "birthdate") Optional<String> birthdate,
			@RequestParam(value = "medications") Optional<ArrayList<String>> medications,
			@RequestParam(value = "allergies") Optional<ArrayList<String>> allergies) {
		MedicalRecord medicalRecord = new MedicalRecord(firstName, lastName);
		if (birthdate.isPresent())
			medicalRecord.setBirthdate(birthdate.get());
		if (medications.isPresent())
			medicalRecord.setMedications(medications.get());
		if (allergies.isPresent())
			medicalRecord.setAllergies(allergies.get());
		return medicalRecordService.putMedicalRecord(medicalRecord);
	}

	/**
	 * Post - Adds a new medicalRecord to the database
	 * 
	 * @param - Two String corresponding to the name of the medicalRecord
	 * 
	 *          An Optional<String> corresponding to the new birthdate
	 * 
	 *          An Optional<ArrayList<String>> corresponding to the new medications
	 * 
	 *          An Optional<ArrayList<String>> corresponding to the new allergies
	 * 
	 *          If one of the optional params has no value, the corresponding field
	 *          of the medicalRecord will be null
	 * @return - A MedicalRecord corresponding to the added medicalRecord
	 */
	@PostMapping
	public MedicalRecord postMedicalRecord(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "birthdate") Optional<String> birthdate,
			@RequestParam(value = "medications") Optional<ArrayList<String>> medications,
			@RequestParam(value = "allergies") Optional<ArrayList<String>> allergies) {
		MedicalRecord medicalRecord = new MedicalRecord(firstName, lastName);
		if (birthdate.isPresent())
			medicalRecord.setBirthdate(birthdate.get());
		if (medications.isPresent())
			medicalRecord.setMedications(medications.get());
		if (allergies.isPresent())
			medicalRecord.setAllergies(allergies.get());
		return medicalRecordService.postMedicalRecord(medicalRecord);
	}

	/**
	 * Delete - Removes a medicalRecord from the database
	 * 
	 * @param - Two String objects corresponding to the first and last name of the
	 *          medicalRecord
	 * @return - A MedicalRecord corresponding to the deleted medicalRecord
	 */
	@DeleteMapping
	public MedicalRecord deleteMedicalRecord(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName) {
		return medicalRecordService.deleteMedicalRecord(new MedicalRecord(firstName, lastName));
	}
}
