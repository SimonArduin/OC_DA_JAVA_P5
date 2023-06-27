package com.openclassrooms.safetynetalerts.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.model.FireStation;

@Repository
public class FireStationRepository {
	
	private static Logger logger = LoggerFactory.getLogger(FireStationRepository.class);
	
	private DataBase dataBase;
	
	public FireStationRepository() {
		this(DataBase.getDataBase());
		logger.debug("call of FireStationRepository()");
	}
	
	public FireStationRepository(DataBase dataBase) {
		this.dataBase = dataBase;
		logger.debug(String.format("call of putFireStation, args : %s", dataBase));
	}

	public FireStation delete(FireStation fireStation) {
		logger.debug(String.format("call of delete, args : %s", fireStation));
		return dataBase.removeFireStation(fireStation);
	}

	public List<FireStation> get(FireStation fireStation) {
		logger.debug(String.format("call of get, args : %s", fireStation));
		return dataBase.getFireStations(fireStation);
	}
	
	public FireStation save(FireStation fireStation) {
		logger.debug(String.format("call of save, args : %s", fireStation));
		return dataBase.addFireStation(fireStation);
	}

}
