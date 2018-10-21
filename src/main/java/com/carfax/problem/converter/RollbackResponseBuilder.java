package com.carfax.problem.converter;

import com.carfax.problem.dto.RollbackResponseDTO;
import com.carfax.problem.dto.VehicleRecordDTO;

/**
 * Builds response objects with odometer rollback information
 *
 */
public class RollbackResponseBuilder {
	
	/**
	 * Generates response object from the vehicle record
	 * @param vehicleRecord vehicle record
	 * @return response object with the rollback information
	 */
	public static RollbackResponseDTO buildOdometerRollbackResponse(VehicleRecordDTO vehicleRecord) {
		boolean recordHasOdometerRollback = vehicleRecord.getHasOdometerRollback();
		
		return RollbackResponseDTO.builder()
				.vin(vehicleRecord.getVin())
				.dataProviderId(vehicleRecord.getDataProviderId())
				.date(vehicleRecord.getDate())
				.odometerReading(vehicleRecord.getOdometerReading())
				.hasOdometerRollback(recordHasOdometerRollback ? true : null)
				.build();
	}

}
