package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolDTO;
import com.example.schoolmanagement.service.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(final SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO create(@RequestBody final SchoolDTO schoolDTO) {
        return this.schoolService.create(schoolDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieve() {
        return this.schoolService.retrieve();
    }

    @GetMapping("/retrieveById/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final String id) {
        return this.schoolService.retrieveById(id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable("id") final String id) {
        return this.schoolService.remove(id);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateById(@RequestBody final SchoolDTO schoolDTO, @PathVariable("id") final String id) {
        return this.schoolService.update(id, schoolDTO);
    }

}
