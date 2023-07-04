package com.openclassrooms.safetynetalerts.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;

@Repository
public class MedicalRecordRepository {

	private static Logger logger = LoggerFactory.getLogger(MedicalRecordRepository.class);
	
	private DataBase dataBase;
	
	public MedicalRecordRepository() {
		this(DataBase.getDataBase());
		logger.debug("call of MedicalRecordRepository()");
	}
	
	public MedicalRecordRepository(DataBase dataBase) {
		this.dataBase = dataBase;
		logger.debug(String.format("call of putMedicalRecord, args : %s", dataBase));
	}

	public MedicalRecord delete(MedicalRecord medicalRecord) {
		logger.debug(String.format("call of delete, args : %s", medicalRecord));
		return dataBase.removeMedicalRecord(medicalRecord);
	}

	public List<MedicalRecord> get(MedicalRecord medicalRecord) {
		logger.debug(String.format("call of get, args : %s", medicalRecord));
		return dataBase.getMedicalRecords(medicalRecord);
	}
	
	public MedicalRecord save(MedicalRecord medicalRecord) {
		logger.debug(String.format("call of save, args : %s", medicalRecord));
		return dataBase.addMedicalRecord(medicalRecord);
	}

}
