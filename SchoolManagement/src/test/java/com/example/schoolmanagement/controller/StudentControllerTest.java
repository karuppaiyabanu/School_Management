package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StudentDTO;
import com.example.schoolmanagement.service.StudentService;
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
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;


    @Test
    @WithMockUser(roles = "OFFICE_ADMIN")
    void testCreateStudent() throws Exception {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Student Name");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Student created successfully");

        when(studentService.create(studentDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/students/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Student Name\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Student created successfully\"}"));
    }

    @Test
    @WithMockUser(roles = "OFFICE_ADMIN")
    void testRetrieveStudents() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Students retrieved successfully");

        when(studentService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/students/retrieve"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Students retrieved successfully\"}"));
    }

    @Test
    @WithMockUser(roles = "OFFICE_ADMIN")
    void testRetrieveStudentById() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Student retrieved successfully");

        when(studentService.retrieveById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/students/retrieveById/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Student retrieved successfully\"}"));
    }

    @Test
    @WithMockUser(roles = "OFFICE_ADMIN")
    void testDeleteStudent() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Student deleted successfully");

        when(studentService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/v1/students/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Student deleted successfully\"}"));
    }

    @Test
    @WithMockUser(roles = "OFFICE_ADMIN")
    void testUpdateStudent() throws Exception {
        String id = "1";
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Update Student");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Student updated successfully");

        when(studentService.update(id, studentDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/students/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Update Student\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Student updated successfully\"}"));
    }
}