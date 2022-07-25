package com.david.retobackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.retobackend.service.OrderService;
import com.david.retobackend.model.Order;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public List<Order> findAll() {
		return orderService.findAll();
	}

	@PostMapping("/orders")
	public Order addOrder(@RequestBody Order order) {
		order.setId(0);
		orderService.save(order);
		return order;

	}

	@PutMapping("/orders")
	public Order updateOrder(@RequestBody Order order) {
		orderService.save(order);
		return order;
	}

	@DeleteMapping("orders/{orderId}")
	public String deleteOrder(@PathVariable long orderId) {

		orderService.deleteById(orderId);
		return "Order Eliminated";
	}
}
