package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.TeacherDTO;
import com.example.schoolmanagement.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @Test
    @WithMockUser(roles = "HR")
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
    @WithMockUser(roles = "HR")
    void testRetrieveTeacher() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Teachers retrieved successfully");

        when(teacherService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/teachers/retrieve"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Teachers retrieved successfully\"}"));
    }

    @Test
    @WithMockUser(roles = "HR")
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
    @WithMockUser(roles = "HR")
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
    @WithMockUser(roles = "HR")
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