// src/main/java/com/shipmanagement/controller/ShipController.java
package com.shipmanagement.controller;

import com.shipmanagement.model.Ship;
import com.shipmanagement.model.ShipStatus;
import com.shipmanagement.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ships")
public class ShipController {
    private final ShipRepository shipRepository;

    @Autowired
    public ShipController(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @GetMapping
    public List<Ship> getAllShips() {
        return shipRepository.findAll();
    }

    @GetMapping("/type/{type}")
    public List<Ship> getShipsByType(@PathVariable String type) {
        return shipRepository.findByType(type);
    }

    @GetMapping("/status/{status}")
    public List<Ship> getShipsByStatus(@PathVariable String status) {
        return shipRepository.findByStatus(ShipStatus.valueOf(status.toUpperCase()));
    }

    @GetMapping("/port/{portId}")
    public List<Ship> getShipsByPort(@PathVariable Long portId) {
        return shipRepository.findByCurrentPortId(portId);
    }
}