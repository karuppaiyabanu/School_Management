package com.example.schoolmanagement.service;


import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StudentDTO;
import com.example.schoolmanagement.model.School;
import com.example.schoolmanagement.model.Section;
import com.example.schoolmanagement.model.Standard;
import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.SectionRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentServiceTest {

    private static final String SCHOOL_ID = "school-1";
    private static final String STANDARD_ID = "std-1";
    private static final String SECTION_ID = "sec-1";
    private static final String STUDENT_ID = "student-1";
    @Mock
    StudentRepository studentRepository;
    @Mock
    SchoolRepository schoolRepository;
    @Mock
    StandardRepository standardRepository;
    @Mock
    SectionRepository sectionRepository;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setSchool(SCHOOL_ID);
        studentDTO.setStandard(STANDARD_ID);
        studentDTO.setSection(SECTION_ID);
        studentDTO.setName("John Doe");
        studentDTO.setFatherName("Father Name");
        studentDTO.setMotherName("Mother Name");
        studentDTO.setAddress("Some address");
        studentDTO.setPhone("1234567890");


        School mockSchool = mock(School.class);
        when(schoolRepository.findById(SCHOOL_ID)).thenReturn(Optional.of(mockSchool));

        Standard mockStandard = mock(Standard.class);
        when(standardRepository.findById(STANDARD_ID)).thenReturn(Optional.of(mockStandard));

        Section mockSection = mock(Section.class);
        when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.of(mockSection));

        Student student = mock(Student.class);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        ResponseDTO response = studentService.create(studentDTO);

        assertNotNull(response);
        assertEquals(student, response.getData());
        assertEquals(Constants.CREATED, response.getMessage());
        assertEquals(HttpStatus.CREATED.name(), response.getStatusValue());
    }

    @Test
    public void testRetrieve() {

        Student student = mock(Student.class);

        List<Student> students = Stream.ofNullable(student).toList();
        when(studentRepository.findAll()).thenReturn(students);

        ResponseDTO response = studentService.retrieve();

        Object data = response.getData();
        assertEquals(data, students);
        assertEquals(Constants.RETRIEVED, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue());
    }

    @Test
    public void testRetrieveById() {
        Student student = mock(Student.class);
        when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.ofNullable(student));

        ResponseDTO responseDTO = studentService.retrieveById(STUDENT_ID);

        Object data = responseDTO.getData();
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    public void testRemove() {

        when(studentRepository.existsById(STUDENT_ID)).thenReturn(true);

        ResponseDTO responseDTO = studentService.remove(STUDENT_ID);
        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertEquals(STUDENT_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }

    @Test
    public void testUpdate() {
        Student student = mock(Student.class);
        StudentDTO studentDTO = mock(StudentDTO.class);

        when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.ofNullable(student));
        assert student != null;
        when(studentRepository.save(student)).thenReturn(student);

        ResponseDTO response = studentService.update(STUDENT_ID, studentDTO);

        assertEquals(response.getData(), student);
    }
}
