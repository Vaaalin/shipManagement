// src/main/java/com/shipmanagement/service/ShipService.java
package com.shipmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipmanagement.model.Port;
import com.shipmanagement.model.Ship;
import com.shipmanagement.model.ShipStatus;
import com.shipmanagement.repository.PortRepository;
import com.shipmanagement.repository.ShipRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ShipService {
    private final ShipRepository shipRepository;
    private final PortRepository portRepository;

    @Autowired
    public ShipService(ShipRepository shipRepository, PortRepository portRepository) {
        this.shipRepository = shipRepository;
        this.portRepository = portRepository;
    }

    public List<Ship> getAllShips() {
        return shipRepository.findAll();
    }

    public Optional<Ship> getShipById(Long id) {
        return shipRepository.findById(id);
    }

    public Ship saveShip(Ship ship) {
        return shipRepository.save(ship);
    }

    public void deleteShip(Long id) {
        shipRepository.deleteById(id);
    }

    @PostConstruct
    public void initSampleData() {
        if (shipRepository.count() == 0) {
            // Create sample ports if they don't exist
            Port port1 = new Port();
            port1.setName("Mombasa Port");
            port1.setCity("Mombasa");
            port1.setCountry("Kenya");
            port1 = portRepository.save(port1);

            Port port2 = new Port();
            port2.setName("Dar es Salaam Port");
            port2.setCity("Dar es Salaam");
            port2.setCountry("Tanzania");
            port2 = portRepository.save(port2);

            // Create sample ships
            Ship ship1 = new Ship();
            ship1.setName("MV Jambo");
            ship1.setType("Cargo");
            ship1.setCapacity(50000.0);
            ship1.setYearBuilt(2015);
            ship1.setFlag("Kenya");
            ship1.setImoNumber("IMO1234567");
            ship1.setCurrentPort(port1);
            ship1.setStatus(ShipStatus.DOCKED);
            shipRepository.save(ship1);

            Ship ship2 = new Ship();
            ship2.setName("MV Kilimanjaro");
            ship2.setType("Container");
            ship2.setCapacity(75000.0);
            ship2.setYearBuilt(2018);
            ship2.setFlag("Tanzania");
            ship2.setImoNumber("IMO7654321");
            ship2.setCurrentPort(port2);
            ship2.setStatus(ShipStatus.AT_SEA);
            shipRepository.save(ship2);
        }
    }
}