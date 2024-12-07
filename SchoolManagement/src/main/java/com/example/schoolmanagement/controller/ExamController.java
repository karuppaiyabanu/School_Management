package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ExamDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final ExamDTO examDTO) {
        return this.examService.create(examDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieve() {
        return this.examService.retrieve();
    }

    @GetMapping("/retrieveById/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final String id) {
        return this.examService.retrieveById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable("id") final String id, @RequestBody final ExamDTO examDTO) {
        return this.examService.update(id, examDTO);
    }

}
