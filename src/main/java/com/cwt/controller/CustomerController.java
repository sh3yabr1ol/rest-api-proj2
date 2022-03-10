package com.cwt.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.dto.CustomerDto;
import com.cwt.entities.Customer;
import com.cwt.service.CustomerService;
import com.cwt.service.ValidatorService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ValidatorService validatorService;
	
	@GetMapping("/{custId}")
	public Customer getCustomer(@PathVariable Integer custId) {
	
		return customerService.findCustomerById(custId);
		
	}
	
	@GetMapping("/all")
	public List<Customer> getAllCustomers(){
		return customerService.findAllCustomers();
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody Customer customer, BindingResult bindingRes){
		Map<String, String> validationMap = validatorService.validate(bindingRes);
		
		if(validationMap.isEmpty()) {
			customerService.createCustomerRecord(customer);
			return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(validationMap, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{custId}")
	public ResponseEntity<?> update(@PathVariable Integer custId, @Valid @RequestBody Customer customer, BindingResult bindingRes){
		
		Map<String, String> validationMap = validatorService.validate(bindingRes);
		
		if(validationMap.isEmpty()) {
			Customer updatedCustomer = customerService.updateCustomerRecord(custId, customer);
			return new ResponseEntity<Customer>(updatedCustomer, HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<>(validationMap, HttpStatus.BAD_REQUEST);	
		}
		
	}
	
	@DeleteMapping("/delete/{custId}")
	public ResponseEntity<Integer> delete(@PathVariable Integer custId){
		
		customerService.deleteCustomerRecord(custId);
		
		return new ResponseEntity<>(custId, HttpStatus.ACCEPTED);
	}
	
	@PatchMapping("/updatePartial/{custId}")
	public ResponseEntity<?> updatePartially(@PathVariable Integer custId, 
			@RequestBody Map<String, String> fields){
			
		Customer customer = customerService.updatePartially(custId, fields);
		return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value = "{custId}/order/{orderId}/add")
    public ResponseEntity<CustomerDto> addItemToCart(@PathVariable final Integer custId,
                                                 @PathVariable final Integer orderId){
		Customer customer = customerService.addOrderToCustomer(custId, orderId);
        return new ResponseEntity<>(CustomerDto.from(customer), HttpStatus.OK);
    }
	
}
