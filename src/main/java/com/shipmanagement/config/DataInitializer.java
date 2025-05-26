package com.shipmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shipmanagement.model.Port;
import com.shipmanagement.model.Ship;
import com.shipmanagement.model.ShipStatus;
import com.shipmanagement.model.User;
import com.shipmanagement.repository.PortRepository;
import com.shipmanagement.repository.ShipRepository;
import com.shipmanagement.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Autowired
    private PortRepository portRepository;
    
    @Autowired
    private ShipRepository shipRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Initialize users
            initializeUsers();
            
            // Initialize ports if they don't exist
            createPortIfNotExists("Mombasa", "Kenya", "Mombasa", -4.0435, 39.6682);
            createPortIfNotExists("Dar es Salaam", "Tanzania", "Dar es Salaam", -6.7924, 39.2083);
            
            // Initialize ships
            initializeShips();
            
            System.out.println("Data initialization completed successfully.");
        };
    }
    
    private void initializeUsers() {
        // Only create users if they don't exist
        if (userRepository.count() == 0) {
            // Create admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123"); // In a real app, this would be encoded
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@shipmanagement.com");
            admin.setRole("ADMIN");
            userRepository.save(admin);
            
            // Create staff user
            User staff = new User();
            staff.setUsername("staff");
            staff.setPassword("staff123"); // In a real app, this would be encoded
            staff.setFirstName("Staff");
            staff.setLastName("User");
            staff.setEmail("staff@shipmanagement.com");
            staff.setRole("STAFF");
            userRepository.save(staff);
            
            System.out.println("Default users created.");
        }
    }
    
    private void createPortIfNotExists(String name, String country, String city, Double latitude, Double longitude) {
        if (portRepository.findByName(name).isEmpty()) {
            Port port = new Port();
            port.setName(name);
            port.setCountry(country);
            port.setCity(city);
            port.setLatitude(latitude);
            port.setLongitude(longitude);
            portRepository.save(port);
            System.out.println("Created port: " + name);
        }
    }
    
    private void initializeShips() {
        // Only create ships if they don't exist
        if (shipRepository.count() == 0) {
            Port mombasaPort = portRepository.findByName("Mombasa")
                .orElse(null);
            
            if (mombasaPort != null) {
                Ship ship1 = new Ship();
                ship1.setName("MV Kenya");
                ship1.setType("Container");
                ship1.setCapacity(50000.0);
                ship1.setYearBuilt(2018);
                ship1.setFlag("Kenya");
                ship1.setImoNumber("IMO1234567");
                ship1.setCurrentPort(mombasaPort);
                ship1.setStatus(ShipStatus.DOCKED);
                shipRepository.save(ship1);
                System.out.println("Created ship: MV Kenya");
            }
        }
    }
}