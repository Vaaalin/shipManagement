package com.shipmanagement.model;

/**
 * Class representing a passenger ship
 * Extends the Ship class with passenger-specific attributes and methods
 */
public class PassengerShip extends Ship {
    private int passengerCapacity;
    private int currentPassengers;
    private String[] amenities;
    
    /**
     * Constructor for PassengerShip
     */
    public PassengerShip(String id, String name, int capacity, int passengerCapacity, String[] amenities) {
        super(id, name, capacity, "Passenger");
        this.passengerCapacity = passengerCapacity;
        this.currentPassengers = 0;
        this.amenities = amenities;
    }
    
    /**
     * Board passengers onto the ship
     * @param count Number of passengers to board
     * @return true if boarding successful, false if exceeds capacity
     */
    public boolean boardPassengers(int count) {
        if (currentPassengers + count <= passengerCapacity) {
            currentPassengers += count;
            System.out.println(count + " passengers boarded " + getName() + ". Current count: " + currentPassengers);
            return true;
        } else {
            System.out.println("Cannot board " + count + " passengers. Would exceed capacity.");
            return false;
        }
    }
    
    /**
     * Disembark passengers from the ship
     * @param count Number of passengers to disembark
     * @return true if disembarking successful, false if count exceeds current passengers
     */
    public boolean disembarkPassengers(int count) {
        if (count <= currentPassengers) {
            currentPassengers -= count;
            System.out.println(count + " passengers disembarked from " + getName() + ". Current count: " + currentPassengers);
            return true;
        } else {
            System.out.println("Cannot disembark " + count + " passengers. Only " + currentPassengers + " on board.");
            return false;
        }
    }
    
    /**
     * List all amenities available on the ship
     */
    public void listAmenities() {
        System.out.println("Amenities available on " + getName() + ":");
        for (String amenity : amenities) {
            System.out.println("- " + amenity);
        }
    }
    
    @Override
    public void checkStatus() {
        super.checkStatus();
        System.out.println("Passenger status: " + currentPassengers + "/" + passengerCapacity);
    }
    
    // Getters and setters
    public int getPassengerCapacity() {
        return passengerCapacity;
    }
    
    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
    
    public int getCurrentPassengers() {
        return currentPassengers;
    }
    
    public String[] getAmenities() {
        return amenities;
    }
    
    public void setAmenities(String[] amenities) {
        this.amenities = amenities;
    }
    
    @Override
    public String toString() {
        return "PassengerShip [id=" + getId() + ", name=" + getName() + 
               ", passengerCapacity=" + passengerCapacity + 
               ", currentPassengers=" + currentPassengers +
               ", status=" + getStatus() + 
               ", location=" + getCurrentLocation() + "]";
    }
}
