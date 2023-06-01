package com.openclassrooms.safetynetalerts.repository;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynetalerts.model.FireStation;

@Repository
public class FireStationRepository {

	private static ArrayList<FireStation> fireStations = new ArrayList<FireStation>(Arrays.asList(
			new FireStation("1509 Culver St", 3), new FireStation("29 15th St", 2), new FireStation("834 Binoc Ave", 3),
			new FireStation("644 Gershwin Cir", 1), new FireStation("748 Townings Dr", 3),
			new FireStation("112 Steppes Pl", 3), new FireStation("489 Manchester St", 4),
			new FireStation("892 Downing Ct", 2), new FireStation("908 73rd St", 1),
			new FireStation("112 Steppes Pl", 4), new FireStation("947 E. Rose Dr", 1),
			new FireStation("748 Townings Dr", 3), new FireStation("951 LoneTree Rd", 2)));
	
	public boolean delete(FireStation fireStation) {
		return fireStations.remove(fireStation);
	}

	public ArrayList<FireStation> findAll() {
		return fireStations;
	}

	public ArrayList<FireStation> findByAddress(String address) {
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		for (FireStation fireStation : fireStations) {
			if (address.equals(fireStation.getAddress()))
				result.add(fireStation);
		}
		return result;
	}
	
	public ArrayList<FireStation> findByStationNumber(int stationNumber) {
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		for (FireStation fireStation : fireStations) {
			if (stationNumber == fireStation.getStationNumber())
				result.add(fireStation);
		}
		return result;
	}
	
	public FireStation save(FireStation fireStation) {
		//TODO ne pas pouvoir sauvegarder une firestation déjà présente
		fireStations.add(fireStation);
		return fireStation;
	}

}
