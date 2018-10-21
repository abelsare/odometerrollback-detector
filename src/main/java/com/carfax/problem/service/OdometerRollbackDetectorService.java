package com.carfax.problem.service;

import java.util.List;

import com.carfax.problem.dto.RollbackResponseDTO;
import com.carfax.problem.exception.NoMatchingDataException;

/**
 * Performs odometer rollback detection.
 *
 */
public interface OdometerRollbackDetectorService {
	
	/**
	 * Detects odometer rollback event in the records corresponding to the given VIN.
	 * The response has such records flagged.
	 * @param vin VIN for which the odometer rollback needs to be detected
	 * @return records corresponding to the given VIN with records with odometer rollback 
	 * flagged.
	 * @throws NoMatchingDataException if the remote api returns no matching records
	 */
	List<RollbackResponseDTO> detectOdometerRollback(String vin) throws NoMatchingDataException;
}
