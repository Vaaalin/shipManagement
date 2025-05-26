package com.shipmanagement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shipmanagement.model.Route;
import com.shipmanagement.model.Ship;
import com.shipmanagement.model.User;
import com.shipmanagement.repository.UserRepository;
import com.shipmanagement.service.PortService;
import com.shipmanagement.service.RouteService;
import com.shipmanagement.service.ShipService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {

    private final ShipService shipService;
    private final PortService portService;
    private final RouteService routeService;
    private final UserRepository userRepository;

    @Autowired
    public WebController(ShipService shipService, PortService portService, RouteService routeService, UserRepository userRepository) {
        this.shipService = shipService;
        this.portService = portService;
        this.routeService = routeService;
        this.userRepository = userRepository;
    }

    // Check if user is logged in
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("userId") != null;
    }

    @GetMapping("/ships")
    public String ships(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        model.addAttribute("ships", shipService.getAllShips());
        model.addAttribute("ports", portService.getAllPorts());
        return "ships";
    }

    @GetMapping("/ports")
    public String ports(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        model.addAttribute("ports", portService.getAllPorts());
        model.addAttribute("ships", shipService.getAllShips());
        return "ports";
    }

    @GetMapping("/dashboard/routes")
    public String routes(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        model.addAttribute("routes", routeService.getAllRoutes());
        model.addAttribute("ports", portService.getAllPorts());
        return "routes";
    }

    @PostMapping("/ships/add")
    public String addShip(@ModelAttribute Ship ship, RedirectAttributes redirectAttributes) {
        try {
            shipService.saveShip(ship);
            redirectAttributes.addFlashAttribute("success", "Ship added successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding ship: " + e.getMessage());
        }
        return "redirect:/ships";
    }

    @GetMapping("/ships/delete/{id}")
    public String deleteShip(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            shipService.deleteShip(id);
            redirectAttributes.addFlashAttribute("success", "Ship deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting ship: " + e.getMessage());
        }
        return "redirect:/ships";
    }

    @GetMapping("/ships/edit/{id}")
    public String showEditShipForm(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        try {
            Optional<Ship> shipOpt = shipService.getShipById(id);
            if (shipOpt.isPresent()) {
                model.addAttribute("ship", shipOpt.get());
                model.addAttribute("ports", portService.getAllPorts());
                return "edit-ship";
            } else {
                redirectAttributes.addFlashAttribute("error", "Ship not found");
                return "redirect:/ships";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error retrieving ship: " + e.getMessage());
            return "redirect:/ships";
        }
    }

    @PostMapping("/ships/edit/{id}")
    public String updateShip(@PathVariable Long id, @ModelAttribute Ship ship, RedirectAttributes redirectAttributes) {
        try {
            Optional<Ship> existingShipOpt = shipService.getShipById(id);
            if (existingShipOpt.isPresent()) {
                ship.setId(id); // Ensure the ID is set correctly
                shipService.saveShip(ship);
                redirectAttributes.addFlashAttribute("success", "Ship updated successfully");
            } else {
                redirectAttributes.addFlashAttribute("error", "Ship not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating ship: " + e.getMessage());
        }
        return "redirect:/ships";
    }

    @PostMapping("/dashboard/routes/add")
    public String addRoute(@ModelAttribute Route route, RedirectAttributes redirectAttributes) {
        try {
            routeService.saveRoute(route);
            redirectAttributes.addFlashAttribute("success", "Route added successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding route: " + e.getMessage());
        }
        return "redirect:/dashboard/routes";
    }

    @GetMapping("/dashboard/routes/delete/{id}")
    public String deleteRoute(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            routeService.deleteRoute(id);
            redirectAttributes.addFlashAttribute("success", "Route deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting route: " + e.getMessage());
        }
        return "redirect:/dashboard/routes";
    }
}