package com.david.retobackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.retobackend.service.HistoryService;
import com.david.retobackend.exception.TruckNotFoundException;
import com.david.retobackend.model.History;

/**
 * 
 * Controlador de la clase del historial (Truck), el cual permitirá visualizar
 * el respectivo historial
 * 
 * @author: David Gutierrez Mariblanca
 * 
 * 
 */
@RestController
@RequestMapping("/api")
public class HistoryController {

	@Autowired
	private HistoryService historyService;

	/**
	 * 
	 * Metodo que devuelve el historial
	 * 
	 * @return El historial
	 * 
	 */
	@GetMapping("/history")
	public ResponseEntity<List<History>> findAll() {
		List<History> history = historyService.findAll();
		if (history.isEmpty()) {
			throw new TruckNotFoundException("No trucks were found");
		}
		return new ResponseEntity<List<History>>(history, HttpStatus.OK);
	}

}
