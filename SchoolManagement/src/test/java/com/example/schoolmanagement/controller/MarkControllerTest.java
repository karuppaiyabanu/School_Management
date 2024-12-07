package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.MarkDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.service.MarkService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MarkControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MarkService markService;

    @InjectMocks
    private MarkController markController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(markController).build();
    }

    @Test
    void testCreateMark() throws Exception {

        MarkDTO markDTO = new MarkDTO();
        markDTO.setMark(95);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Mark created successfully");

        when(markService.create(markDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/marks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"mark\":\"95\"}")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Mark created successfully\"}"));
    }

    @Test
    void testRetrieveByStudentMarkById() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Student Mark retrieved successfully");

        when(markService.retrieveStudentMark(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/marks/retrieve/{student-id}", id)).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Student Mark retrieved successfully\"}"));
    }
}