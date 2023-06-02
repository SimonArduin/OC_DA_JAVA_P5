package com.openclassrooms.safetynetalerts.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;

@Repository
public class MedicalRecordRepository {

	private ArrayList<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>(Arrays.asList(
			new MedicalRecord("John", "Boyd", "03/06/1984",
					new ArrayList<String>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
					new ArrayList<String>(Arrays.asList("nillacilan"))),
			new MedicalRecord("Jacob", "Boyd", "03/06/1989",
					new ArrayList<String>(Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg")),
					new ArrayList<String>(Arrays.asList())),
			new MedicalRecord("Tenley", "Boyd", "02/18/2012", new ArrayList<String>(Arrays.asList()),
					new ArrayList<String>(Arrays.asList("peanut"))),
			new MedicalRecord("Roger", "Boyd", "09/06/2017", new ArrayList<String>(Arrays.asList()),
					new ArrayList<String>(Arrays.asList())),
			new MedicalRecord("Felicia", "Boyd", "01/08/1986",
					new ArrayList<String>(Arrays.asList("tetracyclaz:650mg")),
					new ArrayList<String>(Arrays.asList("xilliathal"))),
			new MedicalRecord("Jonanathan", "Marrack", "01/03/1989", new ArrayList<String>(Arrays.asList()),
					new ArrayList<String>(Arrays.asList())),
			new MedicalRecord("Tessa", "Carman", "02/18/2012", new ArrayList<String>(Arrays.asList()),
					new ArrayList<String>(Arrays.asList())),
			new MedicalRecord("Peter", "Duncan", "09/06/2000", new ArrayList<String>(Arrays.asList()),
					new ArrayList<String>(Arrays.asList("shellfish"))),
			new MedicalRecord("Foster", "Shepard", "01/08/1980", new ArrayList<String>(Arrays.asList()),
					new ArrayList<String>(Arrays.asList())),
			new MedicalRecord("Tony", "Cooper", "03/06/1994",
					new ArrayList<String>(Arrays.asList("hydrapermazol:300mg", "dodoxadin:30mg")),
					new ArrayList<String>(Arrays.asList("shellfish"))),
			new MedicalRecord("Lily", "Cooper", "03/06/1994", new ArrayList<String>(Arrays.asList()),
					new ArrayList<String>(Arrays.asList())),
			new MedicalRecord("Sophia", "Zemicks", "03/06/1988",
					new ArrayList<String>(
							Arrays.asList("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg")),
					new ArrayList<String>(Arrays.asList("peanut", "shellfish", "aznol"))),
			new MedicalRecord("Warren", "Zemicks", "03/06/1985", new ArrayList<String>(Arrays.asList()),
					new ArrayList<String>(Arrays.asList())),
			new MedicalRecord("Zach", "Zemicks", "03/06/2017", new ArrayList<String>(Arrays.asList()),
					new ArrayList<String>(Arrays.asList())),
			new MedicalRecord("Reginold", "Walker", "08/30/1979", new ArrayList<String>(Arrays.asList("thradox:700mg")),
					new ArrayList<String>(Arrays.asList("illisoxian"))),
			new MedicalRecord("Jamie", "Peters", "03/06/1982", new ArrayList<String>(Arrays.asList()),
					new ArrayList<String>(Arrays.asList())),
			new MedicalRecord("Ron", "Peters", "04/06/1965", new ArrayList<String>(Arrays.asList()),
					new ArrayList<String>(Arrays.asList())),
			new MedicalRecord("Allison", "Boyd", "03/15/1965", new ArrayList<String>(Arrays.asList("aznol:200mg")),
					new ArrayList<String>(Arrays.asList("nillacilan"))),
			new MedicalRecord("Brian", "Stelzer", "12/06/1975",
					new ArrayList<String>(Arrays.asList("ibupurin:200mg", "hydrapermazol:400mg")),
					new ArrayList<String>(Arrays.asList("nillacilan"))),
			new MedicalRecord("Shawna", "Stelzer", "07/08/1980", new ArrayList<String>(Arrays.asList()),
					new ArrayList<String>(Arrays.asList())),
			new MedicalRecord("Kendrik", "Stelzer", "03/06/2014",
					new ArrayList<String>(Arrays.asList("noxidian:100mg", "pharmacol:2500mg")),
					new ArrayList<String>(Arrays.asList())),
			new MedicalRecord("Clive", "Ferguson", "03/06/1994", new ArrayList<String>(Arrays.asList()),
					new ArrayList<String>(Arrays.asList())),
			new MedicalRecord("Eric", "Cadigan", "08/06/1945",
					new ArrayList<String>(Arrays.asList("tradoxidine:400mg")),
					new ArrayList<String>(Arrays.asList()))));

	public void resetDataBase() {
		medicalRecords = new ArrayList<MedicalRecord>(Arrays.asList(
				new MedicalRecord("John", "Boyd", "03/06/1984",
						new ArrayList<String>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
						new ArrayList<String>(Arrays.asList("nillacilan"))),
				new MedicalRecord("Jacob", "Boyd", "03/06/1989",
						new ArrayList<String>(Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg")),
						new ArrayList<String>(Arrays.asList())),
				new MedicalRecord("Tenley", "Boyd", "02/18/2012", new ArrayList<String>(Arrays.asList()),
						new ArrayList<String>(Arrays.asList("peanut"))),
				new MedicalRecord("Roger", "Boyd", "09/06/2017", new ArrayList<String>(Arrays.asList()),
						new ArrayList<String>(Arrays.asList())),
				new MedicalRecord("Felicia", "Boyd", "01/08/1986",
						new ArrayList<String>(Arrays.asList("tetracyclaz:650mg")),
						new ArrayList<String>(Arrays.asList("xilliathal"))),
				new MedicalRecord("Jonanathan", "Marrack", "01/03/1989", new ArrayList<String>(Arrays.asList()),
						new ArrayList<String>(Arrays.asList())),
				new MedicalRecord("Tessa", "Carman", "02/18/2012", new ArrayList<String>(Arrays.asList()),
						new ArrayList<String>(Arrays.asList())),
				new MedicalRecord("Peter", "Duncan", "09/06/2000", new ArrayList<String>(Arrays.asList()),
						new ArrayList<String>(Arrays.asList("shellfish"))),
				new MedicalRecord("Foster", "Shepard", "01/08/1980", new ArrayList<String>(Arrays.asList()),
						new ArrayList<String>(Arrays.asList())),
				new MedicalRecord("Tony", "Cooper", "03/06/1994",
						new ArrayList<String>(Arrays.asList("hydrapermazol:300mg", "dodoxadin:30mg")),
						new ArrayList<String>(Arrays.asList("shellfish"))),
				new MedicalRecord("Lily", "Cooper", "03/06/1994", new ArrayList<String>(Arrays.asList()),
						new ArrayList<String>(Arrays.asList())),
				new MedicalRecord("Sophia", "Zemicks", "03/06/1988",
						new ArrayList<String>(Arrays.asList("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg",
								"terazine:500mg")),
						new ArrayList<String>(Arrays.asList("peanut", "shellfish", "aznol"))),
				new MedicalRecord("Warren", "Zemicks", "03/06/1985", new ArrayList<String>(Arrays.asList()),
						new ArrayList<String>(Arrays.asList())),
				new MedicalRecord("Zach", "Zemicks", "03/06/2017", new ArrayList<String>(Arrays.asList()),
						new ArrayList<String>(Arrays.asList())),
				new MedicalRecord("Reginold", "Walker", "08/30/1979",
						new ArrayList<String>(Arrays.asList("thradox:700mg")),
						new ArrayList<String>(Arrays.asList("illisoxian"))),
				new MedicalRecord("Jamie", "Peters", "03/06/1982", new ArrayList<String>(Arrays.asList()),
						new ArrayList<String>(Arrays.asList())),
				new MedicalRecord("Ron", "Peters", "04/06/1965", new ArrayList<String>(Arrays.asList()),
						new ArrayList<String>(Arrays.asList())),
				new MedicalRecord("Allison", "Boyd", "03/15/1965", new ArrayList<String>(Arrays.asList("aznol:200mg")),
						new ArrayList<String>(Arrays.asList("nillacilan"))),
				new MedicalRecord("Brian", "Stelzer", "12/06/1975",
						new ArrayList<String>(Arrays.asList("ibupurin:200mg", "hydrapermazol:400mg")),
						new ArrayList<String>(Arrays.asList("nillacilan"))),
				new MedicalRecord("Shawna", "Stelzer", "07/08/1980", new ArrayList<String>(Arrays.asList()),
						new ArrayList<String>(Arrays.asList())),
				new MedicalRecord("Kendrik", "Stelzer", "03/06/2014",
						new ArrayList<String>(Arrays.asList("noxidian:100mg", "pharmacol:2500mg")),
						new ArrayList<String>(Arrays.asList())),
				new MedicalRecord("Clive", "Ferguson", "03/06/1994", new ArrayList<String>(Arrays.asList()),
						new ArrayList<String>(Arrays.asList())),
				new MedicalRecord("Eric", "Cadigan", "08/06/1945",
						new ArrayList<String>(Arrays.asList("tradoxidine:400mg")),
						new ArrayList<String>(Arrays.asList()))));
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
