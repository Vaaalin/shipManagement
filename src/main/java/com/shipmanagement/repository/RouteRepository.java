package com.shipmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipmanagement.model.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    // Remove or update the findByShipId method since we don't have ship relationship anymore
    List<Route> findByOriginPortId(Long originPortId);
    List<Route> findByDestinationPortId(Long destinationPortId);
    List<Route> findByStatus(String status);
}