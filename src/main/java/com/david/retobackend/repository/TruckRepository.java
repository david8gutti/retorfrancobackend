package com.david.retobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.david.retobackend.model.Truck;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long>{

}
