package com.example.schoolmanagement.dto;

import lombok.Data;

@Data
public class StandardDTO {
    private String StandardName;
    private Double fees;
    private String schoolId;
    private String createdBy;
    private String updatedBy;
}
