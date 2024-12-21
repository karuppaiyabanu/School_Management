package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.AttendanceDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final AttendanceDTO attendanceDTO) {
        return this.attendanceService.create(attendanceDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieve() {
        return this.attendanceService.retrieve();
    }

}
