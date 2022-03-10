package com.cwt.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.entities.Customer;
import com.cwt.entities.Order;
import com.cwt.persistence.OrderRepo;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;
	
	@Override
	public Order findOrderById(Integer orderId) {
		Order order = null;
		
		Optional<Order> optionalCustomer = orderRepo.findById(orderId);
		
		if(optionalCustomer.isPresent()) {
			order = optionalCustomer.get();
		}else {
			throw new RuntimeException("No Order Found With Order Id " + orderId);
		}
		
		return order;
	}

	@Override
	public List<Order> findAllOrders() {
		return StreamSupport
                .stream(orderRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
		//return orderRepo.findAll();
	}

	@Override
	public void createOrderRecord(Order order) {
		orderRepo.save(order);	
	}

	@Override
	public void deleteOrderRecord(Integer orderId) {
		Order order = findOrderById(orderId);
		orderRepo.deleteById(order.getOrderId());

	}

	@Override
	public Order updateOrderRecord(Integer orderId, Order newOrder) {
		Order order = findOrderById(orderId);
		
		order.setProduct(newOrder.getProduct());
		order.setQuantity(newOrder.getQuantity());
		
		orderRepo.save(order);
		
		return order;
	}

}
