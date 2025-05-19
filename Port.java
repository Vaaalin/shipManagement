public class Port {
    private String portId;
    private String name;
    private String location;
    private String capacity;
    private String status;
    
    public Port() {
        // Constructor
    }
    
    public void setPortId(String portId) {
        this.portId = portId;
    }
    
    public String getPortId() {
        return portId;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
    
    public String getCapacity() {
        return capacity;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
}
