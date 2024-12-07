package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionTeacherDTO;
import com.example.schoolmanagement.service.SectionTeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SectionTeacherControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SectionTeacherService service;

    @InjectMocks
    private SectionTeacherController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testAssignTeacherSection() throws Exception {


        SectionTeacherDTO sectionTeacherDTO = new SectionTeacherDTO();
        sectionTeacherDTO.setSection("1");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("AssignTeacher created successfully");


        when(service.create(sectionTeacherDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/assign-teachers/assign").contentType(MediaType.APPLICATION_JSON).content("{\"section\":\"1\"}")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"AssignTeacher created successfully\"}"));
    }

    @Test
    void testRetrieveTeacherSection() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Teachers retrieved successfully");

        when(service.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/assign-teachers/retrieve")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Teachers retrieved successfully\"}"));
    }

    @Test
    void testUpdateTeacherSection() throws Exception {
        String id = "1";
        SectionTeacherDTO sectionTeacherDTO = new SectionTeacherDTO();
        sectionTeacherDTO.setSection("section-B");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("TeacherSection Update Successfully");

        when(service.update(id, sectionTeacherDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/assign-teachers/update/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"section\":\"section-B\"}"))
                .andExpect(status().isOk()).andExpect(content().json("{\"message\":\"TeacherSection Update Successfully\"}"));
    }

}