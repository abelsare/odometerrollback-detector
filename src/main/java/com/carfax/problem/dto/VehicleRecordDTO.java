package com.carfax.problem.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Holds the vehicle record fetched from remote Carfax API 
 *
 */
@Data
@NoArgsConstructor
public class VehicleRecordDTO {
	
	private String vin;
	
	private String date;
	
	@JsonProperty("data_provider_id")
	private Integer dataProviderId;
	
	@JsonProperty("odometer_reading")
	private Integer odometerReading;
	
	@JsonProperty("service_details")
	private List<String> serviceDetails;
	
	@JsonIgnore
	private Boolean hasOdometerRollback = Boolean.FALSE;

}
