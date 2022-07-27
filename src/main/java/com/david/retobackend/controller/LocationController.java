package com.david.retobackend.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.retobackend.service.HistoryService;
import com.david.retobackend.service.LocationService;
import com.david.retobackend.service.TruckService;
import com.david.retobackend.model.History;
import com.david.retobackend.model.Location;
import com.david.retobackend.model.Truck;

/**
 * 
 * Controlador de la clase de la ubicacion (Location), el cual permitirá visualizar y 
 * crear ubicaciones
 * 
 * @author: David Gutierrez Mariblanca
 * 
 * 
 */
@RestController
@RequestMapping("/api")
public class LocationController {

	@Autowired
	private TruckService truckService;

	@Autowired
	private LocationService locationService;
	
	@Autowired
	private HistoryService historyService;

	/**
	 * 
	 * Metodo que devuelve una ubicacion en concreto
	 * 
	 * @param Id del camion
	 * @return El camion especifico
	 * 
	 */
	@GetMapping("/locations/{locationId}")
	public ResponseEntity<Location> getLocation(@PathVariable int locationId) {
		Optional<Location> optionalLocation = locationService.findById(locationId);
		if (optionalLocation.isPresent()) {
			return new ResponseEntity<>(optionalLocation.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 
	 * Metodo que asigna una ubicacion a un camion
	 * 
	 * @param Cabeceras del id de un camion y una ubicacion
	 * @return El camion con la ubicacion cambiada
	 * 
	 */
	@PostMapping("/locationToTruck")
	public ResponseEntity<Truck> addLocationToTruck(@RequestHeader("truck_id") int truck_id,
			@RequestBody Location location) {
		Optional<Truck> optionalTruck = truckService.findById(truck_id);
		if (optionalTruck.isPresent()) {
			location.setId(0);
			locationService.save(location);
			Truck order_truck = optionalTruck.get();
			order_truck.setLocation(location);
			truckService.save(order_truck);
			
			//Una vez asignado la ubicacion, se crea un historial
			History history= new History();
			history.setLocation(location);
			history.setTruck(order_truck);
			SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
			Date date = new Date(System.currentTimeMillis());
			String dateTime= formatter.format(date);
			history.setDate(dateTime);
			
			historyService.save(history);
			

			return new ResponseEntity<Truck>(order_truck, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
