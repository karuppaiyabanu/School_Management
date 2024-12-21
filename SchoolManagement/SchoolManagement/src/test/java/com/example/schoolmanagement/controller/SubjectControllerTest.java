package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SubjectDTO;
import com.example.schoolmanagement.service.SubjectService;
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

class SubjectControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private SubjectController subjectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
    }

    @Test
    void testCreateSubject() throws Exception {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName("Test Subject");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Subject created successfully");

        when(subjectService.create(subjectDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/subjects/create").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Test Subject\", \"schoolId\":\"123\", \"fees\":1.0}")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Subject created successfully\"}"));
    }

    @Test
    void testRetrieveSubject() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Subject retrieved successfully");

        when(subjectService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/subjects/retrieve")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Subject retrieved successfully\"}"));
    }


    @Test
    void testDeleteSubject() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Subject deleted successfully");

        when(subjectService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/v1/subjects/remove/{id}", id)).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Subject deleted successfully\"}"));
    }

    @Test
    void testUpdateSubject() throws Exception {
        String id = "1";

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName("Update Subject");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Subject updated successfully");

        when(subjectService.update(id, subjectDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/subjects/update/{id}", id).contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Update Subject\"}")).andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Subject updated successfully\"}"));
    }

}