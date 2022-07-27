package com.david.retobackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.david.retobackend.model.Location;
import com.david.retobackend.repository.LocationRepository;

@Service
public class LocationService {

	@Autowired
	private LocationRepository locationRepository;

	public Location save(Location location) {
		return locationRepository.save(location);
	}

	public void deleteById(long truckId) {
		locationRepository.deleteById(truckId);
	}

	public Optional<Location> findById(long id) {
		return locationRepository.findById(id);
	}

}
