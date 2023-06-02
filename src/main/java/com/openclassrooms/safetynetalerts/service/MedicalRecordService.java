package com.openclassrooms.safetynetalerts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {
	
	@Autowired
	MedicalRecordRepository medicalRecordRepository;

	public MedicalRecord getMedicalRecordByName(String firstName, String lastName) {
		return medicalRecordRepository.findByFullName(firstName, lastName);
	}

}
