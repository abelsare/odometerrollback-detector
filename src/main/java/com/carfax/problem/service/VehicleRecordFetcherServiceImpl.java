package com.carfax.problem.service;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
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
	
	private Validator validator;
	
	@Autowired
	public VehicleRecordFetcherServiceImpl(RestTemplate restTemplate, Validator validator) {
		super();
		this.restTemplate = restTemplate;
		this.validator = validator;
	}


	@Override
	public List<VehicleRecordDTO> getVehicleRecords(String vin) throws NoMatchingDataException {
		String requestURL = REMOTE_URL + vin;
		List<VehicleRecordDTO> vehicleRecords = null;
		
		log.debug("Fetching vehicle records for vin:{}", vin);
		
		try {
			VehicleRecordsDTO response = restTemplate.getForObject(requestURL, VehicleRecordsDTO.class);
			if(response != null) {
				validateUpstreamResponse(response);
				vehicleRecords = response.getVehicleRecords();			
			} else {
				throwNoDataFoundException(vin);
			}
		} catch (RestClientException e) {
			//Remote API throws RestClientException when records are not available
			e.printStackTrace();
			throwNoDataFoundException(vin);
		}
		
		return vehicleRecords;
	}
	
	private void throwNoDataFoundException(String vin) throws NoMatchingDataException {
		String errorMessage = "No matching data found for vin:" +vin;
		log.error(errorMessage);
		throw new NoMatchingDataException(errorMessage);
	}
	
	private void validateUpstreamResponse(VehicleRecordsDTO response) {
		Set<ConstraintViolation<VehicleRecordsDTO>> validationResults = validator.validate(response);

        if (validationResults.size() > 0) {
            String errorMessage = "Invalid response from Carfax remote API";
            log.error(errorMessage);
            throw new ValidationException(errorMessage);
        }
	}

}
