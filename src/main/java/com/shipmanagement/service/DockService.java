package com.shipmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipmanagement.model.Dock;
import com.shipmanagement.repository.DockRepository;

@Service
public class DockService {

    private final DockRepository dockRepository;

    @Autowired
    public DockService(DockRepository dockRepository) {
        this.dockRepository = dockRepository;
    }

    public List<Dock> getAllDocks() {
        return dockRepository.findAll();
    }

    public Optional<Dock> getDockById(Long id) {
        return dockRepository.findById(id);
    }

    public List<Dock> getAvailableDocks() {
        return dockRepository.findByAvailableTrue();
    }

    public Dock saveDock(Dock dock) {
        return dockRepository.save(dock);
    }

    public void deleteDock(Long id) {
        dockRepository.deleteById(id);
    }

    public Dock updateDockAvailability(Long id, boolean available) {
        Optional<Dock> dockOpt = dockRepository.findById(id);
        if (dockOpt.isPresent()) {
            Dock dock = dockOpt.get();
            dock.setAvailable(available);
            return dockRepository.save(dock);
        } else {
            throw new RuntimeException("Dock not found");
        }
    }
}
