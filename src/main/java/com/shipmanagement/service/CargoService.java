package com.shipmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipmanagement.model.Cargo;
import com.shipmanagement.model.Ship;
import com.shipmanagement.model.User;
import com.shipmanagement.repository.CargoRepository;
import com.shipmanagement.repository.ShipRepository;
import com.shipmanagement.repository.UserRepository;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;
    private final ShipRepository shipRepository;
    private final UserRepository userRepository;

    @Autowired
    public CargoService(CargoRepository cargoRepository, ShipRepository shipRepository, UserRepository userRepository) {
        this.cargoRepository = cargoRepository;
        this.shipRepository = shipRepository;
        this.userRepository = userRepository;
    }

    public List<Cargo> getAllCargo() {
        return cargoRepository.findAll();
    }

    public List<Cargo> getCargoByShip(Ship ship) {
        return cargoRepository.findByShip(ship);
    }

    public List<Cargo> getCargoByLastUpdatedBy(User user) {
        return cargoRepository.findByLastUpdatedBy(user);
    }

    public Optional<Cargo> getCargoById(Long id) {
        return cargoRepository.findById(id);
    }

    public Cargo saveCargo(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public void deleteCargo(Long id) {
        cargoRepository.deleteById(id);
    }

    public Cargo updateCargoDetails(Long id, Cargo cargoDetails, Long updatedByUserId) {
        Optional<Cargo> existingCargoOpt = cargoRepository.findById(id);
        Optional<User> userOpt = userRepository.findById(updatedByUserId);
        
        if (existingCargoOpt.isPresent() && userOpt.isPresent()) {
            Cargo existingCargo = existingCargoOpt.get();
            User updatedBy = userOpt.get();
            
            // Update cargo details
            existingCargo.setCargoType(cargoDetails.getCargoType());
            existingCargo.setWeight(cargoDetails.getWeight());
            existingCargo.setContentDescription(cargoDetails.getContentDescription());
            existingCargo.setHazardous(cargoDetails.isHazardous());
            existingCargo.setSpecialHandling(cargoDetails.getSpecialHandling());
            existingCargo.setLastUpdatedBy(updatedBy);
            existingCargo.setLastUpdatedDate(new java.util.Date());
            
            // If ship is being changed, update it
            if (cargoDetails.getShip() != null) {
                Optional<Ship> shipOpt = shipRepository.findById(cargoDetails.getShip().getId());
                if (shipOpt.isPresent()) {
                    existingCargo.setShip(shipOpt.get());
                }
            }
            
            return cargoRepository.save(existingCargo);
        } else {
            throw new RuntimeException("Cargo or User not found");
        }
    }
}
