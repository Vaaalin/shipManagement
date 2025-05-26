package com.shipmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipmanagement.model.Incident;
import com.shipmanagement.model.User;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    
    List<Incident> findByReportedBy(User reportedBy);
    
    List<Incident> findByStatus(String status);
    
    List<Incident> findByIncidentType(String incidentType);
    
    List<Incident> findBySeverity(String severity);
}
