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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.retobackend.service.OrderService;
import com.david.retobackend.service.TruckService;
import com.david.retobackend.exception.TruckNotFoundException;
import com.david.retobackend.model.Order;
import com.david.retobackend.model.Truck;

/**
 * 
 * Controlador de la clase de camiones (Truck), el cual permitirá visualizar,
 * crear, actualizar y eliminar camiones
 * 
 * @author: David Gutierrez Mariblanca
 * 
 * 
 */
@RestController
@RequestMapping("/api")
public class TruckController {

	@Autowired
	private TruckService truckService;

	@Autowired
	private OrderService orderService;

	/**
	 * 
	 * Metodo que devuelve todos los camiones
	 * 
	 * @return La lista de camiones existentes
	 * 
	 */
	@GetMapping("/trucks")
	public ResponseEntity<List<Truck>> findAll() {
		List<Truck> listTruck = truckService.findAll();
		if (listTruck.isEmpty()) {
			throw new TruckNotFoundException("No trucks were found");
		}
		return new ResponseEntity<List<Truck>>(listTruck, HttpStatus.OK);
	}

	/**
	 * 
	 * Metodo que devuelve un camion en concreto
	 * 
	 * @param Id del camion
	 * @return El camion especifico
	 * 
	 */
	@GetMapping("/trucks/{truckId}")
	public ResponseEntity<Truck> getTruck(@PathVariable int truckId) {
		Optional<Truck> optionalTruck = truckService.findById(truckId);
		if (optionalTruck.isPresent()) {
			return new ResponseEntity<>(optionalTruck.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 
	 * Metodo que crea un camion
	 * 
	 * @param Camion a crear
	 * @return El camion ya creado
	 * 
	 */
	@PostMapping("/trucks")
	public ResponseEntity<Truck> addTruck(@RequestBody Truck truck) {
		truck.setId(0);
		truckService.save(truck);
		return new ResponseEntity<Truck>(truck, HttpStatus.CREATED);

	}

	/**
	 * 
	 * Metodo que asigna un pedido a un camion
	 * 
	 * @param Cabeceras del id de un pedido y del id de un camion
	 * @return El pedido asignado al camion
	 * 
	 */
	@PostMapping("/orderToTruck")
	public ResponseEntity<Truck> addOrderToTruck(@RequestHeader("order_id") int order_id,
			@RequestHeader("truck_id") int truck_id) {
		Optional<Truck> optionalTruck = truckService.findById(truck_id);
		Optional<Order> optionalOrder = orderService.findById(order_id);
		if (optionalTruck.isPresent() && optionalOrder.isPresent()) {

			Truck order_truck = optionalTruck.get();
			order_truck.getOrders().add(optionalOrder.get());
			truckService.save(order_truck);

			return new ResponseEntity<>(order_truck, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * 
	 * Metodo que actualiza un camion
	 * 
	 * @param Camion a actualizar
	 * @return El camion ya actualizado
	 * 
	 */
	@PutMapping("/trucks")
	public ResponseEntity<Truck> updateTruck(@RequestBody Truck truck) {
		truckService.save(truck);
		return new ResponseEntity<Truck>(truck, HttpStatus.ACCEPTED);
	}

	/**
	 * 
	 * Metodo que elimina un camion
	 * 
	 * @param Id del camion
	 * @return Exito de la eliminacion
	 * 
	 */
	@DeleteMapping("trucks/{truckId}")
	public ResponseEntity<Object> deleteTruck(@PathVariable long truckId) {

		truckService.deleteById(truckId);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
