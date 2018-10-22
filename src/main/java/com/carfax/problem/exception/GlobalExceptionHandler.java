package com.carfax.problem.exception;

import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

/**
 * Catches exceptions and returns the appropriate response to the client 
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = {RuntimeException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> handleUnknownExceptions(RuntimeException exception) {
		APIExceptionResponse errorResponse = new APIExceptionResponse(
				LocalTime.now(), "Internal Server Error", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {NoMatchingDataException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleNoMatchingData(NoMatchingDataException exception) {
		APIExceptionResponse errorResponse = new APIExceptionResponse(
				LocalTime.now(), "Not Found", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {RestClientException.class})
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public ResponseEntity<?> handleDataFetchError(RestClientException exception) {
		APIExceptionResponse errorResponse = new APIExceptionResponse(
				LocalTime.now(), "Error", "Error fetching vehicle records from Carfax API");
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}

}
