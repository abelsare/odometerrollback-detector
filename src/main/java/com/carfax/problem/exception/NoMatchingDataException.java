package com.carfax.problem.exception;

import lombok.AllArgsConstructor;

/**
 * Thrown when carfax api doesn't return any matching data
 *
 */
@AllArgsConstructor
public class NoMatchingDataException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	
	@Override
	public String getMessage() {
		return this.errorMessage;
	}

}
