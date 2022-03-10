package com.cwt.dto;

import java.util.Objects;

import com.cwt.entities.Order;

import lombok.Data;

@Data
public class OrderDto {

	private Integer orderId;
	private String product;
	private int quantity;
	private CustomerDetailDto customerDetailDto;
	
	public static OrderDto from(Order order){
		OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setProduct(order.getProduct());
        orderDto.setQuantity(order.getQuantity());
//        if(Objects.nonNull(order.getCustomer())){
//        	orderDto.setCustomerDetailDto(CustomerDetailDto.from(order.getCustomer()));
//        }
        return orderDto;
    }
}
