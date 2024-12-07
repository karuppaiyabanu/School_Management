package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionDTO;
import com.example.schoolmanagement.service.SectionService;
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

class SectionControllerTest {


    private MockMvc mockMvc;

    @Mock
    private SectionService sectionService;

    @InjectMocks
    private SectionController sectionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sectionController).build();
    }

    @Test
    void testCreateSection() throws Exception {

        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setName("Section-1");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section Create Successfully");

        when(sectionService.create(sectionDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/sections/create")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Section-1\"}"))
                .andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Section Create Successfully\"}"));
    }

    @Test
    void testRetrieveSection() throws Exception {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Sections retrieved successfully");

        when(sectionService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/sections/retrieve")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Sections retrieved successfully\"}"));
    }

    @Test
    void testRetrieveSectionById() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section retrieved successfully");

        when(sectionService.retrieveById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/sections/retrieveById/{id}", id)).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Section retrieved successfully\"}"));
    }

    @Test
    void testDeleteSection() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section deleted successfully");

        when(sectionService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/v1/sections/remove/{id}", id)).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Section deleted successfully\"}"));
    }

    @Test
    void testUpdateSection() throws Exception {
        String id = "1";

        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setName("Update Section Name");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section updated successfully");

        when(sectionService.update(id, sectionDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/sections/update/{id}", id).contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Update Section Name\"}")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Section updated successfully\"}"));
    }


}