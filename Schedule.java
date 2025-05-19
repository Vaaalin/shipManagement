public class Schedule {
    private String scheduleId;
    private String departureDate;
    private String arrivalDate;
    private String departurePort;
    private String arrivalPort;
    private String status;
    
    // Association with Ship
    private Ship ship;
    
    public Schedule() {
        // Constructor
    }
    
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
    
    public String getScheduleId() {
        return scheduleId;
    }
    
    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }
    
    public String getDepartureDate() {
        return departureDate;
    }
    
    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
    
    public String getArrivalDate() {
        return arrivalDate;
    }
    
    public void setDeparturePort(String departurePort) {
        this.departurePort = departurePort;
    }
    
    public String getDeparturePort() {
        return departurePort;
    }
    
    public void setArrivalPort(String arrivalPort) {
        this.arrivalPort = arrivalPort;
    }
    
    public String getArrivalPort() {
        return arrivalPort;
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
}
