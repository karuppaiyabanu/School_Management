package com.example.schoolmanagement.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SearchFilterResponse {

    private String school;
    private String standard;
    private String subject;
}
