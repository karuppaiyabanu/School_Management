package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StandardDTO;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.model.School;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StandardService {
    private final StandardRepository standardRepository;
    private final SchoolRepository schoolRepository;

    public StandardService(final StandardRepository standardRepository, final SchoolRepository schoolRepository) {
        this.standardRepository = standardRepository;
        this.schoolRepository = schoolRepository;
    }

    @Transactional
    public ResponseDTO createStandard(final StandardDTO standardDTO) {

        final School school = this.schoolRepository.findById(standardDTO.getSchoolId()).orElseThrow(() -> new BadRequestServiceException("School not found"));
        school.setId(standardDTO.getSchoolId());
        final Standard standard = new Standard();
        standard.setName(standardDTO.getStandardName());
        standard.setFees(standardDTO.getFees());
        standard.setSchool(school);
        standard.setCreatedBy(standardDTO.getCreatedBy());
        standard.setUpdatedBy(standardDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.standardRepository.save(standard), HttpStatus.CREATED.getReasonPhrase());
    }

    public ResponseDTO retrieveStandard() {
        return new ResponseDTO(Constants.SUCCESS, this.standardRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveStandardById(final String id) {
        return new ResponseDTO(Constants.SUCCESS, this.standardRepository.findById(id).orElseThrow(() -> new BadRequestServiceException("Standard not found")), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO deletedStandardById(final String id) {
        if (!this.standardRepository.existsById(id)) {
            throw new BadRequestServiceException("Standard id not found");
        }
        this.standardRepository.deleteById(id);
        return new ResponseDTO(Constants.DELETED, id, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updatedStandardById(final String id, final StandardDTO standardDTO) {

        final Standard existingStandard = this.standardRepository.findById(id).
                orElseThrow(() -> new BadRequestServiceException("Standard not found"));
        final School school = new School();
        school.setId(standardDTO.getSchoolId());
        if (standardDTO.getFees() != null) {
            existingStandard.setFees(standardDTO.getFees());
        }
        if (standardDTO.getStandardName() != null) {
            existingStandard.setName(standardDTO.getStandardName());
        }
        if (standardDTO.getSchoolId() != null) {
            existingStandard.setSchool(school);
        }
        if (standardDTO.getUpdatedBy() != null) {
            existingStandard.setUpdatedBy(standardDTO.getUpdatedBy());
        }

        return new ResponseDTO(Constants.SUCCESS, this.standardRepository.save(existingStandard), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO findMaxFees() {
        return new ResponseDTO(Constants.RETRIEVED, this.standardRepository.findMaxFees(), HttpStatus.OK.getReasonPhrase());
    }

}
