package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.MarkDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.service.MarkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mark")
public class MarkController {
    private final MarkService markService;

    private MarkController(final MarkService markService) {
        this.markService = markService;
    }

    @PostMapping("/create")
    public ResponseDTO createMark(@RequestBody final MarkDTO markDTO) {
        return this.markService.createMark(markDTO);
    }

    @GetMapping("/highest/{studentId}")
    public  ResponseDTO findStudentMarks(@PathVariable ("studentId") String id){
        return this.markService.retrieveMarkForStudent(id);
    }


}
