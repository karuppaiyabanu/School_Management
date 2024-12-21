package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionDTO;
import com.example.schoolmanagement.service.SectionService;
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
class SectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SectionService sectionService;

    @Test
    @WithMockUser(roles = "ADMIN")
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
    @WithMockUser(roles = "ADMIN")
    void testRetrieveSection() throws Exception {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Sections retrieved successfully");

        when(sectionService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/sections/retrieve")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Sections retrieved successfully\"}"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testRetrieveSectionById() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section retrieved successfully");

        when(sectionService.retrieveById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/sections/retrieveById/{id}", id)).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Section retrieved successfully\"}"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteSection() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section deleted successfully");

        when(sectionService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/v1/sections/remove/{id}", id)).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Section deleted successfully\"}"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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