package com.shipmanagement.service.impl;

import com.shipmanagement.model.Ship;
import com.shipmanagement.repository.ShipRepository;
import com.shipmanagement.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService {

    private final ShipRepository shipRepository;

    @Autowired
    public ShipServiceImpl(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @Override
    public List<Ship> getAllShips() {
        return shipRepository.findAll();
    }

    @Override
    public Optional<Ship> getShipById(Long id) {
        return shipRepository.findById(id);
    }

    @Override
    public Ship saveShip(Ship ship) {
        return shipRepository.save(ship);
    }

    @Override
    public void deleteShip(Long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public List<Ship> findShipsByType(String type) {
        return shipRepository.findByType(type);
    }

    @Override
    public List<Ship> findShipsByStatus(String status) {
        return shipRepository.findByStatus(status);
    }

    @Override
    public List<Ship> findShipsByLocation(String location) {
        return shipRepository.findByLocation(location);
    }

    @Override
    public Ship findShipByName(String name) {
        return shipRepository.findByName(name);
    }

    @Override
    public void updateShipLocation(Long id, String location) {
        Optional<Ship> shipOptional = shipRepository.findById(id);
        if (shipOptional.isPresent()) {
            Ship ship = shipOptional.get();
            ship.updateLocation(location);
            shipRepository.save(ship);
        } else {
            throw new RuntimeException("Ship not found with id: " + id);
        }
    }

    @Override
    public void updateShipStatus(Long id, String status) {
        Optional<Ship> shipOptional = shipRepository.findById(id);
        if (shipOptional.isPresent()) {
            Ship ship = shipOptional.get();
            ship.setStatus(status);
            shipRepository.save(ship);
        } else {
            throw new RuntimeException("Ship not found with id: " + id);
        }
    }
}
