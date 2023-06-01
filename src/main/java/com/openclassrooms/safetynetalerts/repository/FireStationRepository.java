package com.openclassrooms.safetynetalerts.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public void resetDataBase() {
		fireStations = new ArrayList<FireStation>(Arrays.asList(new FireStation("1509 Culver St", 3),
				new FireStation("29 15th St", 2), new FireStation("834 Binoc Ave", 3),
				new FireStation("644 Gershwin Cir", 1), new FireStation("748 Townings Dr", 3),
				new FireStation("112 Steppes Pl", 3), new FireStation("489 Manchester St", 4),
				new FireStation("892 Downing Ct", 2), new FireStation("908 73rd St", 1),
				new FireStation("112 Steppes Pl", 4), new FireStation("947 E. Rose Dr", 1),
				new FireStation("748 Townings Dr", 3), new FireStation("951 LoneTree Rd", 2)));
	}

	public boolean delete(FireStation fireStation) {
		boolean isInDB = false;
		int i = 0;
		while (i < fireStations.size() && !isInDB) {
			FireStation fireStationInDB = fireStations.get(i);
			if (fireStationInDB.equals(fireStation)) {
				isInDB = true;
				fireStations.remove(fireStationInDB);
				break;
			}
			i++;
		}
		return isInDB;
	}

	public List<FireStation> findAll() {
		return fireStations;
	}

	public List<FireStation> findByAddress(String address) {
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		for (FireStation fireStation : fireStations) {
			if (address.equals(fireStation.getAddress()))
				result.add(fireStation);
		}
		return result;
	}

	public List<FireStation> findByStationNumber(int stationNumber) {
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		for (FireStation fireStation : fireStations) {
			if (stationNumber == fireStation.getStationNumber())
				result.add(fireStation);
		}
		return result;
	}

	public boolean save(FireStation fireStation) {
		boolean isInDB = false;
		int i = 0;
		while (i < fireStations.size() && !isInDB) {
			FireStation fireStationInDB = fireStations.get(i);
			if (fireStationInDB.equals(fireStation)) {
				isInDB = true;
				break;
			}
			i++;
		}
		if (!isInDB)
			fireStations.add(fireStation);
		return !isInDB;
	}

}
