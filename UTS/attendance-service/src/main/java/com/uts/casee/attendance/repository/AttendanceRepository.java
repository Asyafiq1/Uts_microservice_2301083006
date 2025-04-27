package com.uts.casee.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uts.casee.attendance.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
    
}
