package com.cwt;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cwt.entities.Customer;
import com.cwt.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTests {

	@Autowired
    MockMvc mockMvc;
	
    @MockBean
    CustomerService customerService;
    
    private Customer customer;
    
    @BeforeEach
    public void setUp() {
    	customer = new Customer(1, "John", "Doee", "jd@g.com", "QC");
    }
    
    @Nested
	@DisplayName("Nested Test For All Successful Scenarios")
	class NestedTestForAllSuccessfulScenarios{
    	
    	@Test
        @DisplayName("Test To Get All Customers")
        void getAllCustomer_success() throws Exception {
        	
        	List<Customer> list = new ArrayList<>();
            Customer customer2 = new Customer(2, "John2", "Doe2", "jd1@g.com", "QC");
            Customer customer3 = new Customer(3, "John3", "Doe3", "jd2@g.com", "QC");
              
            list.add(customer);
            list.add(customer2);
            list.add(customer3);
              
            Mockito.when(customerService.findAllCustomers()).thenReturn(list);
            
            mockMvc.perform(get("/customers/all")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)));
        }
        
        @Test
        @DisplayName("Test To Get Customer By Id Success")
        void getCustomerById_success() throws Exception {
              
            Mockito.when(customerService.findCustomerById(customer.getCustId())).thenReturn(customer);
            
            mockMvc.perform(get("/customers/{custId}", 1)
                    .contentType(MediaType.APPLICATION_JSON))
            		.andExpect(status().isOk())
            		.andExpect(jsonPath("$", notNullValue()))
            		.andExpect(jsonPath("$.firstName", is("John")));
        }
        
        @Test
        @DisplayName("Test for Customer Creation")
        void createCustomer_success() throws Exception {
        	
        	mockMvc.perform(post("/customers/create")
        		      .content(new ObjectMapper().writeValueAsString(customer))
        		      .contentType(MediaType.APPLICATION_JSON)
        		      .accept(MediaType.APPLICATION_JSON))
        		      .andExpect(status().isCreated())
        		      .andExpect(jsonPath("$.firstName", is("John")))
        		      .andExpect(jsonPath("$.lastName", is("Doee")))
        		      .andExpect(jsonPath("$.email", is("jd@g.com")));
        		      
        }
        
        @Test
        @DisplayName("Test for Customer Update")
        void updateCustomer_success() throws Exception {
        	
        	Customer toUpdateCustomer = new Customer(1, "John2", "Doe2", "jd2@g.com", "QC");
    	
            Mockito.when(customerService.updateCustomerRecord(customer.getCustId(), toUpdateCustomer)).thenReturn(toUpdateCustomer);
            
        	mockMvc.perform(put("/customers/update/{custId}", customer.getCustId())
        		      .content(new ObjectMapper().writeValueAsString(toUpdateCustomer))
        		      .contentType(MediaType.APPLICATION_JSON)
        		      .accept(MediaType.APPLICATION_JSON))
        		      .andExpect(status().isAccepted())
        		      .andExpect(jsonPath("$.firstName", is("John2")))
        		      .andExpect(jsonPath("$.lastName", is("Doe2")))
        			  .andExpect(jsonPath("$.email", is("jd2@g.com")));        
        }
        
        @Test
        @DisplayName("Test for Customer Deletion")
        void deleteCustomer_success() throws Exception {
        	
        	Mockito.doNothing().when(customerService).deleteCustomerRecord(customer.getCustId());
        	
        	mockMvc.perform(delete("/customers/delete/{custId}", customer.getCustId())
        		      .contentType(MediaType.APPLICATION_JSON)
        		      .accept(MediaType.APPLICATION_JSON))
        		      .andExpect(status().isAccepted());       
        }
    }
    
    
    @Nested
	@DisplayName("Nested Test For All Validation Scenarios")
	class NestedTestForAllValidationScenarios{
    	
    	@Test
        @DisplayName("Test To Get Customer By Id Fail")
        void getCustomerById_fail() throws Exception {
        	
        	Mockito.when(customerService.findCustomerById(customer.getCustId())).thenThrow(new RuntimeException());
            
            mockMvc.perform(get("/customers/{custId}", customer.getCustId())
                    .contentType(MediaType.APPLICATION_JSON))
            		.andExpect(status().isBadRequest());
        }
        
        @Test
        @DisplayName("Test for First Name Size Validation")
        void createCustomer_failFirstName() throws Exception {

        	mockMvc.perform(post("/customers/create")
        		      .content(new ObjectMapper().writeValueAsString((new Customer(1, "J", "Doee", "jd@g.com", "QC"))))
        		      .contentType(MediaType.APPLICATION_JSON)
        		      .accept(MediaType.APPLICATION_JSON))
        		      .andExpect(status().isBadRequest())
        		      .andExpect(jsonPath("$.firstName", is("First Name Size should be between 4 and 32 characters")));
        }
        
        @Test
        @DisplayName("Test for Last Name Size Validation")
        void createCustomer_failLastName() throws Exception {

        	mockMvc.perform(post("/customers/create")
        		      .content(new ObjectMapper().writeValueAsString((new Customer(1, "John", "Doe", "jd@g.com", "QC"))))
        		      .contentType(MediaType.APPLICATION_JSON)
        		      .accept(MediaType.APPLICATION_JSON))
        		      .andExpect(status().isBadRequest())
        		      .andExpect(jsonPath("$.lastName", is("Last Name Size should be between 4 and 32 characters")));
        		      
        }
        
        @Test
        @DisplayName("Test for Email Validation")
        void createCustomer_failEmail() throws Exception {

        	mockMvc.perform(post("/customers/create")
        		      .content(new ObjectMapper().writeValueAsString((new Customer(1, "John", "Doee", "jdg#com", "QC"))))
        		      .contentType(MediaType.APPLICATION_JSON)
        		      .accept(MediaType.APPLICATION_JSON))
        		      .andExpect(status().isBadRequest())
        		      .andExpect(jsonPath("$.email", is("Please input valid email format")));	      
        }
    }
    
    
}
