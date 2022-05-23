package com.cwt.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cwt.entities.Customer;
import com.cwt.entities.Order;

import lombok.Data;

@Data
public class CustomerDto {

	private Integer custId;
    private String firstName;
    private String lastName;
    private String email;
    private String location;
    private List<OrderDto> ordersDto = new ArrayList<>();

    public static CustomerDto from(Customer customer){
    	CustomerDto customerDto = new CustomerDto();
    	customerDto.setCustId(customer.getCustId());
    	customerDto.setFirstName(customer.getFirstName());
    	customerDto.setLastName(customer.getLastName());
    	customerDto.setEmail(customer.getEmail());
    	customerDto.setLocation(customer.getLocation());
        
        return customerDto;
    }
}
