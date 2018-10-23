package com.carfax.problem.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Final API Response 
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseRecordsDTO {

	private List<RollbackRecordDTO> records;
}
