package com.carfax.problem.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.carfax.problem.dto.VehicleRecordDTO;
import com.carfax.problem.dto.VehicleRecordsDTO;
import com.carfax.problem.exception.NoMatchingDataException;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for {@link VehicleRecordFetcherService} 
 *
 */
public class VehicleRecordFetcherServiceTest {

	private static final String REMOTE_URL = "https://s3-eu-west-1.amazonaws.com/coding-challenge.carfax.eu/";
	
	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private VehicleRecordsDTO vehicleRecordsDTO;
	
	@InjectMocks
	private VehicleRecordFetcherServiceImpl vehicleRecordFetcherService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFetchVehicleRecords() throws NoMatchingDataException {
		String vin = "123";
		String url = REMOTE_URL + vin;
		
		when(restTemplate.getForObject(url, VehicleRecordsDTO.class)).thenReturn(vehicleRecordsDTO);
		when(vehicleRecordsDTO.getVehicleRecords()).thenReturn(new ArrayList<>());
		
		List<VehicleRecordDTO> vehicleRecords = vehicleRecordFetcherService.getVehicleRecords(vin);
		
		assertNotNull("Expecting data to be returned", vehicleRecords);
	}
	
	@Test
	public void testFetchNoVehicleRecords() {
		String vin = "123";
		String url = REMOTE_URL + vin;
		
		when(restTemplate.getForObject(url, VehicleRecordsDTO.class)).thenReturn(null);
		
		List<VehicleRecordDTO> vehicleRecords = null;
		try {
			vehicleRecords = vehicleRecordFetcherService.getVehicleRecords(vin);
		} catch (NoMatchingDataException e) {
			assertEquals("No matching data found for vin:123", e.getMessage());
			assertNull("Expecting no data to be returned", vehicleRecords);
		}
		
	}

}
