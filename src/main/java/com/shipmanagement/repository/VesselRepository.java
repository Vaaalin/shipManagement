package com.shipmanagement.repository;

import com.shipmanagement.model.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VesselRepository extends JpaRepository<Vessel, Long> {
    // Custom query methods can be added here
}
