package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ExamDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.exception.ResourceNotFoundException;
import com.example.schoolmanagement.model.Exam;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.model.Subject;
import com.example.schoolmanagement.repository.ExamRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.SubjectRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private StandardRepository standardRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private AuthenticationService authentication;


    @Transactional
    public ResponseDTO create(final ExamDTO examDTO) {
        final Standard standard = this.standardRepository.findById(examDTO.getStandardId()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final Subject subject = this.subjectRepository.findById(examDTO.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final Exam exam = Exam.builder().standard(standard).subject(subject).name(examDTO.getName()).date(examDTO.getDate()).createdBy(authentication.getUserName()).updatedBy(authentication.getUserName()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.examRepository.save(exam)).statusValue(HttpStatus.CREATED.name()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.examRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveById(final String id) {
        final Exam exam = this.examRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(exam).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO update(final String id, final ExamDTO examDTO) {
        if (examDTO == null) {
            throw new ResourceNotFoundException(Constants.DATA_NOT_FOUND);
        }
        final Exam existingExam = this.examRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        if (examDTO.getDate() != null) {
            existingExam.setDate(examDTO.getDate());
        }
        existingExam.setUpdatedBy(authentication.getUserName());
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.examRepository.save(existingExam)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
