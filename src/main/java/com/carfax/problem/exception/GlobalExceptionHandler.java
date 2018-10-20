package com.carfax.problem.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(exception.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
