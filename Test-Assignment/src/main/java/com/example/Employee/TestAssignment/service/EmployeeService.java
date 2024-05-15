package com.example.Employee.TestAssignment.service;

import com.example.Employee.TestAssignment.models.RequestDto.AddressRequestDto;
import com.example.Employee.TestAssignment.models.RequestDto.EmployeeRequestDto;
import com.example.Employee.TestAssignment.models.RequestDto.SearchAndOrderEmployee;
import com.example.Employee.TestAssignment.models.ResponseDto.MiniUserResponseDto;
import com.example.Employee.TestAssignment.models.ResponseDto.NewEmployeeResponse;

import java.util.List;

public interface EmployeeService {
    List<MiniUserResponseDto> viewAll();

    MiniUserResponseDto viewEmployeeById(Long employeeId);

    List<MiniUserResponseDto> searchViaRequest(SearchAndOrderEmployee employee);

    NewEmployeeResponse addNewEmployee(EmployeeRequestDto requestDto);

    boolean softDelete(Long employeeId);

    MiniUserResponseDto updateEmployee(Long employeeId, EmployeeRequestDto requestDto);

    MiniUserResponseDto viewEmployeeAddresses(Long employeeId);

    MiniUserResponseDto addNewEmployeeAddress(Long employeeId, AddressRequestDto requestDto);

    MiniUserResponseDto updateEmployeeAddresses(Long employeeId, AddressRequestDto requestDto, Long addressId);

    boolean deleteEmployeeAddresses(Long employeeId, Long addressId);
}
