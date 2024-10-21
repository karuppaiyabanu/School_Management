package com.example.schoolmanagement.dto;

import com.example.schoolmanagement.model.School;

public class ResponseDTO {
    private String message;
    private Object data;
    private String statusValue;

    public ResponseDTO(){};
    public ResponseDTO(String message, Object data, String statusValue) {
        this.message = message;
        this.data = data;
        this.statusValue = statusValue;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }
}
