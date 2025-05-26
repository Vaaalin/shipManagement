// In Route.java
package com.shipmanagement.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "origin_port_id")
    private Port originPort;
    
    @ManyToOne
    @JoinColumn(name = "destination_port_id")
    private Port destinationPort;
    
    private Double distance; // in nautical miles
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private String status = "SCHEDULED"; // Default status
}