package com.shipmanagement.model;

/**
 * Class representing a regular user with limited privileges
 */
public class RegularUser extends User {
    private String department;
    
    /**
     * Constructor for RegularUser
     */
    public RegularUser(String id, String username, String password, String email, String department) {
        super(id, username, password, email);
        this.department = department;
    }
    
    /**
     * Regular user specific methods
     */
    public void viewShipDetails(Ship ship) {
        System.out.println("Viewing ship details: " + ship.toString());
    }
    
    public void viewCargoDetails(Cargo cargo) {
        System.out.println("Viewing cargo details: " + cargo.toString());
    }
    
    // Getters and setters
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    @Override
    public String toString() {
        return "RegularUser [id=" + getId() + ", username=" + getUsername() + 
               ", email=" + getEmail() + ", department=" + department + "]";
    }
}
