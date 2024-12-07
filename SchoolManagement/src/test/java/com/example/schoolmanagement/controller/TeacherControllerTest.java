package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.TeacherDTO;
import com.example.schoolmanagement.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TeacherControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(teacherController).build();
    }

    @Test
    void testCreateTeacher() throws Exception {

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName("Test Teacher");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Teacher created successfully");


        when(teacherService.create(teacherDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/teachers/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Teacher\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Teacher created successfully\"}"));
    }


    @Test
    void testRetrieveTeacher() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Teachers retrieved successfully");

        when(teacherService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/teachers/retrieve"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Teachers retrieved successfully\"}"));
    }

    @Test
    void testRetrieveTeacherById() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Teacher retrieved successfully");

        when(teacherService.retrieveById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/teachers/retrieveById/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Teacher retrieved successfully\"}"));
    }

    @Test
    void testDeleteTeacher() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("School deleted successfully");

        when(teacherService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/v1/teachers/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"School deleted successfully\"}"));
    }

    @Test
    void testUpdateTeacher() throws Exception {
        String id = "1";
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName("Updated Teacher");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("");

        when(teacherService.update(id, teacherDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/teachers/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Teacher\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"\"}"));
    }


}