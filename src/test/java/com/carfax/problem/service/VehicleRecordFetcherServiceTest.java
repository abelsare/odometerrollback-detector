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
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

/**
 * Unit test for {@link VehicleRecordFetcherService} 
 *
 */
public class VehicleRecordFetcherServiceTest {

	private static final String REMOTE_URL = "https://s3-eu-west-1.amazonaws.com/coding-challenge.carfax.eu/";
	
	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private Validator validator;
	
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
		when(validator.validate(vehicleRecordsDTO)).thenReturn(new HashSet<>());
		
		List<VehicleRecordDTO> vehicleRecords = vehicleRecordFetcherService.getVehicleRecords(vin);
		
		assertNotNull("Expecting data to be returned", vehicleRecords);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFetchInvalidVehicleRecords() throws NoMatchingDataException {
		String vin = "123";
		String url = REMOTE_URL + vin;
		Set<ConstraintViolation<VehicleRecordsDTO>> validationErrors = new HashSet<>();
		validationErrors.add(mock(ConstraintViolation.class));
		
		when(restTemplate.getForObject(url, VehicleRecordsDTO.class)).thenReturn(vehicleRecordsDTO);
		when(vehicleRecordsDTO.getVehicleRecords()).thenReturn(new ArrayList<>());
		when(validator.validate(vehicleRecordsDTO)).thenReturn(validationErrors);
		
		try {			
			 vehicleRecordFetcherService.getVehicleRecords(vin);
		} catch(ValidationException e) {
			
			assertEquals("Invalid response from Carfax remote API", e.getMessage());
		}
		
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
