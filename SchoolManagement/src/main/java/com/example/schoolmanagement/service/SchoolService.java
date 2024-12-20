package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolDTO;
import com.example.schoolmanagement.exception.ResourceNotFoundException;
import com.example.schoolmanagement.model.School;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import com.example.schoolmanagement.util.UtilService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Optional;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private AuthenticationService authentication;


    @Transactional
    public ResponseDTO create(final SchoolDTO schoolDTO) {
        validateEmail(schoolDTO.getEmail());
        final School school = School.builder().name(schoolDTO.getName()).address(schoolDTO.getAddress()).phone(schoolDTO.getPhone()).createdBy(authentication.getUserName()).updatedBy(authentication.getUserName()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.schoolRepository.save(school)).statusValue(HttpStatus.CREATED.name()).build();
    }

    public void validateEmail(String email) {
        if (!UtilService.emailValidation(email)) {
            throw new InputMismatchException(Constants.REGEX_MISS_MATCH);
        }
        Optional<School> school = this.schoolRepository.findByEmail(email);
        if (school.isPresent()) {
            throw new ResourceNotFoundException(Constants.EXISTING_EMAIL);
        }
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.schoolRepository.findAll()).statusValue(HttpStatus.OK.name()).build();
    }

    public ResponseDTO retrieveById(final String id) {
        Optional<School> findSchool = Optional.ofNullable(this.schoolRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND)));
        return ResponseDTO.builder().message(Constants.SUCCESS).data(findSchool).statusValue(HttpStatus.OK.name()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.schoolRepository.existsById(id)) {
            throw new ResourceNotFoundException(Constants.DATA_NOT_FOUND);
        }
        this.schoolRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.REMOVED).data(id).statusValue(HttpStatus.OK.name()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final SchoolDTO schoolDTO) {
        final School existingSchool = this.schoolRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        if (schoolDTO.getName() != null) {
            existingSchool.setName(schoolDTO.getName());
        }
        if (schoolDTO.getAddress() != null) {
            existingSchool.setAddress(schoolDTO.getAddress());
        }
        if (schoolDTO.getEmail() != null) {
            validateEmail(schoolDTO.getEmail());
            existingSchool.setEmail(schoolDTO.getEmail());
        }
        if (schoolDTO.getPhone() != null) {
            existingSchool.setPhone(schoolDTO.getPhone());
        }
        existingSchool.setUpdatedBy(authentication.getUserName());
        return new ResponseDTO(Constants.UPDATED, this.schoolRepository.save(existingSchool), HttpStatus.OK.getReasonPhrase());
    }

}
