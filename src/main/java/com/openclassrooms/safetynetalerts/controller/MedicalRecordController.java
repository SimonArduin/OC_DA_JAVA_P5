package com.openclassrooms.safetynetalerts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

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
		if (medicalRecord == null) {
			logger.error(String.format("bad request on /medicalrecord PUT, args : %s", medicalRecord));
			return ResponseEntity.badRequest().build();
		}
		MedicalRecord putMedicalRecord = medicalRecordService.putMedicalRecord(medicalRecord);
		if (putMedicalRecord == null || putMedicalRecord.isEmpty()) {
			logger.error(String.format("no medicalrecord found on /medicalrecord PUT, args : %s", medicalRecord));
			return ResponseEntity.notFound().build();
		}
		logger.info(String.format("successful request on /medicalrecord PUT, args : %s", medicalRecord));
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
		if (medicalRecord == null) {
			logger.error(String.format("bad request on /medicalrecord POST, args : %s", medicalRecord));
			return ResponseEntity.badRequest().build();
		}
		MedicalRecord postedMedicalRecord = medicalRecordService.postMedicalRecord(medicalRecord);
		if (postedMedicalRecord == null || postedMedicalRecord.isEmpty()) {
			logger.error(String.format("no medicalrecord found on /medicalrecord POST, args : %s", medicalRecord));
			return ResponseEntity.status(409).build();
		}
		logger.info(String.format("successful request on /medicalrecord POST, args : %s", medicalRecord));
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
		if (medicalRecord == null) {
			logger.error(String.format("bad request on /medicalrecord DELETE, args : %s", medicalRecord));
			return ResponseEntity.badRequest().build();
		}
		MedicalRecord deletedMedicalRecord = medicalRecordService.deleteMedicalRecord(medicalRecord);
		if (deletedMedicalRecord == null || deletedMedicalRecord.isEmpty()) {
			logger.error(String.format("no medicalrecord found on /medicalrecord DELETE, args : %s", medicalRecord));
			return ResponseEntity.notFound().build();
		}
		logger.info(String.format("successful request on /medicalrecord DELETE, args : %s", medicalRecord));
		return ResponseEntity.ok().body(deletedMedicalRecord);
	}
}
