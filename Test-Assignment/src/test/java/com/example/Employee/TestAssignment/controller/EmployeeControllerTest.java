package com.example.Employee.TestAssignment.controller;

import com.example.Employee.TestAssignment.models.RequestDto.AddressRequestDto;
import com.example.Employee.TestAssignment.models.RequestDto.EmployeeRequestDto;
import com.example.Employee.TestAssignment.models.RequestDto.SearchAndOrderEmployee;
import com.example.Employee.TestAssignment.models.ResponseDto.MiniUserResponseDto;
import com.example.Employee.TestAssignment.models.ResponseDto.NewEmployeeResponse;
import com.example.Employee.TestAssignment.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private EmployeeService employeeService;

  @Test
  public void testGetAllEmployees() throws Exception {
    // Prepare mock data
    List<MiniUserResponseDto> responseDtoList =
        Collections.singletonList(new MiniUserResponseDto());
    when(employeeService.viewAll()).thenReturn(responseDtoList);

    // Perform GET request
    mockMvc
        .perform(get("/v1/employee/viewAll"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  public void testGetAllEmployeeById() throws Exception {
    // Prepare mock data
    MiniUserResponseDto responseDto = new MiniUserResponseDto();
    when(employeeService.viewEmployeeById(any())).thenReturn(responseDto);

    // Perform GET request
    mockMvc.perform(get("/v1/employee/view/1")).andExpect(status().isOk());
  }

  @Test
  public void testSearchAndSortEmployee() throws Exception {
    // Prepare mock data
    List<MiniUserResponseDto> responseDtoList =
        Collections.singletonList(new MiniUserResponseDto());
    when(employeeService.searchViaRequest(any(SearchAndOrderEmployee.class)))
        .thenReturn(responseDtoList);

    // Perform POST request
    mockMvc
        .perform(
            post("/v1/employee/view/searchAndOrder/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"criteria\": \"value\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  public void testAddNewEmployee() throws Exception {
    // Prepare mock data
    NewEmployeeResponse responseDto = new NewEmployeeResponse();
    when(employeeService.addNewEmployee(any(EmployeeRequestDto.class))).thenReturn(responseDto);

    // Perform POST request
    mockMvc
        .perform(
            post("/v1/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"John\", \"email\": \"john@example.com\"}"))
        .andExpect(status().isOk());
  }

  @Test
  public void testUpdateEmployee() throws Exception {
    // Prepare mock data
    MiniUserResponseDto responseDto = new MiniUserResponseDto();
    when(employeeService.updateEmployee(anyLong(), any(EmployeeRequestDto.class)))
        .thenReturn(responseDto);

    // Perform PUT request
    mockMvc
        .perform(
            put("/v1/employee/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"John\", \"email\": \"john@example.com\"}"))
        .andExpect(status().isOk());
  }

  @Test
  public void testDeleteEmployee() throws Exception {
    // Prepare mock data
    when(employeeService.softDelete(anyLong())).thenReturn(true);

    // Perform DELETE request
    mockMvc.perform(delete("/v1/employee/delete/1")).andExpect(status().isOk());
  }

  @Test
  public void testGetAddressOfEmployee() throws Exception {
    // Prepare mock data
    MiniUserResponseDto responseDto = new MiniUserResponseDto();
    when(employeeService.viewEmployeeAddresses(anyLong())).thenReturn(responseDto);

    // Perform GET request
    mockMvc.perform(get("/v1/employee/1/address")).andExpect(status().isOk());
  }

  @Test
  public void testAddNewAddress() throws Exception {
    // Prepare mock data
    MiniUserResponseDto responseDto = new MiniUserResponseDto();
    when(employeeService.addNewEmployeeAddress(anyLong(), any(AddressRequestDto.class)))
        .thenReturn(responseDto);

    // Perform POST request
    mockMvc
        .perform(
            post("/v1/employee/1/address/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\": \"123 Street\", \"city\": \"City\"}"))
        .andExpect(status().isOk());
  }

  @Test
  public void testUpdateAddress() throws Exception {
    // Prepare mock data
    MiniUserResponseDto responseDto = new MiniUserResponseDto();
    when(employeeService.updateEmployeeAddresses(
            anyLong(), any(AddressRequestDto.class), anyLong()))
        .thenReturn(responseDto);

    // Perform PUT request
    mockMvc
        .perform(
            put("/v1/employee/1/address/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\": \"123 Street\", \"city\": \"Updated City\"}"))
        .andExpect(status().isOk());
  }

    @Test
    public void testDeleteAddress() throws Exception {
        // Prepare mock data
        when(employeeService.deleteEmployeeAddresses(anyLong(), anyLong())).thenReturn(true);

        // Perform DELETE request
        mockMvc.perform(delete("/v1/employee/1/address/delete/1"))
                .andExpect(status().isOk());
    }
}

