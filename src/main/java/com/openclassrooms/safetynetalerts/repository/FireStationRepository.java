package com.openclassrooms.safetynetalerts.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.model.FireStation;

@Repository
public class FireStationRepository {

	private static ArrayList<FireStation> fireStations;
	
	static DataBase dataBase = DataBase.getDataBase();
	
	public static void main(String[] args) {
		fireStations = new ArrayList<FireStation>(dataBase.getFireStations());
		System.out.println(fireStations);
		System.out.println("--- before remove");
		for(FireStation fireStation : dataBase.getFireStations()  ) {
			System.out.println(String.format("fireStation address : %s, station number %s", fireStation.getAddress(), fireStation.getStation()));
		}
		dataBase.removeFireStation(fireStations.get(0));
		System.out.println("--- after remove");
		for(FireStation fireStation : dataBase.getFireStations()  ) {
			System.out.println(String.format("fireStation address : %s, station number %s", fireStation.getAddress(), fireStation.getStation()));
		}
		dataBase.addFireStation(new FireStation("addressTest", "stationTest"));
		System.out.println("--- after add");
		for(FireStation fireStation : dataBase.getFireStations()  ) {
			System.out.println(String.format("fireStation address : %s, station number %s", fireStation.getAddress(), fireStation.getStation()));
		}
		System.out.println("--- search address 748 Townings Dr");
		for(FireStation fireStation : dataBase.getFireStations(new FireStation("748 Townings Dr",""))) {
			System.out.println(String.format("fireStation address : %s, station number %s", fireStation.getAddress(), fireStation.getStation()));
		}
		System.out.println("--- search station 3");
		for(FireStation fireStation : dataBase.getFireStations(new FireStation("","3"))) {
			System.out.println(String.format("fireStation address : %s, station number %s", fireStation.getAddress(), fireStation.getStation()));
		}
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
