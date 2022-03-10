package com.cwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.cwt.entities.Customer;
import com.cwt.persistence.CustomerRepo;

@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class CustomerRepositoryMockitoTests {

	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepo customerRepo;
    
    private Customer customer = new Customer();
    
    @BeforeEach
    void setUp() {

    	customer.setFirstName("John");
    	customer.setLastName("Doee");
    	customer.setEmail("jd@g.com");
    	customer.setLocation("QC");
    	
    	entityManager.persist(customer);
    }
    

    @Test
	@DisplayName("Test To Find Customer By Id")
	 void testFindCustomerById() {
        
    	Optional<Customer> customer = customerRepo.findById(1);
          
        assertEquals(1, customer.get().getCustId());
	}
    
    @Test
	@DisplayName("Test To Find All Customers")
	 void testFindAllCustomer() {
        
    	List<Customer> customer = customerRepo.findAll();
          
        assertEquals(1, customer.size());
	}
    
    @Test
	@DisplayName("Test Customer Creation")
	 void testCustomerCreation() {
        
    	Customer customer1 = new Customer(null, "John4", "Doe4", "jd4@gma.com", "QC");
    	
    	customerRepo.save(customer1);
          
        assertEquals(2, customerRepo.findAll().size());
	}
    
    @Test
	@DisplayName("Test Customer Deletion")
	 void testCustomerDelete() {
    	
    	Optional<Customer> customerFromDb;
		
		customerFromDb = customerRepo.findById(customer.getCustId());
        
		customerRepo.deleteById(customerFromDb.get().getCustId());
		
		customerFromDb  = customerRepo.findById(customer.getCustId());
		
		assertTrue(customerFromDb.isEmpty());
	}
    
    @Test
	@DisplayName("Test Customer Update")
	 void testCustomerUpdate() {
        
    	Customer toUpdateCustomer = new Customer(null, "John4", "Doe4", "jd4@g.com", "QC");
    	
    	Optional<Customer> customerFromDB = customerRepo.findById(customer.getCustId());
    	
    	Customer customerFromRepo = customerFromDB.get();
		
		customerFromRepo.setFirstName(toUpdateCustomer.getFirstName());
		customerFromRepo.setLastName(toUpdateCustomer.getLastName());
		customerFromRepo.setEmail(toUpdateCustomer.getEmail());
		customerFromRepo.setLocation(toUpdateCustomer.getLocation());
		
		customerRepo.save(customerFromRepo);
          
        assertEquals("John4", customerRepo.findById(customer.getCustId()).get().getFirstName());
	}
  
}
