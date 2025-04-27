package com.uts.casee.attendance.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uts.casee.attendance.model.Attendance;
import com.uts.casee.attendance.service.AttendanceService;


@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {
    
    private static final Logger log = LoggerFactory.getLogger(AttendanceController.class);

    @Autowired
    private AttendanceService attendanceService;

     // Endpoint untuk mengambil semua Attendance
    @GetMapping
    public List<Attendance> getAllAttendance() {
        log.info("GET /api/attendances accessed");
        return attendanceService.getAll();
    }

    // Endpoint untuk mengambil Attendance berdasarkan id
    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long id) {
        log.info("GET /api/attendances/{} accessed", id);
            return attendanceService.getById(id)
                .map(attendance -> ResponseEntity.ok().body(attendance))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint membuat Attendance baru
    @PostMapping
    public Attendance createAttendance(@RequestBody Attendance attendance) {
        log.info("POST /api/attendances accessed with body: {}", attendance);
            return attendanceService.createAttendance(attendance);
    }

     // Endpoint untuk update Attendance yang sudah ada
     @PutMapping("/{id}")
     public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id, @RequestBody Attendance attendanceDetails) {
         log.info("PUT /api/attendances/{} accessed with body: {}", id, attendanceDetails);
 
         try {
             Attendance updateAttendance = attendanceService.updateAttendance(id, attendanceDetails);
             return ResponseEntity.ok(updateAttendance);
         } catch (RuntimeException e) {
             log.warn("PUT /api/attendances/{} failed: {}", id, e.getMessage());
             return ResponseEntity.notFound().build();
         }
     }

     // Endpoint DELETE item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttendance(@PathVariable Long id) {
    log.info("DELETE /api/attendances/{} accessed", id);
    Map<String, String> response = new HashMap<>();
        try {
            attendanceService.deleteAttendance(id);
            response.put("message", "Attendance berhasil dihapus");
            return ResponseEntity.ok("Attendance deleted successfully");
        } catch (RuntimeException e) {
            response.put("message", "Attendance tidak ditemukan dengan id" + id);
            log.warn("DELETE /api/attendances/{} failed: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
 

}
