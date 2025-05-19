package com.shipmanagement.model;

/**
 * Abstract class representing a user of the system
 */
public abstract class User {
    private String id;
    private String username;
    private String password;
    private String email;
    
    /**
     * Constructor for User
     */
    public User(String id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    /**
     * Method to authenticate user
     */
    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
