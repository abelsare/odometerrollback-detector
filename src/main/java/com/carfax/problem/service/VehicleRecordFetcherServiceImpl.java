package com.carfax.problem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.carfax.problem.dto.VehicleRecordDTO;
import com.carfax.problem.dto.VehicleRecordsDTO;
import com.carfax.problem.exception.NoMatchingDataException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleRecordFetcherServiceImpl implements VehicleRecordFetcherService {

	private static final String REMOTE_URL = "https://s3-eu-west-1.amazonaws.com/coding-challenge.carfax.eu/";
	
	private RestTemplate restTemplate;
	
	@Autowired
	public VehicleRecordFetcherServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}


	@Override
	public List<VehicleRecordDTO> getVehicleRecords(String vin) throws NoMatchingDataException {
		String requestURL = REMOTE_URL + vin;
		List<VehicleRecordDTO> vehicleRecords = null;
		
		log.debug("Fetching vehicle records for vin:{}", vin);
		
		
		VehicleRecordsDTO response = restTemplate.getForObject(requestURL, VehicleRecordsDTO.class);
		
		if(response != null) {
			vehicleRecords = response.getVehicleRecords();			
		} else {
			String errorMessage = "No matching data found for vin:" +vin;
			log.error(errorMessage);
			throw new NoMatchingDataException(errorMessage);
		}
		
		return vehicleRecords;
	}

}
