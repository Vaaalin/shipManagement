import java.util.ArrayList;
import java.util.List;

public class ShipManagementSystem {
    private List<Ship> ships;
    private List<Cargo> cargos;
    private List<Route> routes;
    private List<Port> ports;
    private List<Schedule> schedules;
    private List<Shipment> shipments;
    private List<ShipRoute> shipRoutes;
    
    public ShipManagementSystem() {
        ships = new ArrayList<>();
        cargos = new ArrayList<>();
        routes = new ArrayList<>();
        ports = new ArrayList<>();
        schedules = new ArrayList<>();
        shipments = new ArrayList<>();
        shipRoutes = new ArrayList<>();
    }
    
    // Ship management methods
    public void addShip(Ship ship) {
        ships.add(ship);
    }
    
    public List<Ship> getAllShips() {
        return ships;
    }
    
    // Cargo management methods
    public void addCargo(Cargo cargo) {
        cargos.add(cargo);
    }
    
    public List<Cargo> getAllCargos() {
        return cargos;
    }
    
    // Route management methods
    public void addRoute(Route route) {
        routes.add(route);
    }
    
    public List<Route> getAllRoutes() {
        return routes;
    }
    
    // Port management methods
    public void addPort(Port port) {
        ports.add(port);
    }
    
    public List<Port> getAllPorts() {
        return ports;
    }
    
    // Schedule management methods
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }
    
    public List<Schedule> getAllSchedules() {
        return schedules;
    }
    
    // Shipment management methods
    public void addShipment(Shipment shipment) {
        shipments.add(shipment);
    }
    
    public List<Shipment> getAllShipments() {
        return shipments;
    }
    
    // Ship Route management methods
    public void addShipRoute(ShipRoute shipRoute) {
        shipRoutes.add(shipRoute);
    }
    
    public List<ShipRoute> getAllShipRoutes() {
        return shipRoutes;
    }
    
    // Main method to demonstrate the system structure
    public static void main(String[] args) {
        System.out.println("Ship Management System Blueprint");
        System.out.println("This is a skeleton implementation showing class relationships");
    }
}
