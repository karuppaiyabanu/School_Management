package com.example.schoolmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@AllArgsConstructor
public class StudentReport {

    private String studentId;
    private String studentName;
    private String standardId;
    private double studentAttendancePercentage;
    private String classTeacherName;
    private String examName;
    private double totalMarks;

}
