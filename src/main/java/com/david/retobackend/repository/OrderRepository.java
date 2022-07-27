package com.david.retobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.david.retobackend.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
