package com.example.Employee.TestAssignment.controller;

import com.example.Employee.TestAssignment.exceptions.CustomException;
import com.example.Employee.TestAssignment.models.RequestDto.AddressRequestDto;
import com.example.Employee.TestAssignment.models.RequestDto.EmployeeRequestDto;
import com.example.Employee.TestAssignment.models.RequestDto.SearchAndOrderEmployee;
import com.example.Employee.TestAssignment.models.ResponseDto.MiniUserResponseDto;
import com.example.Employee.TestAssignment.models.ResponseDto.NewEmployeeResponse;
import com.example.Employee.TestAssignment.service.EmployeeService;

import com.example.Employee.TestAssignment.utils.CatchableErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController("/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * this section covers all employee related endpoints
     */

    @GetMapping("/viewAll")
    private ResponseEntity getAllEmployees(){
        log.info("initiating endpoint to fetch all the employees");
        List<MiniUserResponseDto> responseDtoList = employeeService.viewAll();
        if(responseDtoList==null || responseDtoList.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/view/{employeeId}")
    private ResponseEntity getAllEmployeeById(@PathVariable(name="employeeId")Long employeeId){
        log.info("initiating endpoint to fetch employee by employeeID:: {}", employeeId);
        if(Objects.isNull(employeeId)){
            throw new CustomException(CatchableErrors.BAD_REQUEST);
        }
        MiniUserResponseDto responseDto = employeeService.viewEmployeeById(employeeId);
        if(Objects.isNull(responseDto)){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/view/searchAndOrder/")
    private ResponseEntity searchAndSortEmployee(@Validated@RequestBody SearchAndOrderEmployee employee){
        log.info("initiating endpoint to fetch employee by employeeID:: ");
        List<MiniUserResponseDto> userResponseDto = employeeService.searchViaRequest(employee);
        if(Objects.isNull(userResponseDto)){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    private ResponseEntity addNewEmployee(@Validated @RequestBody EmployeeRequestDto requestDto){
        log.info("initiating endpoint to Add employee:: ");
        NewEmployeeResponse userResponseDto = employeeService.addNewEmployee(requestDto);
        if(Objects.isNull(userResponseDto)){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{employeeId}")
    private ResponseEntity updateEmployee(@PathVariable(name = "employeeID")Long employeeId, @RequestBody EmployeeRequestDto requestDto){
        log.info("initiating endpoint to update employee by employeeID:: ");

        if(Objects.isNull(employeeId) || Objects.isNull(requestDto)){
            throw new CustomException(CatchableErrors.BAD_REQUEST);
        }
        MiniUserResponseDto responseDto = employeeService.updateEmployee(employeeId, requestDto);
        if (Objects.isNull(responseDto)) {
          return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{employeeId}")
    private ResponseEntity deleteEmployee(@PathVariable(name = "employeeId")Long employeeId){
        log.info("initiating endpoint to delete employee by employeeID:: ");
        boolean deleted = employeeService.softDelete(employeeId);
        if(!deleted){
            return new ResponseEntity<>("NOT DELETD",HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }

    /**
     * this section covers all employee address endpoints
     *
     *
     * all the return are in the form of miniuserResponse as it will have address data in it as well
     */

    @GetMapping("/{employeeId}/address")
    private ResponseEntity getAddressOfEmployee(@PathVariable(name = "employeeId")Long employeeId){
        log.info("initiating endpoint to fetch all available address of employee:: {}", employeeId);
        if(Objects.isNull(employeeId)){
            throw new CustomException(CatchableErrors.BAD_REQUEST);
        }
        MiniUserResponseDto responseDto = employeeService.viewEmployeeAddresses(employeeId);
        if (Objects.isNull(responseDto)) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @PostMapping("/{employeeId}/address/add")
    private ResponseEntity addNewAddress(@PathVariable(name = "employeeId")Long employeeId, @Validated @RequestBody AddressRequestDto requestDto){
        log.info("initiating endpoint to add new Address to employee:: {}", employeeId);
        if(Objects.isNull(employeeId)){
            throw new CustomException(CatchableErrors.BAD_REQUEST);
        }
        MiniUserResponseDto responseDto = employeeService.addNewEmployeeAddress(employeeId, requestDto);
        if (Objects.isNull(responseDto)) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @PutMapping("/{employeeId}/address/update/{addressId}")
    private ResponseEntity updateAddress(@PathVariable(name = "employeeId")Long employeeId,
                                         @PathVariable(name = "addressId") Long addressId,
                                         @Validated @RequestBody AddressRequestDto requestDto){
        log.info("initiating endpoint to update Address to employee:: {}  and address id :: {}", employeeId, addressId);
        if(Objects.isNull(employeeId)){
            throw new CustomException(CatchableErrors.BAD_REQUEST);
        }
        MiniUserResponseDto responseDto = employeeService.updateEmployeeAddresses(employeeId, requestDto, addressId);
        if (Objects.isNull(responseDto)) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}/address/delete/{addressId}")
    private ResponseEntity deleteAddress(@PathVariable(name = "employeeId")Long employeeId, @PathVariable(name = "addressId")Long addressId){
        log.info("initiating endpoint to delete Address to employee:: {}", employeeId);
        if(Objects.isNull(employeeId)){
            throw new CustomException(CatchableErrors.BAD_REQUEST);
        }
        boolean deleted = employeeService.deleteEmployeeAddresses(employeeId, addressId);
        if (Objects.isNull(deleted)) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Deleted", HttpStatus.OK);

    }






}
