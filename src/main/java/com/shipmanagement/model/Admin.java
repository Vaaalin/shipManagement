package com.shipmanagement.model;

/**
 * Class representing an admin user with special privileges
 */
public class Admin extends User {
    private String accessLevel;
    
    /**
     * Constructor for Admin
     */
    public Admin(String id, String username, String password, String email, String accessLevel) {
        super(id, username, password, email);
        this.accessLevel = accessLevel;
    }
    
    /**
     * Admin-specific methods
     */
    public void generateSystemReport() {
        System.out.println("Admin generating system report...");
    }
    
    public void manageUsers() {
        System.out.println("Admin managing users...");
    }
    
    public void updateShipLocation(Ship ship, String location) {
        ship.updateLocation(location);
        System.out.println("Admin updated ship location to: " + location);
    }
    
    public void updateShipStatus(Ship ship, String status) {
        ship.setStatus(status);
        System.out.println("Admin updated ship status to: " + status);
    }
    
    // Getters and setters
    public String getAccessLevel() {
        return accessLevel;
    }
    
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
    
    @Override
    public String toString() {
        return "Admin [id=" + getId() + ", username=" + getUsername() + 
               ", email=" + getEmail() + ", accessLevel=" + accessLevel + "]";
    }
}
