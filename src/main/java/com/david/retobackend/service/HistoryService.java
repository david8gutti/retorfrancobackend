package com.david.retobackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.david.retobackend.model.History;
import com.david.retobackend.repository.HistoryRepository;

@Service
public class HistoryService {

	@Autowired
	private HistoryRepository historyRepository;

	
	public List<History> findAll() {
		List<History> history = historyRepository.findAll();
		return history;
	}
	public History save(History history) {
		return historyRepository.save(history);
	}

	public void deleteById(long truckId) {
		historyRepository.deleteById(truckId);
	}

	public Optional<History> findById(long id) {
		return historyRepository.findById(id);
	}

}
