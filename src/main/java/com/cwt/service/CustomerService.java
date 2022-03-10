package com.cwt.service;

import java.util.List;
import java.util.Map;

import com.cwt.entities.Customer;

public interface CustomerService {

	public Customer findCustomerById(Integer custId);
	
	public List<Customer> findAllCustomers();
	
	public void createCustomerRecord(Customer customer);
	
	public void deleteCustomerRecord(Integer custId);
	
	public Customer updateCustomerRecord(Integer custId, Customer newCustomer);
	
	public Customer updatePartially(Integer custId, Map<String, String> fields);
	
	//public Customer addOrderToCustomer(Integer custId, Integer orderId);
}
