package com.example.Employee.TestAssignment.exceptions;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorOutput> handleCustomException(CustomException exception){
        ErrorOutput response = new ErrorOutput(exception.getErrorCode(), exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(exception.getErrorCode()));
    }
}
