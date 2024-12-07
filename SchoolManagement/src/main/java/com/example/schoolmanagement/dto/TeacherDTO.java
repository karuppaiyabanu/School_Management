package com.example.schoolmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {

    private String name;
    private String gender;
    private String knownSubject;
    private String address;
    private String phone;
    private String email;
    private String schoolId;

}