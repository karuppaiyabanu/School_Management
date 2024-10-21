package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.SectionTeacherDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.service.SectionTeacherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/assign")
public class SectionTeacherController {
    private  final SectionTeacherService sectionTeacherService;
    public SectionTeacherController(SectionTeacherService sectionTeacherService){
        this.sectionTeacherService = sectionTeacherService;
    }

    @PostMapping("/create")
    public ResponseDTO createSectionTeacher(@RequestBody SectionTeacherDTO sectionTeacherDTO) {
    return  this.sectionTeacherService.create(sectionTeacherDTO);
    }
    @GetMapping("/retrieve")
    public  ResponseDTO retrieveSectionTeacher(){
        return  this.sectionTeacherService.retrieveSectionTeacher();
    }
    @PutMapping("/update/{id}")
    public  ResponseDTO updateSectionTeacher(@RequestBody SectionTeacherDTO sectionTeacherDTO, @PathVariable("id") String id){
        return  this.sectionTeacherService.updateSectionTeacher(id,sectionTeacherDTO);
    }
}
