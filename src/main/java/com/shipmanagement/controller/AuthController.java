package com.shipmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shipmanagement.model.User;
import com.shipmanagement.repository.UserRepository;
import com.shipmanagement.service.PortService;
import com.shipmanagement.service.ShipService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final ShipService shipService;
    private final PortService portService;

    @Autowired
    public AuthController(UserRepository userRepository, ShipService shipService, PortService portService) {
        this.userRepository = userRepository;
        this.shipService = shipService;
        this.portService = portService;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("userId") != null) {
            String role = (String) session.getAttribute("userRole");
            model.addAttribute("userRole", role);
            return "home";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                      @RequestParam String password,
                      HttpSession session,
                      RedirectAttributes redirectAttributes) {
        
        User user = userRepository.findByUsername(username);
        
        if (user != null && user.getPassword().equals(password)) {
            // Store user info in session
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userRole", user.getRole());
            
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        
        String role = (String) session.getAttribute("userRole");
        String username = (String) session.getAttribute("username");
        boolean isAdmin = role != null && role.equals("ADMIN");
        
        // Add data that was previously provided by WebController
        model.addAttribute("ships", shipService.getAllShips());
        model.addAttribute("ports", portService.getAllPorts());
        
        // Add user information
        model.addAttribute("userRole", role);
        model.addAttribute("username", username);
        model.addAttribute("isAdmin", isAdmin);
        
        return "dashboard";
    }
    
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
    
    @PostMapping("/signup")
    public String processSignup(@ModelAttribute User user, 
                              @RequestParam String confirmPassword,
                              RedirectAttributes redirectAttributes) {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("error", "Username already exists");
            return "redirect:/signup";
        }
        
        // Check if passwords match
        if (!user.getPassword().equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match");
            return "redirect:/signup";
        }
        
        // Set default role to STAFF
        user.setRole("STAFF");
        
        // Save user
        userRepository.save(user);
        
        redirectAttributes.addFlashAttribute("success", "Account created successfully. Please log in.");
        return "redirect:/login";
    }
}