package com.shipmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ships")
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String type;
    
    private double capacity;
    private int yearBuilt;
    private String flag;
    
    @Column(name = "imo_number", unique = true)
    private String imoNumber;
    
    @ManyToOne
    @JoinColumn(name = "current_port_id")
    private Port currentPort;
    
    @Enumerated(EnumType.STRING)
    private ShipStatus status;
    
    // Constructors
    public Ship() {}
    
    public Ship(String name, String type, double capacity, int yearBuilt, String flag, String imoNumber) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.yearBuilt = yearBuilt;
        this.flag = flag;
        this.imoNumber = imoNumber;
        this.status = ShipStatus.DOCKED;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public int getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(int yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getImoNumber() {
        return imoNumber;
    }

    public void setImoNumber(String imoNumber) {
        this.imoNumber = imoNumber;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public ShipStatus getStatus() {
        return status;
    }

    public void setStatus(ShipStatus status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Ship [id=" + id + ", name=" + name + ", type=" + type + ", imoNumber=" + imoNumber + "]";
    }
}