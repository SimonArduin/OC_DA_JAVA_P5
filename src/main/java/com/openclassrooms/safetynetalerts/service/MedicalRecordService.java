package com.openclassrooms.safetynetalerts.service;

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
			return null;
		List<MedicalRecord> medicalRecordsInDB = getMedicalRecord(new MedicalRecord(person.getFirstName(), person.getLastName()));
		if (medicalRecordsInDB == null || medicalRecordsInDB.isEmpty())
			return null;
		return medicalRecordsInDB.get(0);
	}

	/*
	 * Updates the content of the specified medical record
	 * 
	 * If several medical records have the same identifier as the specified medical record,
	 * only one of them will remain, and its fields will be updated
	 * 
	 * @param - A MedicalRecord representing containing the new information
	 * 
	 * @return - true if the medical record was correctly saved
	 */

	public MedicalRecord putMedicalRecord(MedicalRecord medicalRecord) {
		if (medicalRecord == null)
			return null;
		boolean isInDB = false;
		MedicalRecord medicalRecordToPut = new MedicalRecord();
		if (medicalRecord.getFirstName() != null && medicalRecord.getLastName() != null) {
			List<MedicalRecord> medicalRecordsInDB = medicalRecordRepository.get(medicalRecord);
			if (medicalRecordsInDB == null)
				return null;
			for (MedicalRecord medicalRecordInDB : medicalRecordsInDB) {
				if (medicalRecordInDB.equals(medicalRecord)) {
					isInDB = true;
					medicalRecordToPut = medicalRecordInDB;
					medicalRecordRepository.delete(medicalRecordInDB);
				}
			}
		}
		if (isInDB) {
			medicalRecordToPut.update(medicalRecord);
			return medicalRecordRepository.save(medicalRecordToPut);
		} else
			return null;
	}

	public MedicalRecord postMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.save(medicalRecord);
	}

	public MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.delete(medicalRecord);
	}
}
