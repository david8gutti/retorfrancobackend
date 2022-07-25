package com.david.retobackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.david.retobackend.model.Order;
import com.david.retobackend.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public List<Order> findAll() {
		List<Order> listOrder = orderRepository.findAll();
		return listOrder;
	}

	public void save(Order order) {
		orderRepository.save(order);
	}

	public void deleteById(long orderId) {
		orderRepository.deleteById(orderId);
	}

}
