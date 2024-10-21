package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ExamDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.service.ExamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exam")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/create")
    public ResponseDTO createExam(@RequestBody ExamDTO examDTO) {
        return this.examService.createExam(examDTO);
    }

    @GetMapping("/retrieve")
    public  ResponseDTO retrieveExam(){
        return this.examService.retrieveExam();
    }
    @GetMapping("/retrieve/{id}")
    public  ResponseDTO retrieveExamById(@PathVariable("id") final  String id){
        return this.examService.retrieveExamById(id);
    }
    @PutMapping("/update/{id}")
    public  ResponseDTO updateExam(@PathVariable("id") final String id, final  ExamDTO examDTO ){
        return  this.examService.updateExam(id,examDTO);
    }
}
