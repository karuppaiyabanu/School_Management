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
    private final SubjectRepository subjectRepository;

    public ExamService(final ExamRepository examRepository, final StandardRepository standardRepository, final SubjectRepository subjectRepository) {
        this.examRepository = examRepository;
        this.standardRepository = standardRepository;
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public ResponseDTO create(final ExamDTO examDTO) {
        final Standard standard = this.standardRepository.findById(examDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));
        standard.setId(examDTO.getStandardId());
        final Subject subject = this.subjectRepository.findById(examDTO.getSubjectId()).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));
        subject.setId(examDTO.getSubjectId());
        final Exam exam = Exam.builder().standard(standard).subject(subject).name(examDTO.getName()).date(examDTO.getDate()).createdBy(examDTO.getCreatedBy()).updatedBy(examDTO.getUpdatedBy()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(exam).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.examRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveById(final String id) {
        final Exam exam = this.examRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(exam).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO update(final String id, final ExamDTO examDTO) {
        final Exam existingExam = this.examRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));
        if (examDTO.getDate() != null) {
            existingExam.setDate(examDTO.getDate());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(existingExam).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
