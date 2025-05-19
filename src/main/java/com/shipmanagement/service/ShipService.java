package com.shipmanagement.service;

import com.shipmanagement.model.Ship;

import java.util.List;
import java.util.Optional;

public interface ShipService {
    List<Ship> getAllShips();
    Optional<Ship> getShipById(Long id);
    Ship saveShip(Ship ship);
    void deleteShip(Long id);
    List<Ship> findShipsByType(String type);
    List<Ship> findShipsByStatus(String status);
    List<Ship> findShipsByLocation(String location);
    Ship findShipByName(String name);
    void updateShipLocation(Long id, String location);
    void updateShipStatus(Long id, String status);
}
