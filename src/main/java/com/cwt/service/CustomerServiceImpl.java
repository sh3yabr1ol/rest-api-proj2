package com.cwt.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.cwt.entities.Customer;
import com.cwt.entities.Order;
import com.cwt.persistence.CustomerRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Validated
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private OrderService orderService;
	
	@Override
	public Customer findCustomerById(Integer custId) {
		log.info("inside findCustomerById...");
		
		Customer customer = null;
		
		Optional<Customer> optionalCustomer = customerRepo.findById(custId);
		
		if(optionalCustomer.isPresent()) {
			customer = optionalCustomer.get();
		}else {
			throw new RuntimeException("No Customer Found With Customer Id " + custId);
		}
		
		return customer;
	}

	@Override
	public List<Customer> findAllCustomers() {
		log.info("inside findAllCustomers...");
		return StreamSupport
                .stream(customerRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
		//return customerRepo.findAll();
	}

	@Override
	public void createCustomerRecord(Customer customer) {
		customerRepo.save(customer);	
	}

	@Override
	public Customer updateCustomerRecord(Integer custId, Customer newCustomer) {
		Customer customer = findCustomerById(custId);
		
		customer.setFirstName(newCustomer.getFirstName());
		customer.setLastName(newCustomer.getLastName());
		customer.setEmail(newCustomer.getEmail());
		customer.setLocation(newCustomer.getLocation());
		
		customerRepo.save(customer);
		
		return customer;
	}
	
	@Override
	public void deleteCustomerRecord(Integer custId) {
		Customer customer = findCustomerById(custId);
		
		customerRepo.deleteById(customer.getCustId());
	}

	@Override
	public Customer updatePartially(Integer custId, Map<String, String> fields) {
		Customer customer = findCustomerById(custId);
		
		fields.forEach((key, value) -> {
			Field field = ReflectionUtils.findRequiredField(Customer.class, (String) key);
			ReflectionUtils.setField(field, customer, value);
		});
		customerRepo.save(customer);
		return customer;
	}

	@Override
	@Transactional
	public Customer addOrderToCustomer(Integer custId, Integer orderId) {
		Customer customer = findCustomerById(custId);
        Order order = orderService.findOrderById(orderId);
        if(Objects.nonNull(order.getCustomer())){
            throw new RuntimeException("Order has been assigned to another customer");
        }
        customer.addItem(order);
        order.setCustomer(customer);
        return customer;
	}
	
	
}
