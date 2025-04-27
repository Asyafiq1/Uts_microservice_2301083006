package com.uts.casee.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uts.casee.task.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
}
