package com.shipmanagement.repository;

import com.shipmanagement.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findByType(String type);
    List<Ship> findByStatus(String status);
    List<Ship> findByLocation(String location);
    Ship findByName(String name);
}
