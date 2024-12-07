package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.MarkDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.model.Exam;
import com.example.schoolmanagement.model.Mark;
import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.repository.ExamRepository;
import com.example.schoolmanagement.repository.MarkRepository;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarkServiceTest {

    private static final String STUDENT_ID = "student-1";
    private static final String EXAM_ID = "exam-1";
    @Mock
    MarkRepository markRepository;
    @Mock
    StudentRepository studentRepository;
    @Mock
    ExamRepository examRepository;
    @Mock
    AuthenticationService authenticationService;
    @InjectMocks
    MarkService markService;

    @Test
    void testCreate() {
        MarkDTO markDTO = new MarkDTO();
        markDTO.setMark(1);
        markDTO.setExamId(EXAM_ID);
        markDTO.setStudentId(STUDENT_ID);

        Student student = mock(Student.class);
        when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.of(student));

        Exam exam = mock(Exam.class);
        when(examRepository.findById(EXAM_ID)).thenReturn(Optional.of(exam));

        Mark mark = mock(Mark.class);
        when(markRepository.save(any(Mark.class))).thenReturn(mark);

        ResponseDTO response = markService.create(markDTO);
        assertNotNull(response);
        assertEquals(mark, response.getData());
        assertEquals(Constants.CREATED, response.getMessage());
        assertEquals(HttpStatus.CREATED.name(), response.getStatusValue());
    }

    @Test
    void testRetrieveStudentMarkWhenMarksExist() {

        Mark mockMark = mock(Mark.class);
        when(markRepository.findMarkByStudent(STUDENT_ID)).thenReturn(mockMark);

        ResponseDTO response = markService.retrieveStudentMark(STUDENT_ID);

        assertNotNull(response);
        assertEquals(Constants.RETRIEVED, response.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), response.getStatusValue());
        assertEquals(mockMark, response.getData());
    }
}
