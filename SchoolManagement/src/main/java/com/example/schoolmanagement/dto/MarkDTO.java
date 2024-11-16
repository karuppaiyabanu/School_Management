package com.example.schoolmanagement.dto;

import lombok.Data;

@Data
public class MarkDTO {

    private String studentId;
    private Integer mark;
    private String examId;
    private String createdBy;
    private String updatedBy;

}
