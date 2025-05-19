package com.shipmanagement.model;

import javax.persistence.Entity;

/**
 * Ship class that extends Vessel and implements Trackable
 */
@Entity
public class Ship extends Vessel implements Trackable {
    private String type;
    private String location;
    private String destination;
    
    /**
     * Constructor for Ship
     */
    public Ship(String id, String name, int capacity, String type) {
        super(id, name, capacity);
        this.type = type;
        this.location = "Port";
        this.destination = "Unknown";
    }
    
    @Override
    public void checkStatus() {
        System.out.println("Checking ship status: " + getStatus());
    }
    
    @Override
    public String getCurrentLocation() {
        return location;
    }
    
    @Override
    public void updateLocation(String location) {
        this.location = location;
        System.out.println("Ship " + getName() + " location updated to: " + location);
    }
    
    // Getters and setters
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    @Override
    public String toString() {
        return "Ship [id=" + getId() + ", name=" + getName() + ", type=" + type + 
               ", capacity=" + getCapacity() + ", status=" + getStatus() + 
               ", location=" + location + ", destination=" + destination + "]";
    }
}
