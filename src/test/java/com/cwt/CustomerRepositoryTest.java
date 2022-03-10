package com.cwt;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cwt.entities.Customer;
import com.cwt.persistence.CustomerRepo;

@SpringBootTest
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepo customerRepo;
	
	private List<Customer> listOfCustomer;
	
	@BeforeEach
    public void setUp() {
  
		listOfCustomer = new ArrayList<>();
        
		Customer customer = new Customer(1, "John", "Doee", "jd@g.com", "QC");
		Customer customer2 = new Customer(2, "John2", "Doe2", "jd1@g.com", "QC");
        Customer customer3 = new Customer(3, "John3", "Doe3", "jd2@g.com", "QC");
          
        listOfCustomer.add(customer);
        listOfCustomer.add(customer2);
        listOfCustomer.add(customer3);
        
        customerRepo.saveAll(listOfCustomer);
    }
	
	@Test
	@DisplayName("Test To Find All Customers")
	 void testFindAllCustomers() {
        
        List<Customer> listFromDb = customerRepo.findAll();
          
        assertEquals(3, listFromDb.size());
	}
	
	@Test
	@DisplayName("Test To Find Customer By Id")
	 void testFindCustomerById() {
        
		Optional<Customer> optionalCustomer = customerRepo.findById(1);
          
        assertEquals(1, optionalCustomer.get().getCustId());
        assertEquals("John", optionalCustomer.get().getFirstName());
	}
	

	@Test
	@DisplayName("Test To Delete Customer")
	 void testDeleteCustomer() {
        
		Optional<Customer> customerFromDb;
		
		customerFromDb = customerRepo.findById(1);
        
		customerRepo.deleteById(customerFromDb.get().getCustId());
		
		customerFromDb  = customerRepo.findById(1);
		
		assertTrue(customerFromDb.isEmpty());
	}
	
	@Test
	@DisplayName("Test To Update Customer")
	 void testUpdateCustomer() {
        
		Customer toUpdateCustomer = new Customer(null, "John4", "Doe4", "jd4@g.com", "QC");
		
		Optional<Customer> optionalCustomer = customerRepo.findById(1);
        
		Customer customerFromRepo = optionalCustomer.get();
		
		customerFromRepo.setFirstName(toUpdateCustomer.getFirstName());
		customerFromRepo.setLastName(toUpdateCustomer.getLastName());
		customerFromRepo.setEmail(toUpdateCustomer.getEmail());
		customerFromRepo.setLocation(toUpdateCustomer.getLocation());
		
		customerRepo.save(customerFromRepo);
		
        assertEquals(1, optionalCustomer.get().getCustId());
        assertEquals("John4", optionalCustomer.get().getFirstName());
	}
}
