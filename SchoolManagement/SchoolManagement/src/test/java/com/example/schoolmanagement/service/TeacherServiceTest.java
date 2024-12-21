package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.TeacherDTO;
import com.example.schoolmanagement.model.School;
import com.example.schoolmanagement.model.Teacher;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
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
public class TeacherServiceTest {

    private static final String SCHOOL_ID = "school-1";
    private static final String TEACHER_ID = "";
    @Mock
    SchoolRepository schoolRepository;
    @Mock
    TeacherRepository teacherRepository;
    @InjectMocks
    TeacherService teacherService;
    @Mock
    private AuthenticationService authenticationService;

    @Test
    void testCreate() {

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setSchoolId(SCHOOL_ID);
        teacherDTO.setName("John Doe");
        teacherDTO.setGender("Male");
        teacherDTO.setAddress("Some address");
        teacherDTO.setPhone("1234567890");
        teacherDTO.setEmail("johndoe@example.com");
        teacherDTO.setKnownSubject("Math");

        School mockSchool = mock(School.class);
        mockSchool.setId(SCHOOL_ID);
        when(schoolRepository.findById(SCHOOL_ID)).thenReturn(Optional.of(mockSchool));

        Teacher teacher = mock(Teacher.class);
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);


        ResponseDTO response = teacherService.create(teacherDTO);

        assertNotNull(response);
        Object data = response.getData();
        assertEquals(data, teacher);
        assertEquals(Constants.CREATED, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.CREATED.name(), response.getStatusValue());
    }


    @Test
    public void testRetrieve() {

        Teacher teacher = mock(Teacher.class);

        List<Teacher> teachers = Stream.ofNullable(teacher).toList();
        when(teacherRepository.findAll()).thenReturn(teachers);

        ResponseDTO response = teacherService.retrieve();

        Object data = response.getData();
        assertEquals(data, teachers);
        assertEquals(Constants.RETRIEVED, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue());
    }

    @Test
    public void testRetrieveById() {
        Teacher teacher = mock(Teacher.class);
        when(teacherRepository.findById(TEACHER_ID)).thenReturn(Optional.ofNullable(teacher));

        ResponseDTO responseDTO = teacherService.retrieveById(TEACHER_ID);

        Object data = responseDTO.getData();
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.SUCCESS, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }


    @Test
    public void testRemove() {

        when(teacherRepository.existsById(TEACHER_ID)).thenReturn(true);

        ResponseDTO responseDTO = teacherService.remove(TEACHER_ID);
        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertEquals(TEACHER_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }

    @Test
    public void testUpdate() {

        Teacher teacher = mock(Teacher.class);
        TeacherDTO teacherDTO = mock(TeacherDTO.class);

        when(teacherRepository.findById(TEACHER_ID)).thenReturn(Optional.ofNullable(teacher));
        assert teacher != null;
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        ResponseDTO response = teacherService.update(TEACHER_ID, teacherDTO);

        assertEquals(response.getData(), teacher);


    }


}
