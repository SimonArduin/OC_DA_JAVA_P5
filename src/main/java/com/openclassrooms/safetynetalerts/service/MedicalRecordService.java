package com.openclassrooms.safetynetalerts.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {

	private static Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	public List<MedicalRecord> getMedicalRecord(MedicalRecord medicalRecord) {
		logger.debug(String.format("call of getMedicalRecord, args : %s", medicalRecord));
		return medicalRecordRepository.get(medicalRecord);
	}

	public MedicalRecord getMedicalRecord(Person person) {
		logger.debug(String.format("call of getMedicalRecord, args : %s", person));
		if (person == null)
			return null;
		List<MedicalRecord> medicalRecordsInDB = getMedicalRecord(new MedicalRecord(person.getFirstName(), person.getLastName()));
		logger.debug(String.format("medicalRecords found for person %s in medicalRecordURL : %s", person,
				medicalRecordsInDB));
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
		logger.debug(String.format("call of putMedicalRecord, args : %s", medicalRecord));
		if (medicalRecord == null)
			return null;
		boolean isInDB = false;
		MedicalRecord medicalRecordToPut = new MedicalRecord();
		if (medicalRecord.getFirstName() != null && medicalRecord.getLastName() != null) {
			List<MedicalRecord> medicalRecordsInDB = medicalRecordRepository.get(medicalRecord);
			logger.debug(String.format("medicalRecords found for medicalRecord %s in medicalRecordURL : %s", medicalRecord,
					medicalRecordsInDB));
			if (medicalRecordsInDB == null)
				return null;
			for (MedicalRecord medicalRecordInDB : medicalRecordsInDB) {
				if (medicalRecordInDB.equals(medicalRecord)) {
					isInDB = true;
					medicalRecordToPut = medicalRecordInDB;
					logger.debug(String.format("medicalRecordToPut is %s", medicalRecordToPut));
					medicalRecordRepository.delete(medicalRecordInDB);
					logger.debug(String.format("deleted medicalRecord %s", medicalRecordInDB));
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
		logger.debug(String.format("call of postMedicalRecord, args : %s", medicalRecord));
		return medicalRecordRepository.save(medicalRecord);
	}

	public MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) {
		logger.debug(String.format("call of deleteMedicalRecord, args : %s", medicalRecord));
		return medicalRecordRepository.delete(medicalRecord);
	}
}
