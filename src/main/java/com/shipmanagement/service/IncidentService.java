package com.shipmanagement.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipmanagement.model.Incident;
import com.shipmanagement.model.User;
import com.shipmanagement.repository.IncidentRepository;
import com.shipmanagement.repository.UserRepository;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;

    @Autowired
    public IncidentService(IncidentRepository incidentRepository, UserRepository userRepository) {
        this.incidentRepository = incidentRepository;
        this.userRepository = userRepository;
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public List<Incident> getIncidentsByReportedBy(User user) {
        return incidentRepository.findByReportedBy(user);
    }

    public Optional<Incident> getIncidentById(Long id) {
        return incidentRepository.findById(id);
    }

    public Incident saveIncident(Incident incident, Long reportedByUserId) {
        Optional<User> userOpt = userRepository.findById(reportedByUserId);
        if (userOpt.isPresent()) {
            User reportedBy = userOpt.get();
            incident.setReportedBy(reportedBy);
            incident.setReportDate(new Date());
            incident.setStatus("PENDING"); // Default status
            return incidentRepository.save(incident);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Incident updateIncidentStatus(Long id, String status, String adminComments, Long updatedByUserId) {
        Optional<Incident> incidentOpt = incidentRepository.findById(id);
        Optional<User> userOpt = userRepository.findById(updatedByUserId);
        
        if (incidentOpt.isPresent() && userOpt.isPresent()) {
            Incident incident = incidentOpt.get();
            User updatedBy = userOpt.get();
            
            incident.setStatus(status);
            incident.setAdminComments(adminComments);
            incident.setLastUpdatedBy(updatedBy);
            incident.setLastUpdatedDate(new Date());
            
            return incidentRepository.save(incident);
        } else {
            throw new RuntimeException("Incident or User not found");
        }
    }

    public void deleteIncident(Long id) {
        incidentRepository.deleteById(id);
    }
}
