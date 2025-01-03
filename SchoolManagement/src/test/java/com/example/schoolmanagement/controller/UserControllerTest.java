package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.UserLoginRequestDTO;
import com.example.schoolmanagement.dto.UserSignUpRequestDTO;
import com.example.schoolmanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testCreateUser() throws Exception {

        UserSignUpRequestDTO requestDTO = new UserSignUpRequestDTO();
        requestDTO.setUsername("Test User");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("User created successfully");

        when(userService.create(requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/auth/v1/users/create").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"Test User\"}")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"User created successfully\"}"));
    }


    @Test
    void testRetrieveUsers() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Users retrieved successfully");

        when(userService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/auth/v1/users/retrieve")).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"Users retrieved successfully\"}"));
    }

    @Test
    void testRetrieveUserById() throws Exception {
        Integer id = 1;
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("User retrieved successfully");

        when(userService.retrieveById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/auth/v1/users/retrieveById/{id}", id)).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"User retrieved successfully\"}"));
    }

    @Test
    void testDeleteUser() throws Exception {
        Integer id = 1;
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("User deleted successfully");

        when(userService.removeById(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/auth/v1/users/remove/{id}", id)).andExpect(status().isOk()).andExpect(content().json("{\"message\":\"User deleted successfully\"}"));
    }


    @Test
    void testUserLogin() throws Exception {
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setUsername("testUser");
        userLoginRequestDTO.setPassword("testPassword");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("User Login successfully");

        when(userService.login(userLoginRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/auth/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"testUser\",\"password\":\"testPassword\"}"))
                .andExpect(status().isOk()).andExpect(content().json("{\"message\":\"User Login successfully\"}"));

    }


    @Test
    @WithMockUser(username = "banu", roles = "USER")
    public void testAccessWithAdminRole() throws Exception {
        mockMvc.perform(get("/auth/v1/users/"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}