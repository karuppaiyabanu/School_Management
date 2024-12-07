package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.AttendanceDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.model.Attendance;
import com.example.schoolmanagement.model.SectionTeacher;
import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.repository.AttendanceRepository;
import com.example.schoolmanagement.repository.SectionTeacherRepository;
import com.example.schoolmanagement.repository.StudentRepository;
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
class AttendanceServiceTest {

    private static final String SECTION_TEACHER_ID = "";
    private static final String STUDENT_ID = "";
    @Mock
    AttendanceRepository attendanceRepository;
    @Mock
    StudentRepository studentRepository;
    @Mock
    SectionTeacherRepository sectionTeacherRepository;
    @InjectMocks
    AttendanceService attendanceService;

    @Test
    void testCreate() {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setSectionTeacherId(SECTION_TEACHER_ID);
        attendanceDTO.setStudentId(STUDENT_ID);
        attendanceDTO.setStatus("present");

        SectionTeacher sectionTeacher = mock(SectionTeacher.class);
        when(sectionTeacherRepository.findById(SECTION_TEACHER_ID)).thenReturn(Optional.of(sectionTeacher));

        Student student = mock(Student.class);
        when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.of(student));

        Attendance attendance = mock(Attendance.class);
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);

        ResponseDTO response = attendanceService.create(attendanceDTO);

        assertNotNull(response);
        assertEquals(Constants.CREATED, response.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), response.getStatusValue());
        assertEquals(attendance, response.getData());
    }

    @Test
    public void testRetrieve() {

        Attendance attendance = mock(Attendance.class);
        List<Attendance> attendances = Stream.ofNullable(attendance).toList();
        when(attendanceRepository.findAll()).thenReturn(attendances);

        ResponseDTO response = attendanceService.retrieve();

        Object data = response.getData();
        assertEquals(data, attendances);
        assertEquals(Constants.RETRIEVED, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue());
    }


}