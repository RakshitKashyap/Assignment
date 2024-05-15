package com.example.Employee.TestAssignment.service;

import com.example.Employee.TestAssignment.models.RequestDto.UserCreateDto;
import com.example.Employee.TestAssignment.models.ResponseDto.MiniUserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String registerNewUser(UserCreateDto createDto);


}
