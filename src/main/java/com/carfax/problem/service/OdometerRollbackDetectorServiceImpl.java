package com.carfax.problem.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carfax.problem.dto.ResponseRecordsDTO;
import com.carfax.problem.dto.VehicleRecordDTO;
import com.carfax.problem.exception.NoMatchingDataException;

import lombok.extern.slf4j.Slf4j;

/**
 * Performs odometer rollback detection for the given vin 
 *
 */
@Slf4j
@Service
public class OdometerRollbackDetectorServiceImpl implements OdometerRollbackDetectorService {

	private VehicleRecordFetcherService vehicleRecordFetcherService;
	
	private OdometerRollbackResponseGeneratorService rollbackResponseGeneratorService;
	
	@Autowired
	public OdometerRollbackDetectorServiceImpl(VehicleRecordFetcherService vehicleRecordFetcherService,
			OdometerRollbackResponseGeneratorService rollbackResponseGeneratorService) {
		super();
		this.vehicleRecordFetcherService = vehicleRecordFetcherService;
		this.rollbackResponseGeneratorService = rollbackResponseGeneratorService;
	}


	@Override
	public ResponseRecordsDTO detectOdometerRollback(String vin) throws NoMatchingDataException {
		log.debug("Detecting odometer rollback for vin:{}", vin);
		
		//Fetch the vehicle records from Carfax API
		List<VehicleRecordDTO> vehicleRecords = vehicleRecordFetcherService.getVehicleRecords(vin);
		
		if(vehicleRecords == null || vehicleRecords.isEmpty()) {
			String errorMessage = "No matching vehicle records found for vin: " + vin;
			log.debug(errorMessage);
			throw new NoMatchingDataException(errorMessage);
		}
		
		//Detect odometer tampering in each record
		markOdometerRollback(vehicleRecords);
		
		//Generate output with required extra property indicating odometer tampering
		ResponseRecordsDTO odometerRollbackData = rollbackResponseGeneratorService.
				buildOdometerRollbackResponse(vehicleRecords);
		
		return odometerRollbackData;
	}
	
	protected void markOdometerRollback(List<VehicleRecordDTO> vehicleRecords) {
		//Sort the vehicle records on date field
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    Collections.sort(vehicleRecords, (s1, s2) -> 
	    	LocalDate.parse(s1.getDate(), formatter).
	            compareTo(LocalDate.parse(s2.getDate(), formatter)));
	    
		//Find the first record with odometer tampering and mark it.
		//Leave the records after that unchanged.
		OptionalInt tamperedRecordIndex = IntStream.range(0, vehicleRecords.size() - 1)
        	.filter(i -> vehicleRecords.get(i).getOdometerReading() >= vehicleRecords.get(i+1).getOdometerReading())
        	.findFirst();
		
		if(tamperedRecordIndex.isPresent()) {
			vehicleRecords.get(tamperedRecordIndex.getAsInt() + 1).setHasOdometerRollback(Boolean.TRUE);			
		}
	
	}
}
