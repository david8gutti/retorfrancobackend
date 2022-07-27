package com.david.retobackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.retobackend.service.OrderService;
import com.david.retobackend.exception.OrderNotFoundException;
import com.david.retobackend.model.Order;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public ResponseEntity<List<Order>> findAll() {
		List<Order> listOrders = orderService.findAll();
		if (listOrders.isEmpty()) {
			throw new OrderNotFoundException("No orders were found");
		}
		return new ResponseEntity<List<Order>>(listOrders, HttpStatus.OK);
	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable int orderId) {
		Optional<Order> optionalOrder = orderService.findById(orderId);
		if (optionalOrder.isPresent()) {
			return new ResponseEntity<>(optionalOrder.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/orders")
	public ResponseEntity<Order> addOrder(@RequestBody Order order) {
		order.setId(0);
		orderService.save(order);
		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
	}

	@PutMapping("/orders")
	public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
		orderService.save(order);

		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("orders/{orderId}")
	public ResponseEntity<Object> deleteOrder(@PathVariable long orderId) {

		orderService.deleteById(orderId);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
