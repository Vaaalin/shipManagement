package com.shipmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipmanagement.model.Port;

@Repository
public interface PortRepository extends JpaRepository<Port, Long> {
    Optional<Port> findByName(String name);
}