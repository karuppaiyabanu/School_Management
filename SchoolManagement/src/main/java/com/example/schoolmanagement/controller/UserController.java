package com.example.schoolmanagement.controller;


import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.UserLoginRequestDTO;
import com.example.schoolmanagement.dto.UserSignUpRequestDTO;
import com.example.schoolmanagement.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/user")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final UserSignUpRequestDTO userSignUpRequestDTO) {
        return this.userService.create(userSignUpRequestDTO);
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody final UserLoginRequestDTO userLoginRequestDTO) {
        return this.userService.login(userLoginRequestDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieve() {
        return this.userService.retrieve();
    }

    @GetMapping("/retrieveById/{id}")
    public ResponseDTO retrieveById(@PathVariable("id") final Integer id) {
        return this.userService.retrieveById(id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable("id") final Integer id) {
        return this.userService.removeById(id);
    }

}