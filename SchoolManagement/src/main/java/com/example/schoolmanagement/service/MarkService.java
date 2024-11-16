package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.MarkDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.model.Exam;
import com.example.schoolmanagement.model.Mark;
import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.repository.ExamRepository;
import com.example.schoolmanagement.repository.MarkRepository;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MarkService {

    private final MarkRepository markRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;


    public MarkService(final MarkRepository markRepository, final StudentRepository studentRepository, final ExamRepository examRepository) {
        this.markRepository = markRepository;
        this.studentRepository = studentRepository;
        this.examRepository = examRepository;
    }

    @Transactional
    public ResponseDTO create(final MarkDTO markDTO) {
        final Student student = this.studentRepository.findById(markDTO.getStudentId()).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));
        student.setId(markDTO.getStudentId());
        final Exam exam = this.examRepository.findById(markDTO.getExamId()).orElseThrow(() -> new BadRequestServiceException(Constants.NO_DATA_FOUND));
        exam.setId(markDTO.getExamId());
        final Mark mark = Mark.builder().student(student).exam(exam).mark(markDTO.getMark()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.markRepository.save(mark)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveStudentMark(final String studentId) {
        Mark marks = this.markRepository.findMarkByStudent(studentId);
        if (marks != null) {
            return ResponseDTO.builder().message(Constants.RETRIEVED).data(marks).statusValue(HttpStatus.OK.getReasonPhrase()).build();
        }
        return ResponseDTO.builder().message(studentId + Constants.ID_DOES_NOT_EXIST).build();
    }
}
