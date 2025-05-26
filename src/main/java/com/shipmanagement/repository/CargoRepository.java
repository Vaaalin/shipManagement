package com.shipmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipmanagement.model.Cargo;
import com.shipmanagement.model.Ship;
import com.shipmanagement.model.User;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    Optional<Cargo> findById(Long id);
    List<Cargo> findByShip(Ship ship);
    List<Cargo> findByCargoType(String cargoType);
    List<Cargo> findByLastUpdatedBy(User user);
    List<Cargo> findByHazardous(boolean hazardous);
}
