package com.shipmanagement.repository;

import com.shipmanagement.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    List<Cargo> findByType(String type);
    List<Cargo> findByStatus(String status);
    List<Cargo> findByDestination(String destination);
}
