package com.carfax.problem.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.carfax.problem.dto.ResponseRecordsDTO;
import com.carfax.problem.dto.RollbackRecordDTO;
import com.carfax.problem.dto.VehicleRecordDTO;
import com.carfax.problem.exception.NoMatchingDataException;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for {@link OdometerRollbackDetectorService}
 *
 */
public class OdometerRollbackDetectorServiceTest {

	@Mock
	private VehicleRecordFetcherService vehicleRecordFetcherService;
	
	@Mock
	private OdometerRollbackResponseGeneratorService rollbackResponseGeneratorService;
	
	@InjectMocks
	private OdometerRollbackDetectorServiceImpl odometerRollbackDetectorService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testOdometerRollbackNoRecords() throws NoMatchingDataException {
		String vin = "123";
		when(vehicleRecordFetcherService.getVehicleRecords(vin)).thenReturn(new ArrayList<>());
		try {
			odometerRollbackDetectorService.detectOdometerRollback(vin);
		} catch (NoMatchingDataException e) {
			assertEquals(e.getMessage(), "No matching vehicle records found for vin: " +vin);
		}
	}
	
	@Test
	public void testOdometerRollbackNullRecords() {
		String vin = "123";
		try {
			when(vehicleRecordFetcherService.getVehicleRecords(vin)).thenReturn(null);
			odometerRollbackDetectorService.detectOdometerRollback(vin);
		} catch (NoMatchingDataException e) {
			assertEquals(e.getMessage(), "No matching vehicle records found for vin: " +vin);
		}
	}
	
	@Test
	public void testOdometerRollbackSingleRecord() throws NoMatchingDataException {
		String vin = "123";
		VehicleRecordDTO vehicleRecord = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-01-02", 
				10, 10010, Arrays.asList("Oil changed", "Tires rotated"));
		
		
		List<VehicleRecordDTO> vehicleRecords = Arrays.asList(vehicleRecord);
		
		when(vehicleRecordFetcherService.getVehicleRecords(vin)).thenReturn(vehicleRecords);
		
		odometerRollbackDetectorService.markOdometerRollback(vehicleRecords);
		
