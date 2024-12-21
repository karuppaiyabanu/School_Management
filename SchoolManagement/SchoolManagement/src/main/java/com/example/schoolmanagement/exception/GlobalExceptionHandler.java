package com.example.schoolmanagement.exception;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleBadRequestException(ResourceNotFoundException exception) {
        final ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatusValue(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.DATA_NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO conflictException(ConflictException exception) {
        final ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatusValue(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setData(null);
        return responseDTO;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO nullPointerException(NullPointerException exception) {
        final ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatusValue(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.DATA_NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseDTO handleUserNotFoundException(UserNotFoundException exception) {
        final ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatusValue(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        responseDTO.setMessage(exception.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleSQLException(SQLException exception) {
        final ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatusValue(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.DATA_NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleException(Exception exception) {
        final ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatusValue(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.DATA_NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }
}
