package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StandardDTO;
import com.example.schoolmanagement.exception.BadRequestException;
import com.example.schoolmanagement.exception.ResourceNotFoundException;
import com.example.schoolmanagement.model.School;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StandardService {

    @Autowired
    private StandardRepository standardRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private AuthenticationService authentication;


    @Transactional
    public ResponseDTO create(final StandardDTO standardDTO) {
        if (standardDTO==null){
            return ResponseDTO.builder()
                    .message("StandardDTO is null")
                    .statusValue(HttpStatus.BAD_REQUEST.name())
                    .build();
        }
        final School school = this.schoolRepository.findById(standardDTO.getSchoolId()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        if (school == null) {
            return ResponseDTO.builder()
                    .message(Constants.DATA_NOT_FOUND)
                    .statusValue(HttpStatus.BAD_REQUEST.name())
                    .build();
        }

        final Standard standard = Standard.builder().name(standardDTO.getStandardName()).fees(standardDTO.getFees()).school(school).createdBy(authentication.getUserName()).updatedBy(authentication.getUserName()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.standardRepository.save(standard)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.standardRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveById(final String id) {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.standardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND))).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.standardRepository.existsById(id)) {
            throw new ResourceNotFoundException(Constants.DATA_NOT_FOUND);
        }
        this.standardRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.REMOVED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final StandardDTO standardDTO) {
        if (standardDTO == null) {
            throw new ResourceNotFoundException(Constants.DATA_REQUIRED);
        }
        final Standard existingStandard = this.standardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        if (standardDTO.getFees() != null) {
            existingStandard.setFees(standardDTO.getFees());
        }
        if (standardDTO.getStandardName() != null) {
            existingStandard.setName(standardDTO.getStandardName());
        }
        existingStandard.setUpdatedBy(authentication.getUserName());
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.standardRepository.save(existingStandard)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveMaxFees() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.standardRepository.findMaxFees()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
