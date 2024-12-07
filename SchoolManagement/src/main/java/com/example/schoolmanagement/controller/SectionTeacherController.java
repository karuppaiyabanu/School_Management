package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionTeacherDTO;
import com.example.schoolmanagement.service.SectionTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/assign-teachers")
public class SectionTeacherController {

    @Autowired
    private SectionTeacherService sectionTeacherService;

    @PostMapping("/assign")
    public ResponseDTO create(@RequestBody final SectionTeacherDTO sectionTeacherDTO) {
        return this.sectionTeacherService.create(sectionTeacherDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieve() {
        return this.sectionTeacherService.retrieve();
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@RequestBody final SectionTeacherDTO sectionTeacherDTO, @PathVariable("id") final String id) {
        return this.sectionTeacherService.update(id, sectionTeacherDTO);
    }

}
