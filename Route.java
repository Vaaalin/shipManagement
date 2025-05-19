public class Route {
    private String routeId;
    private String startPoint;
    private String endPoint;
    private String distance;
    private String estimatedTime;
    
    public Route() {
        // Constructor
    }
    
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
    
    public String getRouteId() {
        return routeId;
    }
    
    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }
    
    public String getStartPoint() {
        return startPoint;
    }
    
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
    
    public String getEndPoint() {
        return endPoint;
    }
    
    public void setDistance(String distance) {
        this.distance = distance;
    }
    
    public String getDistance() {
        return distance;
    }
    
    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
    
    public String getEstimatedTime() {
        return estimatedTime;
    }
}
