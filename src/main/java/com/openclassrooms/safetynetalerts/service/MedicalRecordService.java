package com.openclassrooms.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	public List<MedicalRecord> getMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.get(medicalRecord);
	}

	public MedicalRecord getMedicalRecord(Person person) {
		if (person == null)
			return new MedicalRecord();
		ArrayList<MedicalRecord> medicalRecordsInDB = new ArrayList<MedicalRecord>(
				getMedicalRecord(new MedicalRecord(person.getFirstName(), person.getLastName())));
		if (!medicalRecordsInDB.isEmpty())
			return medicalRecordsInDB.get(0);
		return new MedicalRecord();
	}

	public MedicalRecord putMedicalRecord(MedicalRecord medicalRecord) {
		if (medicalRecord == null)
			return new MedicalRecord();
		if (medicalRecord.getFirstName() != null && medicalRecord.getLastName() != null) {
			ArrayList<MedicalRecord> medicalRecordsInDB = new ArrayList<MedicalRecord>(
					medicalRecordRepository.get(medicalRecord));
			if (!medicalRecordsInDB.isEmpty()) {
				MedicalRecord medicalRecordToPut = medicalRecordsInDB.get(0);
				if (!medicalRecordToPut.isEmpty()) {
					medicalRecordRepository.delete(medicalRecordToPut);
					medicalRecordToPut.update(medicalRecord);
					return medicalRecordRepository.save(medicalRecordToPut);
				}
			}
		}
		return new MedicalRecord();
	}

	public MedicalRecord postMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.save(medicalRecord);
	}

	public MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.delete(medicalRecord);
	}
}
