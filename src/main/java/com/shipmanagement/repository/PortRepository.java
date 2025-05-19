package com.shipmanagement.repository;

import com.shipmanagement.model.Port;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortRepository extends JpaRepository<Port, Long> {
    List<Port> findByStatus(String status);
    Port findByName(String name);
    List<Port> findByLocation(String location);
}
