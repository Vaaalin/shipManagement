// src/main/java/com/shipmanagement/repository/ShipRepository.java
package com.shipmanagement.repository;

import com.shipmanagement.model.Ship;
import com.shipmanagement.model.ShipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findByType(String type);
    List<Ship> findByStatus(ShipStatus status);
    List<Ship> findByCurrentPortId(Long portId);
}