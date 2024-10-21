package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SubjectDTO;
import com.example.schoolmanagement.service.SubjectService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    private final SubjectService subjectService;

    private SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/create")
    public ResponseDTO createSubject(@RequestBody final SubjectDTO subjectDTO) {
        return this.subjectService.createSubjectToStandard(subjectDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieveSubject() {
        return this.subjectService.retrieveSubject();
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateSubjectById(@RequestBody final SubjectDTO subjectDTO, @PathVariable("id") String id) {
        return this.subjectService.updateSubjectToStandard(id, subjectDTO);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO deleteSubjectById(@PathVariable("id") String id) {
        return this.subjectService.deleteSubjectById(id);
    }


}
