package com.carfax.problem.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response object with rollback event information
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonRootName("vehicle-record")
public class RollbackRecordDTO {

	private String vin;
	
	private String date;
	
	private Integer dataProviderId;
	
	private Integer odometerReading;
	
	private List<String> serviceDetails;
	
	@JsonInclude(Include.NON_NULL)
	private Boolean hasOdometerRollback;
}
