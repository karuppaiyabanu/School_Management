package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolDTO;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.model.School;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {
    private  final  SchoolRepository schoolRepository;

    public SchoolService(final SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Transactional
    public ResponseDTO createSchool(final SchoolDTO schoolDTO) {
        final School school = new School();
        school.setName(schoolDTO.getName());
        school.setAddress(schoolDTO.getAddress());
        school.setPhone(schoolDTO.getPhone());
        school.setCreatedBy(schoolDTO.getCreatedBy());
        school.setUpdatedBy(schoolDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.schoolRepository.save(school), HttpStatus.CREATED.getReasonPhrase());
    }

    public ResponseDTO retrieveSchool() {
        return new ResponseDTO(Constants.SUCCESS, this.schoolRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveSchoolById(final String schoolId) {
        return new ResponseDTO(Constants.SUCCESS, this.schoolRepository.findById(schoolId).orElseThrow(() -> new BadRequestServiceException("School not found")), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO removeSchoolById(final String schoolId) {
        if (!this.schoolRepository.existsById(schoolId)) {
            throw new BadRequestServiceException("school id not found");
        }
        this.schoolRepository.deleteById(schoolId);
        return new ResponseDTO("School Successfully deleted", schoolId, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateSchoolById(final String id, final SchoolDTO schoolDTO) {
       final School existingSchool = this.schoolRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceException("School not found"));

        if (schoolDTO.getName() != null) {
            existingSchool.setName(schoolDTO.getName());
        }
        if (schoolDTO.getAddress() != null) {
            existingSchool.setAddress(schoolDTO.getAddress());
        }
        if (schoolDTO.getPhone() != null) {
            existingSchool.setPhone(schoolDTO.getPhone());
        }
        if (schoolDTO.getUpdatedBy() != null) {
            existingSchool.setUpdatedBy(schoolDTO.getUpdatedBy());
        }
        return new ResponseDTO(Constants.SUCCESS, this.schoolRepository.save(existingSchool), HttpStatus.OK.getReasonPhrase());
    }


    private SchoolDTO convertToSchoolDTO(final School school) {
        SchoolDTO schoolDTO = new SchoolDTO();
        schoolDTO.setName(school.getName());
        schoolDTO.setAddress(school.getAddress());
        schoolDTO.setPhone(school.getPhone());
        schoolDTO.setCreatedBy(school.getCreatedBy());
        schoolDTO.setUpdatedBy(school.getUpdatedBy());
        return schoolDTO;
    }

    @Transactional
    public Page<SchoolDTO> searchSchool(String search, Integer page, Integer size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 5, sort);
        final Page<School> school = this.schoolRepository.searchSchool(search, pageable);
        if (school.isEmpty()) {
            throw new BadRequestServiceException("No data found for the given search criteria");
        }
        return school.map(this::convertToSchoolDTO);
    }

}
