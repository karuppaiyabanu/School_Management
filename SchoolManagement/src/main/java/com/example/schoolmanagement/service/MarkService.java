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
import java.util.List;

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
    public ResponseDTO createMark(final MarkDTO markDTO) {
        final Student student = studentRepository.findById(markDTO.getStudentId()).orElseThrow(() -> new BadRequestServiceException("student not found"));
        student.setId(markDTO.getStudentId());
        Exam exam = examRepository.findById(markDTO.getExamId()).orElseThrow(() -> new BadRequestServiceException("Exam  not found"));
        exam.setId(markDTO.getExamId());
        final Mark mark = new Mark();
        mark.setStudent(student);
        mark.setMark(markDTO.getMark());
        mark.setExam(exam);
        return new ResponseDTO(Constants.CREATED, this.markRepository.save(mark), HttpStatus.CREATED.getReasonPhrase());
    }

    public ResponseDTO retrieveMarkForStudent(final String studentId) {
        List<Mark> marks = markRepository.findHighestMarkByStudent(studentId);
        Mark highestMark = marks.isEmpty() ? null : marks.get(0);
        return new ResponseDTO(
                Constants.RETRIEVED,
                highestMark != null ? highestMark : "No marks found for this student",
                HttpStatus.OK.getReasonPhrase()
        );
    }



}
