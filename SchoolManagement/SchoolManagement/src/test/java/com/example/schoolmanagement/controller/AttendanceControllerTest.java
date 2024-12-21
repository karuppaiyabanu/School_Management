package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.AttendanceDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.service.AttendanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AttendanceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AttendanceService attendanceService;

    @InjectMocks
    private AttendanceController attendanceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(attendanceController).build();
    }

    @Test
    @WithMockUser(roles = "ROLE_TEACHER")
    void testCreateAttendance() throws Exception {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setStatus("present");
        attendanceDTO.setStudentId("1");
        attendanceDTO.setSectionTeacherId("12");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Attendance Create Successfully");

        when(attendanceService.create(attendanceDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/attendances/create")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"status\":\"present\",\"studentId\":\"1\",\"sectionTeacherId\":\"12\"}"))
                .andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Attendance Create Successfully\"}"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testRetrieveAttendances() throws Exception {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Attendance retrieved successfully");

        when(attendanceService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/attendances/retrieve")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Attendance retrieved successfully\"}"));
    }

}