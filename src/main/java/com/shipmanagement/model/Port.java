package com.shipmanagement.model;

/**
 * Class representing a port where ships can dock
 */
public class Port {
    private String id;
    private String name;
    private String location;
    private int capacity;
    private String status;
    
    /**
     * Constructor for Port
     */
    public Port(String id, String name, String location, int capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.status = "Operational";
    }
    
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
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
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
        return "Port [id=" + id + ", name=" + name + ", location=" + location + 
               ", capacity=" + capacity + ", status=" + status + "]";
    }
}
