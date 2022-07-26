package com.david.retobackend.service;

import java.util.List;
import java.util.Optional;

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

	public void deleteById(long truckId) {
		truckRepository.deleteById(truckId);
	}

	public Optional<Truck> findById(long id) {
		return truckRepository.findById(id);
	}

}
