package com.david.retobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.david.retobackend.model.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long>{

}
