package com.shipmanagement.model;

/**
 * Interface for objects that can be tracked
 */
public interface Trackable {
    String getCurrentLocation();
    void updateLocation(String location);
    String getStatus();
}
