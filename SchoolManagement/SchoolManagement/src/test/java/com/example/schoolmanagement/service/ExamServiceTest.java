package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ExamDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.model.Exam;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.model.Subject;
import com.example.schoolmanagement.repository.ExamRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.SubjectRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExamServiceTest {

    private static final String STANDARD_ID = "";
    private static final String SUBJECT_ID = "";
    private static final String EXAM_ID = "";
    @Mock
    ExamRepository examRepository;
    @Mock
    StandardRepository standardRepository;
    @Mock
    SubjectRepository subjectRepository;
    @InjectMocks
    ExamService examService;
    @Mock
    private AuthenticationService authenticationService;

    @Test
    void testCreate() {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setStandardId(STANDARD_ID);
        examDTO.setSubjectId(SUBJECT_ID);
        examDTO.setName("maths");
        examDTO.setDate("");

        Standard standard = mock(Standard.class);
        when(standardRepository.findById(STANDARD_ID)).thenReturn(Optional.of(standard));

        Subject subject = mock(Subject.class);
        when(subjectRepository.findById(SUBJECT_ID)).thenReturn(Optional.of(subject));

        Exam exam = mock(Exam.class);
        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        ResponseDTO response = examService.create(examDTO);

        assertNotNull(response);
        assertEquals(exam, response.getData());
        assertEquals(Constants.CREATED, response.getMessage());
        assertEquals(HttpStatus.CREATED.name(), response.getStatusValue());

    }


    @Test
    public void testRetrieve() {
        Exam exam = mock(Exam.class);

        List<Exam> exams = Stream.ofNullable(exam).toList();
        when(examRepository.findAll()).thenReturn(exams);

        ResponseDTO response = examService.retrieve();

        Object data = response.getData();
        assertEquals(data, exams);
        assertEquals(Constants.RETRIEVED, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue());
    }

    @Test
    public void testRetrieveById() {
        Exam exam = mock(Exam.class);

        when(examRepository.findById(EXAM_ID)).thenReturn(Optional.ofNullable(exam));

        ResponseDTO responseDTO = examService.retrieveById(EXAM_ID);

        Object data = responseDTO.getData();
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    public void testUpdate() {

        Exam exam = mock(Exam.class);
        ExamDTO examDTO = mock(ExamDTO.class);

        when(examRepository.findById(EXAM_ID)).thenReturn(Optional.ofNullable(exam));
        assert exam != null;
        when(examRepository.save(exam)).thenReturn(exam);

        ResponseDTO response = examService.update(EXAM_ID, examDTO);

        assertEquals(response.getData(), exam);
    }
}