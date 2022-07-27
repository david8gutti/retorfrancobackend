package com.david.retobackend.service;

import java.util.List;
import java.util.Optional;

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

	public Order save(Order order) {
		return orderRepository.save(order);
	}

	public Optional<Order> findById(long id) {
		return orderRepository.findById(id);
	}

	public void deleteById(long orderId) {
		orderRepository.deleteById(orderId);
	}

}
