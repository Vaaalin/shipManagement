package com.shipmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipmanagement.model.Dock;

@Repository
public interface DockRepository extends JpaRepository<Dock, Long> {
    
    List<Dock> findByAvailableTrue();
    
    List<Dock> findByName(String name);
    
    List<Dock> findByLocation(String location);
}
