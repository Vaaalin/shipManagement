package com.shipmanagement.controller;

import com.shipmanagement.model.Port;
import com.shipmanagement.repository.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ports")
public class PortController {
    
    @Autowired
    private PortRepository portRepository;
    
    @GetMapping
    public List<Port> getAllPorts() {
        return portRepository.findAll();
    }
    
    @PostMapping
    public Port createPort(@RequestBody Port port) {
        return portRepository.save(port);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Port> getPortById(@PathVariable Long id) {
        return portRepository.findById(id)
                .map(port -> ResponseEntity.ok().body(port))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Port> updatePort(@PathVariable Long id, @RequestBody Port portDetails) {
        return portRepository.findById(id)
                .map(port -> {
                    port.setName(portDetails.getName());
                    port.setCity(portDetails.getCity());
                    port.setCountry(portDetails.getCountry());
                    return ResponseEntity.ok(portRepository.save(port));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePort(@PathVariable Long id) {
        return portRepository.findById(id)
                .map(port -> {
                    portRepository.delete(port);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
