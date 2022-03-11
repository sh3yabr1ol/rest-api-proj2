package com.cwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

import com.cwt.entities.Customer;
import com.cwt.persistence.CustomerRepo;
import com.cwt.service.CustomerService;
import com.cwt.service.CustomerServiceImpl;

@SpringBootTest
class CustomerServiceTests {

	@Mock
	private CustomerRepo customerRepo;
	
	@InjectMocks
	private CustomerService customerService = new CustomerServiceImpl();
	
	private Customer customer;
	
	@BeforeEach
	void setUp() {
		customer = new Customer(1, "John", "Doe", "jd@g.com", "QC");
	}
	
	@Test
	@DisplayName("Test To Find All Customers")
	 void testFindAllCustomers() {
		
		List<Customer> list = new ArrayList<>();
        
		Customer customer2 = new Customer(2, "John2", "Doe2", "jd1@g.com", "QC");
        Customer customer3 = new Customer(3, "John3", "Doe3", "jd2@g.com", "QC");
          
        list.add(customer);
        list.add(customer2);
        list.add(customer3);
          
        Mockito.when(customerRepo.findAll()).thenReturn(list);
          
        List<Customer> customerList = customerService.findAllCustomers();
          
        assertEquals(3, customerList.size());
	}
	
	@Test
	@DisplayName("Test To Find Customer By Id")
	 void testFindCustomerById() {
		
        Mockito.when(customerRepo.findById(customer.getCustId())).thenReturn(Optional.of(customer));
        
        Customer foundCustomer = customerService.findCustomerById(customer.getCustId());
        
        assertEquals(1, foundCustomer.getCustId());
        assertEquals("John", foundCustomer.getFirstName());
        assertEquals("Doe", foundCustomer.getLastName());
        assertEquals("jd@g.com", foundCustomer.getEmail());
	}
	
	@Test
	@DisplayName("Test To Find Customer By Id When Customer Not Found")
	 void testFindCustomerByIdAndCustomerNotFound() {
		
        Customer nonCustomer = new Customer(2, "John2", "Doe2", "jd2@g.com", "QC");
        
        Mockito.when(customerRepo.findById(customer.getCustId())).thenReturn(Optional.of(customer));
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> customerService.findCustomerById(nonCustomer.getCustId()));
		
        assertEquals("No Customer Found With Customer Id " + nonCustomer.getCustId(), exception.getMessage());
	}
	
	@Test
	@DisplayName("Test for Customer Creation")
	 void testCustomerCreation() {
		
		customerService.createCustomerRecord(customer);
		
		Mockito.verify(customerRepo, times(1)).save(customer);
        
	}
	
	@Test
	@DisplayName("Test To Update Customer")
	 void testCustomerUpdate() {
		
		Customer toUpdateCustomer = new Customer(2, "John2", "Doe2", "jd2@g.com", "QC");
		
		Mockito.when(customerRepo.findById(customer.getCustId())).thenReturn(Optional.of(customer));
		
		Customer updatedCustomer = customerService.updateCustomerRecord(1, toUpdateCustomer);
		
		assertEquals(1, updatedCustomer.getCustId());
		assertEquals("John2", updatedCustomer.getFirstName());  
	}
	
	@Test
	@DisplayName("Test To Delete Customer")
	 void testCustomerDeletion() {
		
		Mockito.when(customerRepo.findById(customer.getCustId())).thenReturn(Optional.of(customer));
		
		customerService.deleteCustomerRecord(customer.getCustId());
		
		Mockito.verify(customerRepo, times(1)).deleteById(customer.getCustId());
	}
}
