public class Cargo {
    private String cargoId;
    private String type;
    private String weight;
    private String destination;
    private String status;
    
    public Cargo() {
        // Constructor
    }
    
    public void setCargoId(String cargoId) {
        this.cargoId = cargoId;
    }
    
    public String getCargoId() {
        return cargoId;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    public void setWeight(String weight) {
        this.weight = weight;
    }
    
    public String getWeight() {
        return weight;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
}
