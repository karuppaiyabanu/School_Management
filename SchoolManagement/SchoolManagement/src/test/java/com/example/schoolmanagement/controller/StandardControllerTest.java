package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StandardDTO;
import com.example.schoolmanagement.service.StandardService;
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

public class StandardControllerTest {

    @Mock
    StandardService standardService;
    @InjectMocks
    StandardController standardController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(standardController).build();
    }

    @Test
    void testCreateStandard() throws Exception {

        StandardDTO standardDTO = new StandardDTO();
        standardDTO.setStandardName("Test Standard");
        standardDTO.setSchoolId("123");
        standardDTO.setFees(1.0);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Standard created successfully");

        when(standardService.create(standardDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/standards/create").contentType(MediaType.APPLICATION_JSON).content("{\"standardName\":\"Test Standard\", \"schoolId\":\"123\", \"fees\":1.0}")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Standard created successfully\"}"));
    }


    @Test
    void testRetrieveStandard() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Standard retrieved successfully");

        when(standardService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/standards/retrieve")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Standard retrieved successfully\"}"));
    }

    @Test
    void testRetrieveStandardById() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Standard retrieved successfully");

        when(standardService.retrieveById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/standards/retrieveById/{id}", id)).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Standard retrieved successfully\"}"));
    }

    @Test
    void testDeleteStandard() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Standard deleted successfully");

        when(standardService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/v1/standards/remove/{id}", id)).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Standard deleted successfully\"}"));
    }


    @Test
    void testUpdateStandard() throws Exception {
        String id = "1";
        StandardDTO standardDTO = new StandardDTO();
        standardDTO.setStandardName("Updated Standard");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("");

        when(standardService.update(id, standardDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/standards/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON).content("{\"StandardName\":\"Updated Standard\"}")).andExpect(status().isOk());
        //.andExpect(content().json("{\"message\":\"\"}"));
    }

}

