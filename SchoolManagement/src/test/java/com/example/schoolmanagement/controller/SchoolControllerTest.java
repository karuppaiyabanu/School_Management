package com.example.schoolmanagement.controller;


import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolDTO;
import com.example.schoolmanagement.service.SchoolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class SchoolControllerTest {


    private MockMvc mockMvc;

    @Mock
    private SchoolService schoolService;

    @InjectMocks
    private SchoolController schoolController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(schoolController).build();
    }

    @Test
    void testCreateSchool() throws Exception {

        SchoolDTO schoolDTO = new SchoolDTO();
        schoolDTO.setName("Test School");
        schoolDTO.setPhone("123");
        schoolDTO.setAddress("Test Address");


        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("School created successfully");


        when(schoolService.create(schoolDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/schools/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test School\", \"phone\":\"123\", \"address\":\"Test Address\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"School created successfully\"}"));
    }


    @Test
    void testRetrieveSchools() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Schools retrieved successfully");

        when(schoolService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/schools/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Schools retrieved successfully\"}"));
    }

    @Test
    void testRetrieveSchoolById() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("School retrieved successfully");

        when(schoolService.retrieveById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/schools/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"School retrieved successfully\"}"));
    }

    @Test
    void testDeleteSchool() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("School deleted successfully");

        when(schoolService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/v1/schools/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"School deleted successfully\"}"));
    }

    @Test
    void testUpdateSchool() throws Exception {
        String id = "1";
        SchoolDTO schoolDTO = new SchoolDTO();
        schoolDTO.setName("Updated School");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("School updated successfully");

        when(schoolService.update(id, schoolDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/schools/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated School\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"School updated successfully\"}"));
    }


}

