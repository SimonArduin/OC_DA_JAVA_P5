package com.openclassrooms.safetynetalerts.repository;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;

@Repository
public class MedicalRecordRepository {

	private ArrayList<MedicalRecord> medicalRecords;

	public MedicalRecordRepository() {
		try {
			this.resetDataBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetDataBase() throws Exception {
		medicalRecords = new ArrayList<MedicalRecord>();
		Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/data.json"));
		ObjectMapper objectMapper = new ObjectMapper();
		
		/*
		 * get all data in data.json
		 */
		Map<String, List<Object>> data = objectMapper.readValue(reader,
				   new TypeReference<Map<String,  List<Object>>>() { } );
		/*
		 * extract all fire station data
		 */
		ArrayList<Object> medicalRecordData = new ArrayList<Object>(data.get("firestations"));
		/*
		 * add all fire stations to the list of fire stations
		 */
		for(Object o : medicalRecordData) {
			MedicalRecord medicalRecord = objectMapper.convertValue(o, MedicalRecord.class);
			medicalRecords.add(medicalRecord);
		}
	}

	public boolean delete(MedicalRecord medicalRecord) {
		boolean isInDB = false;
		int i = 0;
		while (i < medicalRecords.size() && !isInDB) {
			MedicalRecord medicalRecordInDB = medicalRecords.get(i);
			if (medicalRecordInDB.equals(medicalRecord)) {
				isInDB = true;
				medicalRecords.remove(medicalRecordInDB);
				break;
			}
			i++;
		}
		return isInDB;
	}

	public List<MedicalRecord> findAll() {
		return medicalRecords;
	}

	public MedicalRecord findByFullName(String firstName, String lastName) {
		MedicalRecord result = new MedicalRecord();
		for (MedicalRecord medicalRecord : medicalRecords) {
			if (firstName.equals(medicalRecord.getFirstName()) && lastName.equals(medicalRecord.getLastName()))
				result = medicalRecord;
		}
		return result;
	}

	public List<MedicalRecord> findByFirstName(String firstName) {
		ArrayList<MedicalRecord> result = new ArrayList<MedicalRecord>();
		for (MedicalRecord medicalRecord : medicalRecords) {
			if (firstName.equals(medicalRecord.getFirstName()))
				result.add(medicalRecord);
		}
		return result;
	}

	public List<MedicalRecord> findByLastName(String lastName) {
		ArrayList<MedicalRecord> result = new ArrayList<MedicalRecord>();
		for (MedicalRecord medicalRecord : medicalRecords) {
			if (lastName.equals(medicalRecord.getLastName()))
				result.add(medicalRecord);
		}
		return result;
	}

	public List<MedicalRecord> findByBirthdate(String birthdate) {
		ArrayList<MedicalRecord> result = new ArrayList<MedicalRecord>();
		for (MedicalRecord medicalRecord : medicalRecords) {
			if (birthdate.equals(medicalRecord.getBirthdate()))
				result.add(medicalRecord);
		}
		return result;
	}

	public List<MedicalRecord> findByMedication(String medication) {
		ArrayList<MedicalRecord> result = new ArrayList<MedicalRecord>();
		for (MedicalRecord medicalRecord : medicalRecords) {
			for (String medicationInMedicalRecord : medicalRecord.getMedications()) {
				if (medicationInMedicalRecord.equals(medication))
					result.add(medicalRecord);
			}
		}
		return result;
	}

	/*
	 * search by EXACT medication list TODO rewrite so it returns every
	 * MedicalRecord that contains specified medications
	 */
	public List<MedicalRecord> findByMedications(ArrayList<String> medications) {
		ArrayList<MedicalRecord> result = new ArrayList<MedicalRecord>();
		for (MedicalRecord medicalRecord : medicalRecords) {
			if (medications.equals(medicalRecord.getMedications()))
				result.add(medicalRecord);
		}
		return result;
	}

	public List<MedicalRecord> findByAllergy(String allergy) {
		ArrayList<MedicalRecord> result = new ArrayList<MedicalRecord>();
		for (MedicalRecord medicalRecord : medicalRecords) {
			for (String allergyInMedicalRecord : medicalRecord.getAllergies()) {
				if (allergyInMedicalRecord.equals(allergy))
					result.add(medicalRecord);
			}
		}
		return result;
	}

	/*
	 * search by EXACT allergy list TODO rewrite so it returns every MedicalRecord
	 * that contains specified allergies
	 */
	public List<MedicalRecord> findByAllergies(ArrayList<String> allergies) {
		ArrayList<MedicalRecord> result = new ArrayList<MedicalRecord>();
		for (MedicalRecord medicalRecord : medicalRecords) {
			if (allergies.equals(medicalRecord.getAllergies()))
				result.add(medicalRecord);
		}
		return result;
	}

	public boolean save(MedicalRecord medicalRecord) {
		boolean isInDB = false;
		int i = 0;
		while (i < medicalRecords.size() && !isInDB) {
			MedicalRecord medicalRecordInDB = medicalRecords.get(i);
			if (medicalRecordInDB.equals(medicalRecord)) {
				isInDB = true;
				break;
			}
			i++;
		}
		if (!isInDB)
			medicalRecords.add(medicalRecord);
		return !isInDB;
	}

}
