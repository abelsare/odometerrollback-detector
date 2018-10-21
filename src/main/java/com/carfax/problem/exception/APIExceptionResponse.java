package com.carfax.problem.exception;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Custom error response object for API errors
 *
 */
@Data
@AllArgsConstructor
public class APIExceptionResponse {

	private LocalTime timestamp;
	private String message;
	private String details;
}
