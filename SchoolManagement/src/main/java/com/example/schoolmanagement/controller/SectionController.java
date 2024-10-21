package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionDTO;
import com.example.schoolmanagement.service.SectionService;
import com.example.schoolmanagement.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/section")
public class SectionController {
    private final SectionService sectionService;
    private SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/create")
    public ResponseDTO createSection(@RequestBody final SectionDTO sectionDTO) {
        return new ResponseDTO(Constants.CREATED, this.sectionService.createSection(sectionDTO), HttpStatus.CREATED.getReasonPhrase());
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieveSection() {
      return  this.sectionService.retrieveSection();
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieveSectionById(@PathVariable("id") final String id) {
      return  this.sectionService.retrieveSectionById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteSectionById(@PathVariable("id") final String id) {
       return this.sectionService.deleteSectionById(id);
    }

    @PutMapping("update/{id}")
    public ResponseDTO updateSection(@PathVariable ("id") final String id,@RequestBody final  SectionDTO sectionDTO){
        return new ResponseDTO(Constants.SUCCESS, this.sectionService.updateSection(id,sectionDTO), HttpStatus.OK.getReasonPhrase());
    }
}
