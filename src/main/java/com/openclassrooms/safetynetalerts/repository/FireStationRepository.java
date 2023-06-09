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

	public List<FireStation> findAll() {
		return dataBase.getFireStations();
	}

	public List<FireStation> find(FireStation fireStation) {
		return dataBase.getFireStations(fireStation);
	}

	public List<FireStation> findByAddress(String address) {
		return dataBase.getFireStations(new FireStation(address, ""));
	}

	public List<FireStation> findByStation(String station) {
		return dataBase.getFireStations(new FireStation("", station));
	}

	public FireStation save(FireStation fireStation) {
		return dataBase.addFireStation(fireStation);
	}

}
