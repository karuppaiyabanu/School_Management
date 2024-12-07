package com.example.schoolmanagement.dto;

import lombok.Data;

@Data
public class AttendanceDTO {

    private String studentId;
    private String sectionTeacherId;
    private String status;

}
