package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SearchFilterResponse;
import com.example.schoolmanagement.dto.SubjectDTO;
import com.example.schoolmanagement.exception.ResourceNotFoundException;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.model.Subject;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.SubjectRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StandardRepository standardRepository;
    @Autowired
    private AuthenticationService authentication;

    @Transactional
    public ResponseDTO create(final SubjectDTO subjectDTO) {
        final Standard standard = this.standardRepository.findById(subjectDTO.getStandard()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final Subject subject = Subject.builder().standard(standard).name(subjectDTO.getName()).createdBy(authentication.getUserName()).updatedBy(authentication.getUserName()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.subjectRepository.save(subject)).statusValue(HttpStatus.CREATED.name()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.subjectRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final SubjectDTO subjectDTO) {
        if (subjectDTO == null) {
            throw new ResourceNotFoundException(Constants.DATA_REQUIRED);
        }
        final Subject existingSubject = this.subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        if (subjectDTO.getName() != null) {
            existingSubject.setName(subjectDTO.getName());
        }
        existingSubject.setUpdatedBy(authentication.getUserName());
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.subjectRepository.save(existingSubject)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO remove(final String id) {
        if (!this.subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException(Constants.DATA_NOT_FOUND);
        }
        this.subjectRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.REMOVED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO search(final String school, final String subject, final String range, final Boolean lesserThan) {

       final List<Object> resultList = this.subjectRepository.search(school, subject, range, lesserThan);
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(resultList).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }
}