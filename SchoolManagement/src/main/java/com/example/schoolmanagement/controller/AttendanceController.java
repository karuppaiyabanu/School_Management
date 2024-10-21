package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.AttendanceDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.service.AttendanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
     private final AttendanceService attendanceService;

     private AttendanceController(AttendanceService attendanceService){
         this.attendanceService=attendanceService;
     }

     @PostMapping("/create")
    public ResponseDTO createAttendance(@RequestBody  final AttendanceDTO attendanceDTO) {
      return  this.attendanceService.createAttendance(attendanceDTO);
     }

     @GetMapping("/retrieve")
    public ResponseDTO getAllAttendance() {
        return  this.attendanceService.getAllStudentsAttendance();
     }

}
