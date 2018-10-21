package com.carfax.problem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.carfax.problem.converter.RollbackResponseBuilder;
import com.carfax.problem.dto.RollbackResponseDTO;
import com.carfax.problem.dto.VehicleRecordDTO;
import com.carfax.problem.exception.NoMatchingDataException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OdometerRollbackResponseGeneratorServiceImpl implements OdometerRollbackResponseGeneratorService {

	@Override
	public List<RollbackResponseDTO> buildOdometerRollbackResponse(List<VehicleRecordDTO> vehicleRecords) throws NoMatchingDataException {
		if(vehicleRecords == null || vehicleRecords.isEmpty()) {
			String errorMessage = "No vehicle records found for conversion";
			log.debug(errorMessage);
			throw new NoMatchingDataException(errorMessage);
		}
		
		List<RollbackResponseDTO> odometerRollbackResponse = vehicleRecords.stream().map(
				vehicleRecord -> RollbackResponseBuilder.
					buildOdometerRollbackResponse(vehicleRecord)).collect(Collectors.toList());
		
		return odometerRollbackResponse;
	}

}
