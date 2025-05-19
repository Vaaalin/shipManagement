package com.shipmanagement.controller;

import com.shipmanagement.model.Ship;
import com.shipmanagement.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ships")
public class ShipController {

    private final ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping
    public ResponseEntity<List<Ship>> getAllShips() {
        List<Ship> ships = shipService.getAllShips();
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ship> getShipById(@PathVariable Long id) {
        Optional<Ship> ship = shipService.getShipById(id);
        return ship.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Ship> createShip(@Valid @RequestBody Ship ship) {
        Ship savedShip = shipService.saveShip(ship);
        return new ResponseEntity<>(savedShip, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable Long id, @Valid @RequestBody Ship ship) {
        Optional<Ship> existingShip = shipService.getShipById(id);
        if (existingShip.isPresent()) {
            ship.setId(id);
            Ship updatedShip = shipService.saveShip(ship);
            return new ResponseEntity<>(updatedShip, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShip(@PathVariable Long id) {
        Optional<Ship> existingShip = shipService.getShipById(id);
        if (existingShip.isPresent()) {
            shipService.deleteShip(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Ship>> getShipsByType(@PathVariable String type) {
        List<Ship> ships = shipService.findShipsByType(type);
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Ship>> getShipsByStatus(@PathVariable String status) {
        List<Ship> ships = shipService.findShipsByStatus(status);
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Ship>> getShipsByLocation(@PathVariable String location) {
        List<Ship> ships = shipService.findShipsByLocation(location);
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @PatchMapping("/{id}/location")
    public ResponseEntity<Void> updateShipLocation(@PathVariable Long id, @RequestBody String location) {
        try {
            shipService.updateShipLocation(id, location);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateShipStatus(@PathVariable Long id, @RequestBody String status) {
        try {
            shipService.updateShipStatus(id, status);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
