package com.shipmanagement.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipmanagement.model.Port;
import com.shipmanagement.model.Route;
import com.shipmanagement.repository.PortRepository;
import com.shipmanagement.repository.RouteRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;
    
    @Autowired
    private PortRepository portRepository;  
    
    @PostConstruct
    public void initializeSampleData() {
        if (routeRepository.count() == 0) {
            // Create sample ports if they don't exist
            Port mombasa = portRepository.findByName("Mombasa")
                .orElseGet(() -> portRepository.save(new Port("Mombasa", "Kenya", "Mombasa", -4.0435, 39.6682)));
            
            Port durban = portRepository.findByName("Durban")
                .orElseGet(() -> portRepository.save(new Port("Durban", "South Africa", "Durban", -29.8587, 31.0218)));
            
            // Create sample route
            Route route = new Route();
            route.setName("Mombasa to Durban");
            route.setOriginPort(mombasa);
            route.setDestinationPort(durban);
            route.setDistance(1500.0);
            route.setDepartureDate(LocalDate.now().plusDays(2));
            route.setArrivalDate(LocalDate.now().plusDays(5));
            route.setStatus("SCHEDULED");
            
            routeRepository.save(route);
        }
    }
    
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }
    
    public Route saveRoute(Route route) {
        return routeRepository.save(route);
    }
    
    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }
}