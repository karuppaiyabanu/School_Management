package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.MarkDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/marks")
public class MarkController {

    @Autowired
    private MarkService markService;

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final MarkDTO markDTO) {
        return this.markService.create(markDTO);
    }

    @GetMapping("/retrieve/{student-id}")
    public ResponseDTO retrieveStudentMark(@PathVariable("student-id") final String studentId) {
        return this.markService.retrieveStudentMark(studentId);
    }

}
