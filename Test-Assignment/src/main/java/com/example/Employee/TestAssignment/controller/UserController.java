package com.example.Employee.TestAssignment.controller;

import com.example.Employee.TestAssignment.exceptions.CustomException;
import com.example.Employee.TestAssignment.models.RequestDto.UserCreateDto;
import com.example.Employee.TestAssignment.models.ResponseDto.MiniUserResponseDto;
import com.example.Employee.TestAssignment.service.UserService;
import com.example.Employee.TestAssignment.utils.CatchableErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController("v1/user")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity addNewUser(@RequestBody UserCreateDto createDto){
        log.info("initiating endpoint to register a new user");
        String user = userService.registerNewUser(createDto);
        if(Objects.isNull(user))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>("Registered", HttpStatus.CREATED);
    }


    @PostMapping("/test-request")
    public ResponseEntity testPostRequest() {
        return ResponseEntity.ok("POST request successful");
    }
}
