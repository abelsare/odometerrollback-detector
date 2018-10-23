package com.carfax.problem.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

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

	@NotNull
	@JsonProperty("records")
	private List<VehicleRecordDTO> vehicleRecords;
}
