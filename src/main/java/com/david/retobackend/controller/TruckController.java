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

import com.david.retobackend.service.TruckService;
import com.david.retobackend.model.Truck;

@RestController
@RequestMapping("/api")
public class TruckController {

	@Autowired
	private TruckService truckService;

	@GetMapping("/trucks")
	public List<Truck> findAll() {
		return truckService.findAll();
	}

	@PostMapping("/trucks")
	public Truck addTruck(@RequestBody Truck truck) {
		truck.setId(0);
		truckService.save(truck);
		return truck;

	}

	@PutMapping("/trucks")
	public Truck updateTruck(@RequestBody Truck truck) {
		truckService.save(truck);
		return truck;
	}

	@DeleteMapping("trucks/{truckId}")
	public String deleteTruck(@PathVariable long truckId) {

		truckService.deleteById(truckId);
		return "Truck Eliminated";
	}
}
