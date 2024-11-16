package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolDTO;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.model.School;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private  final  JwtService jwtService;
    public SchoolService(final SchoolRepository schoolRepository,final JwtService jwtService) {
        this.schoolRepository = schoolRepository;
        this.jwtService=jwtService;
    }



    @Transactional
    public ResponseDTO create(final SchoolDTO schoolDTO) {
        final School school = School.builder()
                .name(schoolDTO.getName())
                .address(schoolDTO.getAddress())
                .phone(schoolDTO.getPhone())
                .createdBy(schoolDTO.getCreatedBy())
                .updatedBy(schoolDTO.getUpdatedBy()).build();
        return new ResponseDTO(Constants.CREATED, this.schoolRepository.save(school), HttpStatus.CREATED.name());
    }

    public ResponseDTO retrieve() {
        return new ResponseDTO(Constants.SUCCESS, this.schoolRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveById(final String schoolId) {
        return new ResponseDTO(Constants.SUCCESS, this.schoolRepository.findById(schoolId).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND)), HttpStatus.BAD_REQUEST.name());
    }

    @Transactional
    public ResponseDTO remove(final String schoolId) {
        if (!this.schoolRepository.existsById(schoolId)) {
            throw new BadRequestServiceException(Constants.NO_DATA_FOUND);
        }
        this.schoolRepository.deleteById(schoolId);
        return new ResponseDTO(Constants.REMOVED, schoolId, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final SchoolDTO schoolDTO) {
        final School existingSchool = this.schoolRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));

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
        return new ResponseDTO(Constants.UPDATED, this.schoolRepository.save(existingSchool), HttpStatus.OK.getReasonPhrase());
    }

}
