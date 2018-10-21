package com.carfax.problem.service;

import java.util.List;

import com.carfax.problem.dto.RollbackResponseDTO;
import com.carfax.problem.dto.VehicleRecordDTO;
import com.carfax.problem.exception.NoMatchingDataException;

/**
 * Generates API response with rollback information
 *
 */
public interface OdometerRollbackResponseGeneratorService {

	/**
	 * Generates response with the odometer rollback information
	 * @param vehicleRecords vehicle records fetched for a given Vin
	 * @return response objects
	 */
	List<RollbackResponseDTO> buildOdometerRollbackResponse(List<VehicleRecordDTO> vehicleRecords)
			throws NoMatchingDataException;
}
