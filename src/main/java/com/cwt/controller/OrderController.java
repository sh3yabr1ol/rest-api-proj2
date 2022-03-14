package com.cwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.entities.Order;
import com.cwt.service.OrderService;

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
	public ResponseEntity<Order> create(@RequestBody Order Order){
		
		orderService.createOrderRecord(Order);
		return new ResponseEntity<>(Order, HttpStatus.CREATED);
		
	}

}
