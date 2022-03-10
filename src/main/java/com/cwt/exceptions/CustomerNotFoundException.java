package com.cwt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cwt.entities.Customer;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {

	public CustomerNotFoundException(Integer custId) {
        super("Could not find customer with id " + custId);
    }
}
