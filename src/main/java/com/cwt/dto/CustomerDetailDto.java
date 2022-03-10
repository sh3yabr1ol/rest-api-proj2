package com.cwt.dto;

import com.cwt.entities.Customer;

import lombok.Data;

@Data
public class CustomerDetailDto {

	private Integer custId;
	private String firstName;
	private String lastName;
	
	public static CustomerDetailDto from(Customer customer){
		CustomerDetailDto customerDetail = new CustomerDetailDto();
		customerDetail.setCustId(customer.getCustId());
		customerDetail.setFirstName(customer.getFirstName());
		customerDetail.setLastName(customer.getLastName());
        return customerDetail;
    }
}
