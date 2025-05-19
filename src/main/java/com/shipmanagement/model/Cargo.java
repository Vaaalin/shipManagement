package com.shipmanagement.model;

/**
 * Class representing cargo that can be transported by ships
 */
public class Cargo {
    private String id;
    private String type;
    private double weight;
    private String destination;
    private String status;
    
    /**
     * Constructor for Cargo
     */
    public Cargo(String id, String type, double weight, String destination) {
        this.id = id;
        this.type = type;
        this.weight = weight;
        this.destination = destination;
        this.status = "Pending";
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Cargo [id=" + id + ", type=" + type + ", weight=" + weight + 
               ", destination=" + destination + ", status=" + status + "]";
    }
}
