package com.david.retobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.david.retobackend.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{

}
