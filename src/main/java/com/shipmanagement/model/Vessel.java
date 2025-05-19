package com.shipmanagement.model;

/**
 * Abstract class representing a vessel
 */
public abstract class Vessel {
    private String id;
    private String name;
    private int capacity;
    private String status;
    
    // Constructor
    public Vessel(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.status = "Docked"; // Default status
    }
    
    // Abstract method to be implemented by subclasses
    public abstract void checkStatus();
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Vessel [id=" + id + ", name=" + name + ", capacity=" + capacity + ", status=" + status + "]";
    }
}
