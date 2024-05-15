package com.example.Employee.TestAssignment.exceptions;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorOutput {

    private int statusCode;
    private String message;
    private LocalDateTime timeStamp;

}
