package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.TeacherDTO;
import com.example.schoolmanagement.service.TeacherService;
import com.example.schoolmanagement.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/create")
    public ResponseDTO createTeacher(@RequestBody final TeacherDTO teacherDTO) {
        return this.teacherService.create(teacherDTO);
    }

    @GetMapping("/retrieveById/{id}")
    public ResponseDTO retrieveTeacherById(@PathVariable("id") final String teacherId) {
        return this.teacherService.retrieveById(teacherId);
    }

    @GetMapping("/retrieve")
    public ResponseDTO getAllTeacher() {
        return this.teacherService.retrieve();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO deleteTeacher(@PathVariable("id") final String id) {
        return this.teacherService.remove(id);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateTeacher(@PathVariable("id") final String id, @RequestBody final TeacherDTO teacherDTO) {
        return this.teacherService.update(id, teacherDTO);
    }
   @GetMapping("/")
    public  ResponseDTO search(@RequestParam("school")String schoolName,@RequestParam("standard")String standardName,@RequestParam("subject")String subjectName,@RequestParam("lesserThan")final boolean lesserThan){
        return this.teacherService.search(schoolName,standardName,subjectName,lesserThan);
   }
}
