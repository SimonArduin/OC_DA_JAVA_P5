package com.openclassrooms.safetynetalerts.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;

@Repository
public class MedicalRecordRepository {
	
	private DataBase dataBase;
	
	public MedicalRecordRepository() {
		this(DataBase.getDataBase());
	}
	
	public MedicalRecordRepository(DataBase dataBase) {
		this.dataBase = dataBase;
	}

	public MedicalRecord delete(MedicalRecord medicalRecord) {
		return dataBase.removeMedicalRecord(medicalRecord);
	}

	public List<MedicalRecord> get(MedicalRecord medicalRecord) {
		return dataBase.getMedicalRecords(medicalRecord);
	}
	
	public MedicalRecord save(MedicalRecord medicalRecord) {
		return dataBase.addMedicalRecord(medicalRecord);
	}

}
