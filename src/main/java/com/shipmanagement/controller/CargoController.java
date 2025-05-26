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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shipmanagement.model.Cargo;
import com.shipmanagement.model.User;
import com.shipmanagement.repository.UserRepository;
import com.shipmanagement.service.CargoService;
import com.shipmanagement.service.ShipService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cargo")
public class CargoController {

    private final CargoService cargoService;
    private final ShipService shipService;
    private final UserRepository userRepository;

    @Autowired
    public CargoController(CargoService cargoService, ShipService shipService, UserRepository userRepository) {
        this.cargoService = cargoService;
        this.shipService = shipService;
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
    public String viewCargo(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        Long userId = (Long) session.getAttribute("userId");
        User currentUser = userRepository.findById(userId).orElse(null);
        String userRole = (String) session.getAttribute("userRole");
        boolean isAdmin = userRole != null && userRole.equals("ADMIN");

        List<Cargo> cargoList = cargoService.getAllCargo();
        model.addAttribute("cargoList", cargoList);
        model.addAttribute("isAdmin", isAdmin);

        return "cargo/list";
    }

    @GetMapping("/add")
    public String showAddCargoForm(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        // Only admins can add new cargo
        if (!isAdmin(session)) {
            return "redirect:/cargo";
        }

        model.addAttribute("cargo", new Cargo());
        model.addAttribute("ships", shipService.getAllShips());
        model.addAttribute("isAdmin", true);

        return "cargo/add";
    }

    @PostMapping("/add")
    public String addCargo(@ModelAttribute Cargo cargo, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        // Only admins can add new cargo
        if (!isAdmin(session)) {
            return "redirect:/cargo";
        }

        try {
            Long userId = (Long) session.getAttribute("userId");
            User currentUser = userRepository.findById(userId).orElse(null);

            if (currentUser != null) {
                cargo.setLastUpdatedBy(currentUser);
                cargo.setLastUpdatedDate(new java.util.Date());
                cargoService.saveCargo(cargo);
                redirectAttributes.addFlashAttribute("success", "Cargo added successfully");
            } else {
                redirectAttributes.addFlashAttribute("error", "User not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding cargo: " + e.getMessage());
        }

        return "redirect:/cargo";
    }

    @GetMapping("/edit/{id}")
    public String showEditCargoForm(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        try {
            Optional<Cargo> cargoOpt = cargoService.getCargoById(id);
            if (cargoOpt.isPresent()) {
                model.addAttribute("cargo", cargoOpt.get());
                model.addAttribute("ships", shipService.getAllShips());
                
                String userRole = (String) session.getAttribute("userRole");
                boolean isAdmin = userRole != null && userRole.equals("ADMIN");
                model.addAttribute("isAdmin", isAdmin);
                
                return "cargo/edit";
            } else {
                redirectAttributes.addFlashAttribute("error", "Cargo not found");
                return "redirect:/cargo";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error retrieving cargo: " + e.getMessage());
            return "redirect:/cargo";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateCargo(@PathVariable Long id, @ModelAttribute Cargo cargo, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        try {
            Long userId = (Long) session.getAttribute("userId");
            cargoService.updateCargoDetails(id, cargo, userId);
            redirectAttributes.addFlashAttribute("success", "Cargo updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating cargo: " + e.getMessage());
        }

        return "redirect:/cargo";
    }

    @GetMapping("/delete/{id}")
    public String deleteCargo(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        // Only admins can delete cargo
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You don't have permission to delete cargo");
            return "redirect:/cargo";
        }

        try {
            cargoService.deleteCargo(id);
            redirectAttributes.addFlashAttribute("success", "Cargo deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting cargo: " + e.getMessage());
        }

        return "redirect:/cargo";
    }
}
