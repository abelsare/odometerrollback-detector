package com.carfax.problem.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carfax.problem.dto.VehicleRecordDTO;
import com.carfax.problem.service.OdometerRollbackDetectorService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Exposes API for odometer rollback detection requests 
 *
 */
@Slf4j
@RestController
@RequestMapping("/carfax/analyze")
public class OdometerRollbackDetectorController {
	
	private OdometerRollbackDetectorService odometerRollbackDetectorService;
	
	@Autowired
	public OdometerRollbackDetectorController(OdometerRollbackDetectorService odometerRollbackDetectorService) {
		this.odometerRollbackDetectorService = odometerRollbackDetectorService;
	}

	@GetMapping(value = "/odometer-rollback", params = {"vin!="},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "Endpoint that analyzes odometer rollback for the given vin")
	List<VehicleRecordDTO> detectOdometerFallback(@RequestParam("vin") String vin) {
		log.info("Received request with vin:{}", vin);
		return null;
		
	}
}
