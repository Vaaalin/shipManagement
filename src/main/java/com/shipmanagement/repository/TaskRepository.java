package com.shipmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipmanagement.model.Task;
import com.shipmanagement.model.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByAssignedTo(User user);
    
    List<Task> findBySupervisor(User supervisor);
    
    List<Task> findByTaskType(String taskType);
    
    List<Task> findByStatus(String status);
}
