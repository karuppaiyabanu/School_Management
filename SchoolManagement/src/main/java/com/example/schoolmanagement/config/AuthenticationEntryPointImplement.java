package com.example.schoolmanagement.config;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointImplement implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
       System.out.println("my guess is Correct");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatusValue(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        responseDTO.setMessage(Constants.CREDENTIALS_MISMATCH);

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("json");
        mapper.writeValue(response.getOutputStream(), responseDTO);
    }

}
