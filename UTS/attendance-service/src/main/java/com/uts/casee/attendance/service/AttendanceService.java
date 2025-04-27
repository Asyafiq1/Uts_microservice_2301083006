package com.uts.casee.attendance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uts.casee.attendance.model.Attendance;
import com.uts.casee.attendance.repository.AttendanceRepository;

@Service
public class AttendanceService {
     @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getAll() {
        return attendanceRepository.findAll();
    }

    public Optional<Attendance> getById(Long id) {
        return attendanceRepository.findById(id);
    }

    public Attendance createAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public Attendance updateAttendance(Long id, Attendance attendanceDetails) {
        Attendance attendance = attendanceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Attendance not foundd with id " + id));
        attendance.setEmployeeName(attendanceDetails.getEmployeeName());
        attendance.setDate(attendanceDetails.getDate());
        attendance.setStatus(attendanceDetails.getStatus());
        return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Attendance not found with id" + id));
        attendanceRepository.delete(attendance);
    }
}
