package com.carfax.problem.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wrapper for vehicle records
 *
 */
@NoArgsConstructor
@Data
public class VehicleRecordsDTO {

	@JsonProperty("records")
	private List<VehicleRecordDTO> vehicleRecords;
}
