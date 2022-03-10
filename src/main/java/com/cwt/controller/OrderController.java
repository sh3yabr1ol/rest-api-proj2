package com.cwt.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.dto.OrderDto;
import com.cwt.entities.Order;
import com.cwt.service.OrderService;
import com.cwt.service.ValidatorService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	
	@GetMapping("/{orderId}")
	public Order getOrder(@PathVariable Integer orderId) {
		return orderService.findOrderById(orderId);
		
	}
	
	@GetMapping("/all")
	public List<Order> getAllOrders(){
		return orderService.findAllOrders();
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Order Order){
		
		orderService.createOrderRecord(Order);
		return new ResponseEntity<Order>(Order, HttpStatus.CREATED);
		
	}

}
