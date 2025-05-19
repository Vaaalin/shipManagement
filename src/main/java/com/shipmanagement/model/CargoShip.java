package com.shipmanagement.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a cargo ship
 * Extends the Ship class with cargo-specific attributes and methods
 */
public class CargoShip extends Ship {
    private double maxCargoWeight;
    private double currentCargoWeight;
    private List<Cargo> cargoList;
    
    /**
     * Constructor for CargoShip
     */
    public CargoShip(String id, String name, int capacity, double maxCargoWeight) {
        super(id, name, capacity, "Cargo");
        this.maxCargoWeight = maxCargoWeight;
        this.currentCargoWeight = 0.0;
        this.cargoList = new ArrayList<>();
    }
    
    /**
     * Load cargo onto the ship
     * @param cargo The cargo to load
     * @return true if loading successful, false if exceeds capacity
     */
    public boolean loadCargo(Cargo cargo) {
        if (currentCargoWeight + cargo.getWeight() <= maxCargoWeight) {
            cargoList.add(cargo);
            currentCargoWeight += cargo.getWeight();
            cargo.setStatus("Loaded");
            System.out.println("Loaded cargo: " + cargo.getType() + " (" + cargo.getWeight() + " tons) onto " + getName());
            return true;
        } else {
            System.out.println("Cannot load cargo. Would exceed max weight capacity.");
            return false;
        }
    }
    
    /**
     * Unload cargo from the ship
     * @param cargo The cargo to unload
     * @return true if unloading successful, false if cargo not found
     */
    public boolean unloadCargo(Cargo cargo) {
        if (cargoList.contains(cargo)) {
            cargoList.remove(cargo);
            currentCargoWeight -= cargo.getWeight();
            cargo.setStatus("Unloaded");
            System.out.println("Unloaded cargo: " + cargo.getType() + " (" + cargo.getWeight() + " tons) from " + getName());
            return true;
        } else {
            System.out.println("Cargo not found on this ship.");
            return false;
        }
    }
    
    /**
     * List all cargo currently on the ship
     */
    public void listCargo() {
        if (cargoList.isEmpty()) {
            System.out.println("No cargo currently loaded on " + getName());
            return;
        }
        
        System.out.println("Cargo manifest for " + getName() + ":");
        for (Cargo cargo : cargoList) {
            System.out.println("- " + cargo.getType() + " (" + cargo.getWeight() + " tons), destination: " + cargo.getDestination());
        }
        System.out.println("Total weight: " + currentCargoWeight + "/" + maxCargoWeight + " tons");
    }
    
    @Override
    public void checkStatus() {
        super.checkStatus();
        System.out.println("Cargo status: " + currentCargoWeight + "/" + maxCargoWeight + " tons (" + cargoList.size() + " items)");
    }
    
    // Getters and setters
    public double getMaxCargoWeight() {
        return maxCargoWeight;
    }
    
    public void setMaxCargoWeight(double maxCargoWeight) {
        this.maxCargoWeight = maxCargoWeight;
    }
    
    public double getCurrentCargoWeight() {
        return currentCargoWeight;
    }
    
    public List<Cargo> getCargoList() {
        return cargoList;
    }
    
    @Override
    public String toString() {
        return "CargoShip [id=" + getId() + ", name=" + getName() + 
               ", maxCargoWeight=" + maxCargoWeight + 
               ", currentCargoWeight=" + currentCargoWeight +
               ", cargoCount=" + cargoList.size() +
               ", status=" + getStatus() + 
               ", location=" + getCurrentLocation() + "]";
    }
}
