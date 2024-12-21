package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolDTO;
import com.example.schoolmanagement.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schools")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @PostMapping("/")
    public ResponseDTO create(@RequestBody final SchoolDTO schoolDTO) {
        return this.schoolService.create(schoolDTO);
    }

    @GetMapping("/")
    public ResponseDTO retrieve() {
        return this.schoolService.retrieve();
    }

    @GetMapping("/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final String id) {
        return this.schoolService.retrieveById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO remove(@PathVariable("id") final String id) {
        return this.schoolService.remove(id);
    }

    @PutMapping("/{id}")
    public ResponseDTO updateById(@RequestBody final SchoolDTO schoolDTO, @PathVariable("id") final String id) {
        return this.schoolService.update(id, schoolDTO);
    }

   @GetMapping("/schl")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public  String hi(){
       return "hii";
   }

}
