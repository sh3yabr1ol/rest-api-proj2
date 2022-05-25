package com.cwt.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.cwt.dto.CustomerDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer custId;
	
	@Column(name = "first_name")
	@Size(min=4, max=32, message="First Name Size should be between 4 and 32 characters")
	@NotBlank(message = "First Name Should Not Be Blank")
	private String firstName;
	
	@Size(min=4, max=32, message="Last Name Size should be between 4 and 32 characters")
	@NotBlank(message = "Last Name Should Not Be Blank")
	@Column(name = "last_name")
	private String lastName;
	
	@NotBlank(message = "Email Should Not Be Blank")
	@Pattern(regexp = "^(.+)@(.+)$", message="Please input valid email format")
	@Column(name="email", unique=true, nullable = false)
	private String email;
	
	@NotBlank(message = "Location Should Not Be Blank")
	private String location;
	
	@OneToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "customer_cust_id")
	@JsonManagedReference
	private List<Order> orderList = new ArrayList();
	
	public void addItem(Order order){
		orderList.add(order);
    }

    public static Customer from(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getFirstName());
        customer.setEmail(customerDto.getEmail());
        customer.setLocation(customerDto.getLocation());
    
        return customer;
    }

	public Customer(Integer i, String string, String string2, String string3, String string4) {
		this.custId = i;
		this.firstName = string;
		this.lastName = string2;
		this.email = string3;
		this.location = string4;
	}
}
