package com.shipmanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shipmanagement.model.User;
import com.shipmanagement.repository.UserRepository;
import com.shipmanagement.service.CargoService;
import com.shipmanagement.service.DockService;
import com.shipmanagement.service.IncidentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/incidents")
public class IncidentController {

    private final IncidentService incidentService;
    private final CargoService cargoService;
    private final DockService dockService;
    private final UserRepository userRepository;

    @Autowired
    public IncidentController(IncidentService incidentService, CargoService cargoService, 
                             DockService dockService, UserRepository userRepository) {
        this.incidentService = incidentService;
        this.cargoService = cargoService;
        this.dockService = dockService;
        this.userRepository = userRepository;
    }

    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("userId") != null;
    }

    private boolean isAdmin(HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");
        return userRole != null && userRole.equals("ADMIN");
    }

    @GetMapping
    public String viewIncidents(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        Long userId = (Long) session.getAttribute("userId");
        User currentUser = userRepository.findById(userId).orElse(null);
        boolean isAdmin = isAdmin(session);

        List<com.shipmanagement.model.Incident> incidents;
        if (isAdmin) {
            // Admins can see all incidents
            incidents = incidentService.getAllIncidents();
        } else {
            // Staff can only see their reported incidents
            incidents = incidentService.getIncidentsByReportedBy(currentUser);
        }

        model.addAttribute("incidents", incidents);
        model.addAttribute("isAdmin", isAdmin);

        return "incidents/list";
    }

    @GetMapping("/report")
    public String showReportForm(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        model.addAttribute("incident", new com.shipmanagement.model.Incident());
        model.addAttribute("cargoList", cargoService.getAllCargo());
        model.addAttribute("dockList", dockService.getAllDocks());
        
        String userRole = (String) session.getAttribute("userRole");
        boolean isAdmin = userRole != null && userRole.equals("ADMIN");
        model.addAttribute("isAdmin", isAdmin);

        return "incidents/report";
    }

    @PostMapping("/report")
    public String reportIncident(@ModelAttribute com.shipmanagement.model.Incident incident, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        try {
            Long userId = (Long) session.getAttribute("userId");
            incidentService.saveIncident(incident, userId);
            redirectAttributes.addFlashAttribute("success", "Incident reported successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error reporting incident: " + e.getMessage());
        }

        return "redirect:/incidents";
    }

    @GetMapping("/view/{id}")
    public String viewIncidentDetails(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        try {
            Optional<com.shipmanagement.model.Incident> incidentOpt = incidentService.getIncidentById(id);
            if (incidentOpt.isPresent()) {
                com.shipmanagement.model.Incident incident = incidentOpt.get();
                
                // Check if user has permission to view this incident
                Long userId = (Long) session.getAttribute("userId");
                User currentUser = userRepository.findById(userId).orElse(null);
                boolean isAdmin = isAdmin(session);
                
                if (isAdmin || (currentUser != null && incident.getReportedBy().getId().equals(currentUser.getId()))) {
                    model.addAttribute("incident", incident);
                    model.addAttribute("isAdmin", isAdmin);
                    return "incidents/view";
                } else {
                    redirectAttributes.addFlashAttribute("error", "You don't have permission to view this incident");
                    return "redirect:/incidents";
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Incident not found");
                return "redirect:/incidents";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error retrieving incident: " + e.getMessage());
            return "redirect:/incidents";
        }
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        // Only admins can update incident status
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You don't have permission to update incidents");
            return "redirect:/incidents";
        }

        try {
            Optional<com.shipmanagement.model.Incident> incidentOpt = incidentService.getIncidentById(id);
            if (incidentOpt.isPresent()) {
                model.addAttribute("incident", incidentOpt.get());
                model.addAttribute("isAdmin", true);
                return "incidents/update";
            } else {
                redirectAttributes.addFlashAttribute("error", "Incident not found");
                return "redirect:/incidents";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error retrieving incident: " + e.getMessage());
            return "redirect:/incidents";
        }
    }

    @PostMapping("/update/{id}")
    public String updateIncidentStatus(@PathVariable Long id, 
                                     @RequestParam String status,
                                     @RequestParam String adminComments,
                                     HttpSession session, 
                                     RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        // Only admins can update incident status
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You don't have permission to update incidents");
            return "redirect:/incidents";
        }

        try {
            Long userId = (Long) session.getAttribute("userId");
            incidentService.updateIncidentStatus(id, status, adminComments, userId);
            redirectAttributes.addFlashAttribute("success", "Incident status updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating incident: " + e.getMessage());
        }

        return "redirect:/incidents";
    }

    @GetMapping("/delete/{id}")
    public String deleteIncident(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        // Only admins can delete incidents
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You don't have permission to delete incidents");
            return "redirect:/incidents";
        }

        try {
            incidentService.deleteIncident(id);
            redirectAttributes.addFlashAttribute("success", "Incident deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting incident: " + e.getMessage());
        }

        return "redirect:/incidents";
    }
}
