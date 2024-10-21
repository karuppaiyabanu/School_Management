package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.TeacherDTO;
import com.example.schoolmanagement.service.TeacherService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    private TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/create")
    public ResponseDTO createTeacher(@RequestBody final TeacherDTO teacherDTO) {
        return this.teacherService.createTeacher(teacherDTO);
       }


    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieveTeacherById(@PathVariable("id") final String teacherId) {
       return  this.teacherService.retrieveTeacherById(teacherId);
    }

    @GetMapping("/retrieve")
    public ResponseDTO getAllTeacher() {
        return  this.teacherService.retrieveTeacher();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteTeacher(@PathVariable("id") final String id) {
        return  this.teacherService.deleteTeacherById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateTeacher(@PathVariable("id") final String id, @RequestBody final TeacherDTO teacherDTO) {
        return  this.teacherService.updateTeacher(id,teacherDTO);
    }


}
