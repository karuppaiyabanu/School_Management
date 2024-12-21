package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ExamDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.service.ExamService;
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

class ExamControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExamService examService;

    @InjectMocks
    private ExamController examController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(examController).build();
    }

    @Test
    void testCreateExam() throws Exception {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setName("Tamil");
        examDTO.setSubjectId("subject_id");
        examDTO.setStandardId("standard_id");
        examDTO.setDate("06-12-2024");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Exam created successfully");

        when(examService.create(examDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/exams/create").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Tamil\",\"subjectId\":\"subject_id\",\"standardId\":\"standard_id\",\"date\":\"06-12-2024\"}")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Exam created successfully\"}"));
    }


    @Test
    void testRetrieveExam() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Exam retrieved successfully");

        when(examService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/exams/retrieve")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Exam retrieved successfully\"}"));
    }

    @Test
    void testRetrieveExamById() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Exam retrieved successfully");

        when(examService.retrieveById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/exams/retrieveById/{id}", id)).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Exam retrieved successfully\"}"));
    }

    @Test
    void testUpdateExam() throws Exception {
        String id = "1";
        ExamDTO examDTO = new ExamDTO();
        examDTO.setName("English");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Update Exam Successfully");

        when(examService.update(id, examDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/exams/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"English\"}"))
                .andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Update Exam Successfully\"}"));
    }
}