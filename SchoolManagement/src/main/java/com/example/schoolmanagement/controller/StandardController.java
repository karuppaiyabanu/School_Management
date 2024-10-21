package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StandardDTO;
import com.example.schoolmanagement.service.StandardService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/standard")
public class StandardController {
    private final StandardService standardService;

    private StandardController(final StandardService standardService) {
        this.standardService = standardService;
    }

    @PostMapping("/create")
    public ResponseDTO createStandard(@RequestBody final StandardDTO standardDTO) {
        return this.standardService.createStandard(standardDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO getAllStandard() {
        return this.standardService.retrieveStandard();
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO getStandardById(@PathVariable("id") final String id) {
        return this.standardService.retrieveStandardById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteStandardById(@PathVariable("id") final String id) {
        return this.standardService.deletedStandardById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateStandardById(@RequestBody final StandardDTO standardDTO, @PathVariable("id") final String id) {
        return this.standardService.updatedStandardById(id, standardDTO);
    }

    @GetMapping("/max_fees")
    public  ResponseDTO findMaxFees(){
        return this.standardService.findMaxFees();
    }

}
