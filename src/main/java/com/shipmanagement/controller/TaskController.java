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

import com.shipmanagement.model.Task;
import com.shipmanagement.model.User;
import com.shipmanagement.repository.UserRepository;
import com.shipmanagement.service.TaskService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserRepository userRepository;
    
    @Autowired
    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }
    
    // Check if user is logged in and get their role
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("userId") != null;
    }
    
    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        return role != null && role.equals("ADMIN");
    }
    
    // View tasks - accessible to both admin and staff
    @GetMapping
    public String viewTasks(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        Long userId = (Long) session.getAttribute("userId");
        User currentUser = userRepository.findById(userId).orElse(null);
        
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        // Admin sees all tasks, staff sees only their assigned tasks
        List<Task> tasks;
        if (isAdmin(session)) {
            tasks = taskService.getAllTasks();
            model.addAttribute("allUsers", userRepository.findAll());
        } else {
            tasks = taskService.getTasksByAssignedTo(currentUser);
        }
        
        model.addAttribute("tasks", tasks);
        model.addAttribute("isAdmin", isAdmin(session));
        model.addAttribute("newTask", new Task());
        return "tasks/list";
    }
    
    // Add a new task - admin only
    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task, @RequestParam Long assignedToId, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session) || !isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You don't have permission to perform this action");
            return "redirect:/tasks";
        }
        
        // Set the supervisor (current admin user)
        Long adminId = (Long) session.getAttribute("userId");
        User admin = userRepository.findById(adminId).orElse(null);
        
        // Set the assigned staff member
        User assignedStaff = userRepository.findById(assignedToId).orElse(null);
        
        if (admin == null || assignedStaff == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid user assignment");
            return "redirect:/tasks";
        }
        
        task.setSupervisor(admin);
        task.setAssignedTo(assignedStaff);
        
        taskService.saveTask(task);
        redirectAttributes.addFlashAttribute("success", "Task assigned successfully");
        return "redirect:/tasks";
    }
    
    // Update task status - both admin and staff can update
    @PostMapping("/update")
    public String updateTask(@RequestParam Long taskId, @RequestParam String status, 
                           HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        Long userId = (Long) session.getAttribute("userId");
        User currentUser = userRepository.findById(userId).orElse(null);
        
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        Optional<Task> taskOpt = taskService.getTaskById(taskId);
        
        if (!taskOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Task not found");
            return "redirect:/tasks";
        }
        
        Task task = taskOpt.get();
        
        // Check if the user is admin or the assigned staff member
        if (!isAdmin(session) && (task.getAssignedTo() == null || !task.getAssignedTo().getId().equals(userId))) {
            redirectAttributes.addFlashAttribute("error", "You don't have permission to update this task");
            return "redirect:/tasks";
        }
        
        task.setStatus(status);
        taskService.saveTask(task);
        redirectAttributes.addFlashAttribute("success", "Task status updated successfully");
        return "redirect:/tasks";
    }
    
    // Delete task - admin only
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session) || !isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You don't have permission to perform this action");
            return "redirect:/tasks";
        }
        
        taskService.deleteTask(id);
        redirectAttributes.addFlashAttribute("success", "Task deleted successfully");
        return "redirect:/tasks";
    }
}
