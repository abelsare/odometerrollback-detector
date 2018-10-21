package com.carfax.problem.exception;

import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> handleNoMatchingData(NoMatchingDataException exception) {
		APIExceptionResponse errorResponse = new APIExceptionResponse(
				LocalTime.now(), "No Content", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
	}

}
