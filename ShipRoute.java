public class ShipRoute {
    private String assignmentId;
    private String assignmentDate;
    private String status;
    
    // Associations
    private Ship ship;
    private Route route;
    
    public ShipRoute() {
        // Constructor
    }
    
    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }
    
    public String getAssignmentId() {
        return assignmentId;
    }
    
    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }
    
    public String getAssignmentDate() {
        return assignmentDate;
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
    
    public void setRoute(Route route) {
        this.route = route;
    }
    
    public Route getRoute() {
        return route;
    }
}
