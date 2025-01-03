package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SubjectDTO;
import com.example.schoolmanagement.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final SubjectDTO subjectDTO) {
        return this.subjectService.create(subjectDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieve() {
        return this.subjectService.retrieve();
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@RequestBody final SubjectDTO subjectDTO, @PathVariable("id") final String id) {
        return this.subjectService.update(id, subjectDTO);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable("id") final String id) {
        return this.subjectService.remove(id);
    }

    @GetMapping("/search")
    public ResponseDTO search(@RequestParam("school") final String school, @RequestParam("subject") final String subject
            , @RequestParam("range") final String range, @RequestParam("lesserThan") final Boolean lesserThan) {
        return this.subjectService.search(school, subject, range, lesserThan);
    }

}
