package com.carfax.problem.service;

import java.util.List;

import com.carfax.problem.dto.VehicleRecordDTO;

/**
 * Fetches vehicle records from remote Carfax API
 *
 */
public interface VehicleRecordFetcherService {
	
	/**
	 * Fetches vehicle records from the remote API
	 * @param vin VIN for which the records need to be fetched
	 * @return records corresponding to the given VIN
	 */
	List<VehicleRecordDTO> getVehicleRecords(String vin);

}
