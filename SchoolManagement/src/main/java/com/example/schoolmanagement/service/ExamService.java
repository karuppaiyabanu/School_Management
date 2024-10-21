package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ExamDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.model.Exam;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.model.Subject;
import com.example.schoolmanagement.repository.ExamRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.SubjectRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ExamService {
    private final ExamRepository examRepository;
    private final StandardRepository standardRepository;
    private  final SubjectRepository subjectRepository;
    public ExamService(final ExamRepository examRepository, final StandardRepository standardRepository,final SubjectRepository subjectRepository) {
        this.examRepository = examRepository;
        this.standardRepository = standardRepository;
        this.subjectRepository=subjectRepository;
    }

    @Transactional
    public ResponseDTO createExam(final ExamDTO examDTO) {
        Standard standard = this.standardRepository.findById(examDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceException("Standard not found"));
        standard.setId(examDTO.getStandardId());
        Subject subject=this.subjectRepository.findById(examDTO.getSubjectId()).orElseThrow(() -> new BadRequestServiceException("Subject not found"));
        Exam exam = new Exam();
        exam.setStandard(standard);
        exam.setSubject(subject);
        exam.setName(examDTO.getName());
        exam.setDate(examDTO.getDate());
        exam.setCreatedBy(examDTO.getCreatedBy());
        exam.setUpdatedBy(examDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.examRepository.save(exam), HttpStatus.CREATED.getReasonPhrase());
    }

    public ResponseDTO retrieveExam() {
        return new ResponseDTO(Constants.SUCCESS, this.examRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveExamById(final String id) {
        return new ResponseDTO(Constants.SUCCESS, this.examRepository.findById(id).orElseThrow(() -> new BadRequestServiceException("Exam not found")), HttpStatus.OK.getReasonPhrase());
    }

    public  ResponseDTO updateExam(final  String id,final  ExamDTO examDTO){
       final Exam existingExam=this.examRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceException("Exam not found"));
       if (examDTO.getDate()!=null){
            existingExam.setDate(examDTO.getDate());
       }
        this.examRepository.save(existingExam);
        return new ResponseDTO(Constants.SUCCESS, existingExam, HttpStatus.OK.getReasonPhrase());

    }
}
