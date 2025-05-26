package com.shipmanagement.service;

import com.shipmanagement.model.Port;
import com.shipmanagement.repository.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortService {
    private final PortRepository portRepository;

    @Autowired
    public PortService(PortRepository portRepository) {
        this.portRepository = portRepository;
    }

    public List<Port> getAllPorts() {
        return portRepository.findAll();
    }

    public Port savePort(Port port) {
        return portRepository.save(port);
    }

    public void deletePort(Long id) {
        portRepository.deleteById(id);
    }
}