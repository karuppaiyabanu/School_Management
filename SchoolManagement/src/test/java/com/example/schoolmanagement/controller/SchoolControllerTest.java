package com.example.schoolmanagement.controller;


import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolDTO;
import com.example.schoolmanagement.service.SchoolService;
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
class SchoolControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchoolService schoolService;


    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
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
    @WithMockUser(roles = "SUPER_ADMIN")
    void testRetrieveSchools() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Schools retrieved successfully");

        when(schoolService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/schools/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Schools retrieved successfully\"}"));
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
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
    @WithMockUser(roles = "SUPER_ADMIN")
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
    @WithMockUser(roles = "SUPER_ADMIN")
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

