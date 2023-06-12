package com.openclassrooms.safetynetalerts.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.model.FireStation;

@Repository
public class FireStationRepository {
	
	private DataBase dataBase;
	
	public FireStationRepository() {
		this(DataBase.getDataBase());
	}
	
	public FireStationRepository(DataBase dataBase) {
		this.dataBase = dataBase;
	}

	public FireStation delete(FireStation fireStation) {
		return dataBase.removeFireStation(fireStation);
	}

	public List<FireStation> getAll() {
		return dataBase.getFireStations();
	}

	public List<FireStation> get(FireStation fireStation) {
		return dataBase.getFireStations(fireStation);
	}
	
	public FireStation save(FireStation fireStation) {
		return dataBase.addFireStation(fireStation);
	}

}
