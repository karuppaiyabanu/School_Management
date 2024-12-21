package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.MarkDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.exception.ResourceNotFoundException;
import com.example.schoolmanagement.model.Exam;
import com.example.schoolmanagement.model.Mark;
import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.repository.ExamRepository;
import com.example.schoolmanagement.repository.MarkRepository;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MarkService {

    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private AuthenticationService authentication;


    @Transactional
    public ResponseDTO create(final MarkDTO markDTO) {
        final Student student = this.studentRepository.findById(markDTO.getStudentId()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final Exam exam = this.examRepository.findById(markDTO.getExamId()).orElseThrow(() -> new ResourceNotFoundException(Constants.DATA_NOT_FOUND));
        final Mark mark = Mark.builder().student(student).exam(exam).mark(markDTO.getMark()).createdBy(authentication.getUserName()).updatedBy(authentication.getUserName()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.markRepository.save(mark)).statusValue(HttpStatus.CREATED.name()).build();
    }

    public ResponseDTO retrieveStudentMark(final String studentId) {
        Mark marks = this.markRepository.findMarkByStudent(studentId);
        if (marks != null) {
            return ResponseDTO.builder().message(Constants.RETRIEVED).data(marks).statusValue(HttpStatus.OK.getReasonPhrase()).build();
        }
        return ResponseDTO.builder().message(studentId + Constants.ID_DOES_NOT_EXIST).build();
    }
}
