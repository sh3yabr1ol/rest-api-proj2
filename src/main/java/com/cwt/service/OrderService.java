package com.cwt.service;

import java.util.List;

import com.cwt.entities.Order;

public interface OrderService {

	public Order findOrderById(Integer custId);
	
	public List<Order> findAllOrders();
	
	public void createOrderRecord(Order order);
	
	public void deleteOrderRecord(Integer custId);
	
	public Order updateOrderRecord(Integer custId, Order newOrder);
}
