package com.example.schoolmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherDTO {
    private String name;
    private String gender;
    private String knownSubject;
    private String address;
    private String phone;
    private String email;
    private String schoolId;
    private String createdBy;
    private String updatedBy;

}