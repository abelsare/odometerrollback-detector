package com.carfax.problem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.carfax.problem.dto.VehicleRecordDTO;


@Service
public class VehicleRecordFetcherServiceImpl implements VehicleRecordFetcherService {

	private static final String remoteURL = "https://s3-eu-west1.amazonaws.com/coding-challenge.carfax.eu/";
	
	private RestTemplate restTemplate;
	
	@Autowired
	public VehicleRecordFetcherServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<VehicleRecordDTO> getVehicleRecords(String vin) {
		String requestURL = remoteURL + vin;
		List<VehicleRecordDTO> vehicleRecords = restTemplate.getForObject(requestURL, 
				List.class);
		return vehicleRecords;
	}

}
