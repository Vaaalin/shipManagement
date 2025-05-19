public class Shipment {
    private String shipmentId;
    private String date;
    private String status;
    
    // Associations
    private Ship ship;
    private Cargo cargo;
    
    public Shipment() {
        // Constructor
    }
    
    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }
    
    public String getShipmentId() {
        return shipmentId;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setShip(Ship ship) {
        this.ship = ship;
    }
    
    public Ship getShip() {
        return ship;
    }
    
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
    public Cargo getCargo() {
        return cargo;
    }
}
