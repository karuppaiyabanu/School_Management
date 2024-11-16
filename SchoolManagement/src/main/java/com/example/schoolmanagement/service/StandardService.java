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
    public ResponseDTO create(final StandardDTO standardDTO) {
        final School school = this.schoolRepository.findById(standardDTO.getSchoolId()).orElseThrow(() -> new BadRequestServiceException(Constants.NOT_FOUND));
        school.setId(standardDTO.getSchoolId());
        final Standard standard = Standard.builder().name(standardDTO.getStandardName()).fees(standardDTO.getFees()).school(school).createdBy(standardDTO.getCreatedBy()).updatedBy(standardDTO.getUpdatedBy()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.standardRepository.save(standard)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.standardRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveById(final String id) {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.standardRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.NOT_FOUND))).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.standardRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.NOT_FOUND);
        }
        this.standardRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.REMOVED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final StandardDTO standardDTO) {
        final Standard existingStandard = this.standardRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.NOT_FOUND));
        if (standardDTO.getFees() != null) {
            existingStandard.setFees(standardDTO.getFees());
        }
        if (standardDTO.getStandardName() != null) {
            existingStandard.setName(standardDTO.getStandardName());
        }
        if (standardDTO.getUpdatedBy() != null) {
            existingStandard.setUpdatedBy(standardDTO.getUpdatedBy());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.standardRepository.save(existingStandard)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveMaxFees() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.standardRepository.findMaxFees()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
