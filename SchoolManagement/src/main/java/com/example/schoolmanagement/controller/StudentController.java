package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StudentDTO;
import com.example.schoolmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public ResponseDTO createStudent(@RequestBody final StudentDTO studentDTO) {
        return this.studentService.create(studentDTO);
    }

    @GetMapping("/retrieveById/{id}")
    public ResponseDTO retrieveStudentById(@PathVariable("id") final String id) {
        return this.studentService.retrieveById(id);
    }

    @GetMapping("/retrieve")
    public ResponseDTO getAllStudent() {
        return this.studentService.retrieve();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO deleteStudentById(@PathVariable("id") final String id) {
        return this.studentService.remove(id);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateStudent(@PathVariable("id") String id, @RequestBody StudentDTO studentDTO) {
        return this.studentService.update(id, studentDTO);
    }
}
