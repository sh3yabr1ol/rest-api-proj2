package com.cwt.exceptions;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.transaction.TransactionSystemException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> exceptionHandler(RuntimeException ex){
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TransactionSystemException.class)
    protected ResponseEntity<?> handleConflict(TransactionSystemException ex) {

		Map<Object, String> map = new HashMap<>();
		
        Throwable cause = ex.getRootCause();

        if (cause.getClass().equals(ConstraintViolationException.class)) {
           
        	((ConstraintViolationException) cause).getConstraintViolations()
        	.forEach(e -> map.put(e.getPropertyPath(), e.getMessage()));

      }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    } 
	
}
