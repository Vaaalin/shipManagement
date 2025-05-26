// In RouteController.java
package com.shipmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shipmanagement.model.Route;
import com.shipmanagement.service.RouteService;

@Controller
@RequestMapping("/routes")
public class RouteController {
    @Autowired
    private RouteService routeService;
    
    @GetMapping
    public String listRoutes(Model model) {
        model.addAttribute("routes", routeService.getAllRoutes());
        model.addAttribute("newRoute", new Route());
        return "routes/list";
    }
    
    @PostMapping
    public String addRoute(@ModelAttribute Route route) {
        routeService.saveRoute(route);
        return "redirect:/routes";
    }
}