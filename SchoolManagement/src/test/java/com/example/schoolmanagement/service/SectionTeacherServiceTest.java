package com.example.schoolmanagement.service;


import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionTeacherDTO;
import com.example.schoolmanagement.model.Section;
import com.example.schoolmanagement.model.SectionTeacher;
import com.example.schoolmanagement.model.Teacher;
import com.example.schoolmanagement.repository.SectionRepository;
import com.example.schoolmanagement.repository.SectionTeacherRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
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

class SectionTeacherServiceTest {

    private static final String SECTION_ID = "sec-1";
    private static final String TEACHER_ID = "";

    @Mock
    SectionTeacherRepository sectionTeacherRepository;
    @Mock
    SectionRepository sectionRepository;
    @Mock
    TeacherRepository teacherRepository;
    @Mock
    AuthenticationService authenticationService;
    @InjectMocks
    SectionTeacherService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {

        SectionTeacherDTO sectionTeacherDTO = new SectionTeacherDTO();
        sectionTeacherDTO.setSection(SECTION_ID);
        sectionTeacherDTO.setTeacherId(TEACHER_ID);


        Section mockSection = mock(Section.class);
        when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.of(mockSection));

        Teacher mockTeacher = mock(Teacher.class);
        when(teacherRepository.findById(TEACHER_ID)).thenReturn(Optional.of(mockTeacher));


        SectionTeacher mockSectionTeacher = mock(SectionTeacher.class);
        when(sectionTeacherRepository.save(any(SectionTeacher.class))).thenReturn(mockSectionTeacher);

        ResponseDTO response = service.create(sectionTeacherDTO);

        assertNotNull(response);
        assertEquals(Constants.CREATED, response.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), response.getStatusValue());
        assertEquals(mockSectionTeacher, response.getData());
    }

    @Test
    public void testRetrieve() {

        SectionTeacher sectionTeacher = mock(SectionTeacher.class);

        List<SectionTeacher> sectionTeachers = Stream.ofNullable(sectionTeacher).toList();
        when(sectionTeacherRepository.findAll()).thenReturn(sectionTeachers);

        ResponseDTO response = service.retrieve();

        Object data = response.getData();
        assertEquals(data, sectionTeachers);
        assertEquals(Constants.RETRIEVED, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue());
    }

    @Test
    public void testUpdate() {

        SectionTeacher sectionTeacher = mock(SectionTeacher.class);
        SectionTeacherDTO sectionTeacherDTO = mock(SectionTeacherDTO.class);

        when(sectionTeacherRepository.findById(TEACHER_ID)).thenReturn(Optional.ofNullable(sectionTeacher));
        assert sectionTeacher != null;
        when(sectionTeacherRepository.save(sectionTeacher)).thenReturn(sectionTeacher);

        ResponseDTO response = service.update(TEACHER_ID, sectionTeacherDTO);

        assertEquals(response.getData(), sectionTeacher);

    }
}