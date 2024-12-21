package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionDTO;
import com.example.schoolmanagement.service.SectionService;
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
@RequestMapping("/api/v1/sections")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final SectionDTO sectionDTO) {
        return this.sectionService.create(sectionDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieve() {
        return this.sectionService.retrieve();
    }

    @GetMapping("/retrieveById/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final String id) {
        return this.sectionService.retrieveById(id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable("id") final String id) {
        return this.sectionService.remove(id);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable("id") final String id, @RequestBody final SectionDTO sectionDTO) {
        return this.sectionService.update(id, sectionDTO);
    }

}
