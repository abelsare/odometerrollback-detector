package com.carfax.problem.converter;

import java.util.List;

import com.carfax.problem.dto.ResponseRecordsDTO;
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
				.serviceDetails(vehicleRecord.getServiceDetails())
				.hasOdometerRollback(recordHasOdometerRollback ? true : null)
				.build();
	}
	
	public static ResponseRecordsDTO buildApiResponse(List<RollbackResponseDTO> rollbackRecords) {
		return ResponseRecordsDTO.builder().records(rollbackRecords).build();
	}

}
