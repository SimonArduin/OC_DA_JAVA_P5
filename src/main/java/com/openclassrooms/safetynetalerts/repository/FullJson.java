package com.openclassrooms.safetynetalerts.repository;

import java.util.List;

import com.openclassrooms.safetynetalerts.model.FireStation;
import com.openclassrooms.safetynetalerts.model.MedicalRecord;
import com.openclassrooms.safetynetalerts.model.Person;

public class FullJson {
    public List<FireStation> firestations;
    public List<Person> persons;
    public List<MedicalRecord> medicalrecords;
}