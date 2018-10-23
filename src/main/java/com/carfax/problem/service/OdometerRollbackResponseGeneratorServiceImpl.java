package com.carfax.problem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.carfax.problem.converter.RollbackResponseBuilder;
import com.carfax.problem.dto.ResponseRecordsDTO;
import com.carfax.problem.dto.RollbackRecordDTO;
import com.carfax.problem.dto.VehicleRecordDTO;
import com.carfax.problem.exception.NoMatchingDataException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OdometerRollbackResponseGeneratorServiceImpl implements OdometerRollbackResponseGeneratorService {

	@Override
	public ResponseRecordsDTO buildOdometerRollbackResponse(List<VehicleRecordDTO> vehicleRecords) throws NoMatchingDataException {
		log.debug("Generating odometer rollback response ...");
		
		if(vehicleRecords == null || vehicleRecords.isEmpty()) {
			String errorMessage = "No vehicle records found for conversion.";
			log.debug(errorMessage);
			throw new NoMatchingDataException(errorMessage);
		}
		
		List<RollbackRecordDTO> rollbackRecords = vehicleRecords.stream().map(
				vehicleRecord -> RollbackResponseBuilder.
					buildOdometerRollbackResponse(vehicleRecord)).collect(Collectors.toList());
		
		ResponseRecordsDTO odometerRollbackResponse = RollbackResponseBuilder.buildApiResponse(rollbackRecords);
		
		return odometerRollbackResponse;
	}

}