		assertFalse("Expecting the first record to not have odometer tampering", vehicleRecord.getHasOdometerRollback());
	}
	
	@Test
	public void testOdometerRollback() throws NoMatchingDataException {
		String vin = "123";
		VehicleRecordDTO vehicleRecord1 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-01-02", 
				10, 10010, Arrays.asList("Oil changed", "Tires rotated"));
		VehicleRecordDTO vehicleRecord2 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-06-20", 
				10, 12100, Arrays.asList("Tires replaced"));
		VehicleRecordDTO vehicleRecord3 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2018-02-12", 
				10, 15100, Arrays.asList("Windshield replaced"));
		VehicleRecordDTO vehicleRecord4 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2018-04-01", 
				10, 5600, Arrays.asList("Air dam replaced", "Oil service"));
		VehicleRecordDTO vehicleRecord5 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2018-06-01", 
				10, 600, Arrays.asList("Air dam replaced", "Oil service"));
		
		List<VehicleRecordDTO> vehicleRecords = Arrays.asList(vehicleRecord1, vehicleRecord2,
				vehicleRecord3, vehicleRecord4, vehicleRecord5);
		
		when(vehicleRecordFetcherService.getVehicleRecords(vin)).thenReturn(vehicleRecords);
		
		odometerRollbackDetectorService.markOdometerRollback(vehicleRecords);
		
		assertFalse("Expecting the first record to not have odometer tampering", vehicleRecord1.getHasOdometerRollback());
		assertFalse("Expecting the second record to not have odometer tampering", vehicleRecord2.getHasOdometerRollback());
		assertFalse("Expecting the third record to not have odometer tampering", vehicleRecord3.getHasOdometerRollback());
		assertTrue("Expecting the fourth record to have odometer tampering", vehicleRecord4.getHasOdometerRollback());
		assertFalse("Expecting the last record to be untouched", vehicleRecord5.getHasOdometerRollback());
	}
	
	@Test
	public void testNoOdometerRollback() throws NoMatchingDataException {
		String vin = "123";
		VehicleRecordDTO vehicleRecord1 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-01-02", 
				10, 10010, Arrays.asList("Oil changed", "Tires rotated"));
		VehicleRecordDTO vehicleRecord2 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-06-20", 
				10, 12100, Arrays.asList("Tires replaced"));
		VehicleRecordDTO vehicleRecord3 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2018-02-12", 
				10, 15100, Arrays.asList("Windshield replaced"));
		VehicleRecordDTO vehicleRecord4 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2018-04-01", 
				10, 56000, Arrays.asList("Air dam replaced", "Oil service"));
		VehicleRecordDTO vehicleRecord5 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2018-06-01", 
				10, 60000, Arrays.asList("Air dam replaced", "Oil service"));
		
		List<VehicleRecordDTO> vehicleRecords = Arrays.asList(vehicleRecord1, vehicleRecord2,
				vehicleRecord3, vehicleRecord4, vehicleRecord5);
		
		when(vehicleRecordFetcherService.getVehicleRecords(vin)).thenReturn(vehicleRecords);
		
		odometerRollbackDetectorService.markOdometerRollback(vehicleRecords);
		
		assertFalse("Expecting the first record to not have odometer tampering", vehicleRecord1.getHasOdometerRollback());
		assertFalse("Expecting the second record to not have odometer tampering", vehicleRecord2.getHasOdometerRollback());
		assertFalse("Expecting the third record to not have odometer tampering", vehicleRecord3.getHasOdometerRollback());
		assertFalse("Expecting the fourth record to not have odometer tampering", vehicleRecord4.getHasOdometerRollback());
		assertFalse("Expecting the last record to not hae odometer tampering", vehicleRecord5.getHasOdometerRollback());
		
	}
	
	@Test
	public void testDetectOdometerRollbackResponse() throws NoMatchingDataException {
		String vin = "123";
		VehicleRecordDTO vehicleRecord1 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-01-02", 
				10, 10010, Arrays.asList("Oil changed", "Tires rotated"));
		VehicleRecordDTO vehicleRecord2 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-06-20", 
				10, 12100, Arrays.asList("Tires replaced"));
		
		List<VehicleRecordDTO> vehicleRecords = Arrays.asList(vehicleRecord1, vehicleRecord2);
		
		RollbackRecordDTO responseDTO1 = buildRollbackResponseDTO("VSSZZZ6JZ9R056308", "2017-01-02", 
				10, 10010, Arrays.asList("Oil changed", "Tires rotated"), null);
		RollbackRecordDTO responseDTO2 = buildRollbackResponseDTO("VSSZZZ6JZ9R056308", "2017-06-20", 
				10, 12100, Arrays.asList("Tires replaced"), null);
		
		List<RollbackRecordDTO> rollbackResponse = Arrays.asList(responseDTO1, responseDTO2);
		
		ResponseRecordsDTO responseRecords = mock(ResponseRecordsDTO.class);
		when(responseRecords.getRecords()).thenReturn(rollbackResponse);
		
		when(vehicleRecordFetcherService.getVehicleRecords(vin)).thenReturn(vehicleRecords);
		
		when(rollbackResponseGeneratorService.buildOdometerRollbackResponse(vehicleRecords))
			.thenReturn(responseRecords);
		
		ResponseRecordsDTO response = odometerRollbackDetectorService.detectOdometerRollback(vin);
		
		assertEquals("Expecting two records", response.getRecords().size(), 2);
		
	}
	
	@Test
	public void testDetectOdometerRollbackResponseSameDate() throws NoMatchingDataException {
		String vin = "123";
		VehicleRecordDTO vehicleRecord1 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-01-02", 
				10, 12200, Arrays.asList("Oil changed", "Tires rotated"));
		VehicleRecordDTO vehicleRecord2 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-01-02", 
				10, 12100, Arrays.asList("Tires replaced"));
		
		List<VehicleRecordDTO> vehicleRecords = Arrays.asList(vehicleRecord1, vehicleRecord2);
		
		when(vehicleRecordFetcherService.getVehicleRecords(vin)).thenReturn(vehicleRecords);
		
		odometerRollbackDetectorService.markOdometerRollback(vehicleRecords);
		
		assertFalse("Expecting the first record to not have odometer tampering", vehicleRecord1.getHasOdometerRollback());
		assertFalse("Expecting the second record to not have odometer tampering", vehicleRecord2.getHasOdometerRollback());
		
	}
	
	@Test
	public void testDetectMileageInconsistency() throws NoMatchingDataException {
		String vin = "123";
		VehicleRecordDTO vehicleRecord1 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-01-02", 
				10, 12200, Arrays.asList("Oil changed", "Tires rotated"));
		VehicleRecordDTO vehicleRecord2 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-02-02", 
				10, 12100, Arrays.asList("Tires replaced"));
		VehicleRecordDTO vehicleRecord3 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-03-02", 
				10, 12300, Arrays.asList("Tires replaced"));
		
		List<VehicleRecordDTO> vehicleRecords = Arrays.asList(vehicleRecord1, vehicleRecord2, vehicleRecord3);
		
		when(vehicleRecordFetcherService.getVehicleRecords(vin)).thenReturn(vehicleRecords);
		
		odometerRollbackDetectorService.markOdometerRollback(vehicleRecords);
		
		assertFalse("Expecting the first record to not have mileage inconistency", vehicleRecord1.getHasMileageInconsistency());
		assertTrue("Expecting the second record to have mileage inconistency", vehicleRecord2.getHasMileageInconsistency());
		assertFalse("Expecting the third record to not have mileage inconistency", vehicleRecord3.getHasMileageInconsistency());
	}
	
	@Test
	public void testOdometerRollbackAndMileageInconsistency() throws NoMatchingDataException {
		String vin = "123";
		VehicleRecordDTO vehicleRecord1 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-01-02", 
				10, 12200, Arrays.asList("Oil changed", "Tires rotated"));
		VehicleRecordDTO vehicleRecord2 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-02-02", 
				10, 12100, Arrays.asList("Tires replaced"));
		VehicleRecordDTO vehicleRecord3 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2017-03-02", 
				10, 12300, Arrays.asList("Tires replaced"));
		VehicleRecordDTO vehicleRecord4 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2018-04-01", 
				10, 600, Arrays.asList("Air dam replaced", "Oil service"));
		VehicleRecordDTO vehicleRecord5 = buildVehicleRecordDTO("VSSZZZ6JZ9R056308", "2018-06-01", 
				10, 650, Arrays.asList("Air dam replaced", "Oil service"));
		
		List<VehicleRecordDTO> vehicleRecords = Arrays.asList(vehicleRecord1, vehicleRecord2,
				vehicleRecord3, vehicleRecord4, vehicleRecord5);
		
		when(vehicleRecordFetcherService.getVehicleRecords(vin)).thenReturn(vehicleRecords);
		
		odometerRollbackDetectorService.markOdometerRollback(vehicleRecords);
		
		assertFalse("Expecting the first record to not have mileage inconistency", vehicleRecord1.getHasMileageInconsistency());
		assertTrue("Expecting the second record to have mileage inconistency", vehicleRecord2.getHasMileageInconsistency());
		assertFalse("Expecting the third record to not have mileage inconistency", vehicleRecord3.getHasMileageInconsistency());
		assertTrue("Expecting the fourth record to not have odometer rollback", vehicleRecord4.getHasOdometerRollback());
		
	}
	
	private VehicleRecordDTO buildVehicleRecordDTO(String vin, String date, Integer dataProviderId,
			Integer odometerReading, List<String> serviceDetails) {
		VehicleRecordDTO recordDTO = new VehicleRecordDTO();
		
		recordDTO.setVin(vin);
		recordDTO.setDataProviderId(dataProviderId);
		recordDTO.setDate(date);
		recordDTO.setOdometerReading(odometerReading);
		recordDTO.setServiceDetails(serviceDetails);
		
		return recordDTO;
	}
	
	private RollbackRecordDTO buildRollbackResponseDTO(String vin, String date, Integer dataProviderId,
			Integer odometerReading, List<String> serviceDetails, Boolean hasOdometerRollback) {
		RollbackRecordDTO responseDTO = mock(RollbackRecordDTO.class);
		
		when(responseDTO.getDataProviderId()).thenReturn(dataProviderId);
		when(responseDTO.getDate()).thenReturn(date);
		when(responseDTO.getHasOdometerRollback()).thenReturn(hasOdometerRollback);
		when(responseDTO.getOdometerReading()).thenReturn(odometerReading);
		when(responseDTO.getServiceDetails()).thenReturn(serviceDetails);
		when(responseDTO.getVin()).thenReturn(vin);
		
		return responseDTO;
	}
	
	

}
