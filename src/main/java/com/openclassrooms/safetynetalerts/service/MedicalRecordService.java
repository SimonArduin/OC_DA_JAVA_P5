package com.openclassrooms.safetynetalerts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	public MedicalRecord getMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.get(medicalRecord);
	}

	public MedicalRecord putMedicalRecord(MedicalRecord medicalRecord) {
		if (!medicalRecordRepository.get(medicalRecord).isEmpty()) {
			medicalRecordRepository.delete(medicalRecord);
			return medicalRecordRepository.save(medicalRecord);
		} else
			return new MedicalRecord();
	}

	public MedicalRecord postMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.save(medicalRecord);
	}

	public MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.delete(medicalRecord);
	}
}
