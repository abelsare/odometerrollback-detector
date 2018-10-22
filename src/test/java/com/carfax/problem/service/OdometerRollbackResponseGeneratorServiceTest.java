package com.carfax.problem.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import com.carfax.problem.dto.RollbackResponseDTO;
import com.carfax.problem.dto.VehicleRecordDTO;
import com.carfax.problem.exception.NoMatchingDataException;

/**
 * Unit tests for {@link OdometerRollbackResponseGeneratorService}
 *
 */
public class OdometerRollbackResponseGeneratorServiceTest {

	private OdometerRollbackResponseGeneratorServiceImpl rollbackResponseGeneratorService
		= new OdometerRollbackResponseGeneratorServiceImpl();
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testGenerateResponseNullRecords() {
		try {
			rollbackResponseGeneratorService.buildOdometerRollbackResponse(null);
		} catch (NoMatchingDataException e) {
			assertEquals("No vehicle records found for conversion.", e.getMessage());
		}
	}
	
	@Test
	public void testGenerateResponseNoRecords() {
		try {
			rollbackResponseGeneratorService.buildOdometerRollbackResponse(new ArrayList<>());
		} catch (NoMatchingDataException e) {
			assertEquals("No vehicle records found for conversion.", e.getMessage());
		}
	}

	@Test
	public void testGenerateRollbackEventResponse() throws NoMatchingDataException {
		VehicleRecordDTO vehicleRecord1 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-01-02", 
				10, 10010, Arrays.asList("Oil changed", "Tires rotated"), false);
		VehicleRecordDTO vehicleRecord2 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-06-20", 
				10, 12100, Arrays.asList("Tires replaced"), false);
		VehicleRecordDTO vehicleRecord3 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2018-02-12", 
				10, 15100, Arrays.asList("Windshield replaced"), false);
		VehicleRecordDTO vehicleRecord4 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2018-04-01", 
				10, 5600, Arrays.asList("Air dam replaced", "Oil service"), true);
		
		List<VehicleRecordDTO> vehicleRecords = Arrays.asList(vehicleRecord1, vehicleRecord2,
				vehicleRecord3, vehicleRecord4);
		
		List<RollbackResponseDTO> response = rollbackResponseGeneratorService.
				buildOdometerRollbackResponse(vehicleRecords);
		
		assertNull("The first record does not have odometer rollback", response.get(0).getHasOdometerRollback());
		assertNull("The second record does not have odometer rollback", response.get(1).getHasOdometerRollback());
		assertNull("The third record does not have odometer rollback", response.get(2).getHasOdometerRollback());
		assertTrue("The last record has odometer rollback", response.get(3).getHasOdometerRollback());
		
	}
	
	@Test
	public void testGenerateNoRollbackEventResponse() throws NoMatchingDataException {
		
		VehicleRecordDTO vehicleRecord1 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-01-02", 
				10, 10010, Arrays.asList("Oil changed", "Tires rotated"), false);
		VehicleRecordDTO vehicleRecord2 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-06-20", 
				10, 12100, Arrays.asList("Tires replaced"), false);
		VehicleRecordDTO vehicleRecord3 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2018-02-12", 
				10, 15100, Arrays.asList("Windshield replaced"), false);
		VehicleRecordDTO vehicleRecord4 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2018-04-01", 
				10, 56000, Arrays.asList("Air dam replaced", "Oil service"), false);
		
		List<VehicleRecordDTO> vehicleRecords = Arrays.asList(vehicleRecord1, vehicleRecord2,
				vehicleRecord3, vehicleRecord4);
		
		List<RollbackResponseDTO> response = rollbackResponseGeneratorService.
				buildOdometerRollbackResponse(vehicleRecords);
		
		assertNull("The first record does not have odometer rollback", response.get(0).getHasOdometerRollback());
		assertNull("The second record does not have odometer rollback", response.get(1).getHasOdometerRollback());
		assertNull("The third record does not have odometer rollback", response.get(2).getHasOdometerRollback());
		assertNull("The last record does not have odometer rollback", response.get(3).getHasOdometerRollback());
		
	}
	
	private VehicleRecordDTO buildVehicleRecordDTO(String vin, String date, Integer dataProviderId,
			Integer odometerReading, List<String> serviceDetails, boolean hasOdometerRollback) {
		VehicleRecordDTO vehicleOrder = mock(VehicleRecordDTO.class);
		
		when(vehicleOrder.getDataProviderId()).thenReturn(dataProviderId);
		when(vehicleOrder.getDate()).thenReturn(date);
		when(vehicleOrder.getHasOdometerRollback()).thenReturn(hasOdometerRollback);
		when(vehicleOrder.getOdometerReading()).thenReturn(odometerReading);
		when(vehicleOrder.getServiceDetails()).thenReturn(serviceDetails);
		when(vehicleOrder.getVin()).thenReturn(vin);
		
		return vehicleOrder;
	}

}
