package com.openclassrooms.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.service.MedicalRecordService;

@RestController
@EnableWebMvc
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

	@Autowired
	private MedicalRecordService medicalRecordService;

	/**
	 * Put - Changes the fields of a medicalRecord in the database
	 * 
	 * @param - A MedicalRecord containing the new information
	 * 
	 *          If one of this medicalRecord's field is empty, the field of the medicalRecord in
	 *          the database will remain the same
	 * @return - A MedicalRecord corresponding to the modified medicalRecord
	 */
	@PutMapping
	public ResponseEntity<MedicalRecord> putMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		if (medicalRecord == null)
			return ResponseEntity.badRequest().build();
		MedicalRecord putMedicalRecord = medicalRecordService.putMedicalRecord(medicalRecord);
		if (putMedicalRecord == null || putMedicalRecord.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.created(null).body(putMedicalRecord);
	}

	/**
	 * Post - Adds a new medicalRecord to the database
	 * 
	 * @param - A MedicalRecord corresponding to the new medicalRecord
	 * @return - A MedicalRecord corresponding to the added medicalRecord
	 */
	@PostMapping
	public ResponseEntity<MedicalRecord> postMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		if (medicalRecord == null)
			return ResponseEntity.badRequest().build();
		MedicalRecord postedMedicalRecord = medicalRecordService.postMedicalRecord(medicalRecord);
		if (postedMedicalRecord == null || postedMedicalRecord.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.created(null).body(postedMedicalRecord);
	}

	/**
	 * Delete - Removes a medicalRecord from the database
	 * 
	 * @param - A MedicalRecord corresponding to the medicalRecord to be deleted
	 * @return - A MedicalRecord corresponding to the deleted medicalRecord
	 */
	@DeleteMapping
	public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		if (medicalRecord == null)
			return ResponseEntity.badRequest().build();
		MedicalRecord deletedMedicalRecord = medicalRecordService.deleteMedicalRecord(medicalRecord);
		if (deletedMedicalRecord == null || deletedMedicalRecord.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(deletedMedicalRecord);
	}
}
