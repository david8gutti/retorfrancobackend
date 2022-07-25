package com.david.retobackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.david.retobackend.model.Truck;
import com.david.retobackend.repository.TruckRepository;

@Service
public class TruckService {
	@Autowired
	private TruckRepository truckRepository;

	public List<Truck> findAll() {
		List<Truck> listUsers = truckRepository.findAll();
		return listUsers;
	}

	public void save(Truck truck) {
		truckRepository.save(truck);
	}

}