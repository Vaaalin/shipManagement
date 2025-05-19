package com.shipmanagement;

import com.shipmanagement.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for the Ship Management System
 * This demonstrates the use of OOP concepts like inheritance, polymorphism, and interfaces
 */
public class ShipManagementSystem {
    private List<Vessel> vessels;
    private List<Cargo> cargos;
    private List<Port> ports;
    private List<User> users;
    
    /**
     * Constructor initializes the lists
     */
    public ShipManagementSystem() {
        vessels = new ArrayList<>();
        cargos = new ArrayList<>();
        ports = new ArrayList<>();
        users = new ArrayList<>();
    }
    
    /**
     * Add a vessel to the system
     */
    public void addVessel(Vessel vessel) {
        vessels.add(vessel);
        System.out.println("Added vessel: " + vessel.getName());
    }
    
    /**
     * Add cargo to the system
     */
    public void addCargo(Cargo cargo) {
        cargos.add(cargo);
        System.out.println("Added cargo: " + cargo.getType());
    }
    
    /**
     * Add a port to the system
     */
    public void addPort(Port port) {
        ports.add(port);
        System.out.println("Added port: " + port.getName());
    }
    
    /**
     * Add a user to the system
     */
    public void addUser(User user) {
        users.add(user);
        System.out.println("Added user: " + user.getUsername());
    }
    
    /**
     * Get all vessels in the system
     */
    public List<Vessel> getAllVessels() {
        return vessels;
    }
    
    /**
     * Get all cargo in the system
     */
    public List<Cargo> getAllCargo() {
        return cargos;
    }
    
    /**
     * Get all ports in the system
     */
    public List<Port> getAllPorts() {
        return ports;
    }
    
    /**
     * Get all users in the system
     */
    public List<User> getAllUsers() {
        return users;
    }
    
    /**
     * Main method to demonstrate the system
     */
    public static void main(String[] args) {
        System.out.println("Starting Ship Management System...");
        
        // Create the system
        ShipManagementSystem system = new ShipManagementSystem();
        
        // Create and add ports
        Port portA = new Port("P001", "Port A", "Mombasa", 10000);
        Port portB = new Port("P002", "Port B", "Madagascar", 8000);
        system.addPort(portA);
        system.addPort(portB);
        
        // Create and add ships - now using specialized ship classes
        CargoShip cargoShip = new CargoShip("S001", "Oceanic Voyager", 5000, 10000.0);
        String[] amenities = {"Restaurant", "Swimming Pool", "Casino", "Theater"};
        PassengerShip passengerShip = new PassengerShip("S002", "Northern Star", 2000, 1500, amenities);
        system.addVessel(cargoShip);
        system.addVessel(passengerShip);
        
        // Create and add cargo
        Cargo electronics = new Cargo("C001", "Electronics", 500, "Port B");
        Cargo food = new Cargo("C002", "Food", 1000, "Port A");
        system.addCargo(electronics);
        system.addCargo(food);
        
        // Create and add users
        Admin admin = new Admin("U001", "admin", "admin123", "admin@example.com", "Full Access");
        RegularUser user = new RegularUser("U002", "user", "user123", "user@example.com", "Shipping");
        system.addUser(admin);
        system.addUser(user);
        
        // Demonstrate inheritance and polymorphism
        System.out.println("\nDemonstrating inheritance and polymorphism:");
        for (Vessel vessel : system.getAllVessels()) {
            vessel.checkStatus(); // Polymorphic call to checkStatus()
        }
        
        // Demonstrate interface implementation
        System.out.println("\nDemonstrating interface implementation:");
        cargoShip.updateLocation("Port A");
        passengerShip.updateLocation("Port B");
        
        // Demonstrate specialized CargoShip functionality
        System.out.println("\nDemonstrating CargoShip functionality:");
        cargoShip.loadCargo(electronics);
        cargoShip.loadCargo(food);
        cargoShip.listCargo();
        cargoShip.unloadCargo(electronics);
        cargoShip.listCargo();
        
        // Demonstrate specialized PassengerShip functionality
        System.out.println("\nDemonstrating PassengerShip functionality:");
        passengerShip.boardPassengers(500);
        passengerShip.listAmenities();
        passengerShip.boardPassengers(800);
        passengerShip.disembarkPassengers(300);
        
        // Demonstrate admin functionality
        System.out.println("\nDemonstrating admin functionality:");
        admin.updateShipStatus(cargoShip, "Active");
        admin.generateSystemReport();
        
        // Demonstrate regular user functionality
        System.out.println("\nDemonstrating regular user functionality:");
        user.viewShipDetails(passengerShip);
        user.viewCargoDetails(electronics);
        
        System.out.println("\nShip Management System demonstration completed.");
    }
}
