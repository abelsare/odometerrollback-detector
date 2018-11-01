package com.carfax.problem.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carfax.problem.dto.ResponseRecordsDTO;
import com.carfax.problem.exception.NoMatchingDataException;
import com.carfax.problem.service.OdometerRollbackDetectorService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Exposes API for odometer rollback detection requests 
 *
 */
@Slf4j
@RestController
@RequestMapping("/odometer-rollback")
public class OdometerRollbackDetectorController {
	
	private OdometerRollbackDetectorService odometerRollbackDetectorService;
	
	@Autowired
	public OdometerRollbackDetectorController(OdometerRollbackDetectorService odometerRollbackDetectorService) {
		this.odometerRollbackDetectorService = odometerRollbackDetectorService;
	}

	@GetMapping(value = "/{vin}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "Endpoint that analyzes odometer rollback for the given vin")
	ResponseRecordsDTO detectOdometerRollback(@PathVariable("vin") String vin) throws NoMatchingDataException {
		log.info("Received request with vin:{}", vin);
		
		ResponseRecordsDTO rollbackRecords = odometerRollbackDetectorService.detectOdometerRollback(vin);
		
		return rollbackRecords;
		
	}
}
