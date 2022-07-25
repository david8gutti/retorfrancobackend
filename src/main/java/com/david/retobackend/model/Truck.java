package com.david.retobackend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "truck")
public class Truck implements Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "brand")
	private String brand;
	@Column(name = "model")
	private String model;

	@OneToMany(mappedBy = "truck")
	List<Order> orderList;

	public Truck() {
		super();
		orderList = new ArrayList<Order>();
	}

	public Truck(String brand, String model) {
		super();
		this.brand = brand;
		this.model = model;
		orderList = new ArrayList();

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void addOrder(Order order) {
		orderList.add(order);
	}

	public void removeOrder(Order order) {
		orderList.remove(order);
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

}
