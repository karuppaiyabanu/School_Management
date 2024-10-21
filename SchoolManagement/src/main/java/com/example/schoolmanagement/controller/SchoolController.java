package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolDTO;
import com.example.schoolmanagement.service.SchoolService;
import com.example.schoolmanagement.util.Constants;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/school")
public class SchoolController {

    private final SchoolService schoolService;

    private SchoolController(final SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final SchoolDTO schoolDTO) {
        return this.schoolService.createSchool(schoolDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieve() {
        return this.schoolService.retrieveSchool();
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final String id) {
        return this.schoolService.retrieveSchoolById(id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO removeById(@PathVariable("id") final String id) {
        return this.schoolService.removeSchoolById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateById(@RequestBody final SchoolDTO schoolDTO, @PathVariable("id") final String id) {
        return this.schoolService.updateSchoolById(id, schoolDTO);
    }

    @GetMapping("/search")
    public ResponseDTO searchSchool(@RequestParam(required = false) String search,
                                    @RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer size,
                                    @RequestParam(required = false, defaultValue = "name") String sortField,
                                    @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        Page<SchoolDTO> SchoolDTOS = this.schoolService.searchSchool(search, page, size, sortField, sortDirection);
        return new ResponseDTO(Constants.RETRIEVED, SchoolDTOS, HttpStatus.OK.getReasonPhrase());
    }


}
