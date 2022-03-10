package com.cwt.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cwt.entities.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{

}
