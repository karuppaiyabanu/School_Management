package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StandardDTO;
import com.example.schoolmanagement.service.StandardService;
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
@RequestMapping("/api/v1/standards")
public class StandardController {

    @Autowired
    private StandardService standardService;

    @PostMapping("/create")
    public ResponseDTO createStandard(@RequestBody final StandardDTO standardDTO) {
        return this.standardService.create(standardDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO getAllStandard() {
        return this.standardService.retrieve();
    }

    @GetMapping("/retrieveById/{id}")
    public ResponseDTO getStandardById(@PathVariable("id") final String id) {
        return this.standardService.retrieveById(id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO deleteStandardById(@PathVariable("id") final String id) {
        return this.standardService.remove(id);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateStandardById(@RequestBody final StandardDTO standardDTO, @PathVariable("id") final String id) {
        return this.standardService.update(id, standardDTO);
    }

    @GetMapping("/max-fees")
    public ResponseDTO findMaxFees() {
        return this.standardService.retrieveMaxFees();
    }

}
