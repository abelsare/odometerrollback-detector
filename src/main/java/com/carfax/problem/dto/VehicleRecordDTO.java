package com.carfax.problem.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

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
	
	@NotNull
	private String vin;
	
	@NotNull
	private String date;
	
	@NotNull
	@JsonProperty("data_provider_id")
	private Integer dataProviderId;
	
	@NotNull
	@JsonProperty("odometer_reading")
	private Integer odometerReading;
	
	@NotNull
	@JsonProperty("service_details")
	private List<String> serviceDetails;
	
	@JsonIgnore
	private Boolean hasOdometerRollback = Boolean.FALSE;

}
