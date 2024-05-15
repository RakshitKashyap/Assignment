package com.example.Employee.TestAssignment.service.impl;

import com.example.Employee.TestAssignment.exceptions.CustomException;
import com.example.Employee.TestAssignment.models.RequestDto.AddressRequestDto;
import com.example.Employee.TestAssignment.models.RequestDto.EmployeeRequestDto;
import com.example.Employee.TestAssignment.models.RequestDto.SearchAndOrderEmployee;
import com.example.Employee.TestAssignment.models.RequestDto.UserCreateDto;
import com.example.Employee.TestAssignment.models.ResponseDto.MiniUserResponseDto;
import com.example.Employee.TestAssignment.models.ResponseDto.NewEmployeeResponse;
import com.example.Employee.TestAssignment.models.entity.Address;
import com.example.Employee.TestAssignment.models.entity.Employee;
import com.example.Employee.TestAssignment.models.entity.User;
import com.example.Employee.TestAssignment.repository.AddressRepository;
import com.example.Employee.TestAssignment.repository.EmployeeRepository;
import com.example.Employee.TestAssignment.service.EmployeeService;
import com.example.Employee.TestAssignment.service.UserService;
import com.example.Employee.TestAssignment.utils.CatchableErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressRepository addressRepos;

    @Override
    public List<MiniUserResponseDto> viewAll() {
        List<Employee> employees =  employeeRepository.findByStatus(true);
        if(Objects.isNull(employees) || employees.isEmpty()){
            throw new CustomException(CatchableErrors.NO_RECORDS_AVAILABLE);
        }
        return employees.stream().map(employee->convertToUserResponse(employee)).collect(Collectors.toList());
    }

    private MiniUserResponseDto convertToUserResponse(Employee employee) {
        MiniUserResponseDto responseDto = new MiniUserResponseDto();
        BeanUtils.copyProperties(employee, responseDto);
        return responseDto;
    }

    @Override
    public MiniUserResponseDto viewEmployeeById(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findByIdAndStatus(employeeId, true);
        if(employee.isEmpty()){
            throw new CustomException(CatchableErrors.INVALID_INPUT);
        }

        return convertToUserResponse(employee.get());
    }

    @Override
    public List<MiniUserResponseDto> searchViaRequest(SearchAndOrderEmployee employeeRequest) {
        /**
         * i'm proposing two solution in search employee by name and sort order
         *
         * 1. using jpa projection
         *
         * 2. using stream & collection api
         */

        if(Objects.isNull(employeeRequest) || employeeRequest.getName().trim().isEmpty()){
            throw new CustomException(CatchableErrors.INVALID_INPUT);
        }

        //Solution 1

//        List<Employee> employees = new ArrayList<>();
//        if(employeeRequest.isAscending()){
//            employees = employeeRepository.findByFullNameAndStatusOrderByFullNameAsc(employeeRequest.getName());
//        }
//        else if(!employeeRequest.isAscending()){
//            employees = employeeRepository.findByFullNameAndStatusOrderByFullNameDesc(employeeRequest.getName());
//        }
//        if(employees.isEmpty()){
//            return null;
//        }
//        return employees.stream().map(employee -> convertToUserResponse(employee)).collect(Collectors.toList());

        // solution 2

        List<Employee> employees = employeeRepository.findByStatus(true);

        return employees.stream()   // taking entire list of existing employee
                .filter(employee -> employee.getFullName().equalsIgnoreCase(employeeRequest.getName()))// filter employee based on name ignoring cases
                .sorted(        // sorting the filtered list
                        employeeRequest.isAscending()    // checking for the order
                        ? Comparator.comparing(Employee::getFullName)  // if ascending
                        : Comparator.comparing(Employee::getFullName, Comparator.reverseOrder())  // if descending
                    )
                .map(employee -> convertToUserResponse(employee))  // map to list to desired DAO
                .collect(Collectors.toList());  //  returning the result
    }

    @Override
    public NewEmployeeResponse addNewEmployee(EmployeeRequestDto requestDto) {
        Employee employee = new Employee();
        employee.setUUID(UUID.randomUUID().toString());
        employee.setFullName(requestDto.getFullName());
        employee.setEmail(requestDto.getEmail());
        employee.setUsername(requestDto.getEmail());
        employee.setAddressList(requestDto.getAddressList());
        employee.setMobile(requestDto.getMobile());
        employee.setStatus(true);

        employee = employeeRepository.save(employee);

        boolean registerUser = initiateUserRegistration(employee);

        return convertToNewUserResponse(employee);
    }

    private NewEmployeeResponse convertToNewUserResponse(Employee employee) {
        NewEmployeeResponse response = new NewEmployeeResponse();
        BeanUtils.copyProperties(employee, response);
        User user = new User();
        user.setUsername(employee.getUsername());
        user.setEmail(employee.getEmail());
        user.setPassword(employee.getUsername()+"_passKey");
        user.setRole("");

        response.setUser(user);
        return response;
    }

    private boolean initiateUserRegistration(Employee employee) {
        UserCreateDto createDto = new UserCreateDto();
        createDto.setEmail(employee.getEmail());
        createDto.setUsername(employee.getUsername());
        createDto.setRole("NA");
        String result = userService.registerNewUser(createDto);
        if (Objects.nonNull(result)){
            return true;
        }
        return false;
    }

    @Override
    public boolean softDelete(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findByIdAndStatus(employeeId, true);
        if(employee.isEmpty()){
            throw new CustomException(CatchableErrors.INVALID_INPUT);
        }
        Employee emp = employee.get();
        emp.setStatus(false);
        employeeRepository.save(emp);

        return true;
    }

    @Override
    public MiniUserResponseDto updateEmployee(Long employeeId, EmployeeRequestDto requestDto) {
        Optional<Employee> employeeOptional = employeeRepository.findByIdAndStatus(employeeId, true);
        if(employeeOptional.isEmpty()){
            throw new CustomException(CatchableErrors.INVALID_INPUT);
        }
        Employee employee = employeeOptional.get();
        BeanUtils.copyProperties(requestDto, employee);
        employee = employeeRepository.save(employee);
        return convertToUserResponse(employee);
    }

    @Override
    public MiniUserResponseDto viewEmployeeAddresses(Long employeeId) {
        return viewEmployeeById(employeeId);
    }

    @Override
    public MiniUserResponseDto addNewEmployeeAddress(Long employeeId, AddressRequestDto requestDto) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        if(employeeOptional.isEmpty()){
            throw new CustomException(CatchableErrors.INVALID_INPUT);
        }
        Employee employee = employeeOptional.get();

        Address address = new Address();
        address.setTitle(requestDto.getTitle());
        address.setAddress1(requestDto.getAddress1());
        address.setCity(requestDto.getCity());
        address.setState(requestDto.getState());
        address.setZipCode(requestDto.getZipCode());
        address = addressRepos.save(address);
        employee.getAddressList().add(address);
        employee= employeeRepository.save(employee);


        return convertToUserResponse(employee);
    }

    @Override
    public MiniUserResponseDto updateEmployeeAddresses(Long employeeId, AddressRequestDto requestDto, Long addressId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        if(employeeOptional.isEmpty()){
            throw new CustomException(CatchableErrors.INVALID_INPUT);
        }
        Employee employee = employeeOptional.get();
        Optional<Address> addressOptional = employee.getAddressList().stream().filter(address -> address.getId().equals(addressId)).findFirst();
        if(addressOptional.isEmpty()){
            throw new CustomException(CatchableErrors.INVALID_INPUT);
        }
        Address address = addressOptional.get();
        address.setTitle(requestDto.getTitle());
        address.setAddress1(requestDto.getAddress1());
        address.setCity(requestDto.getCity());
        address.setState(requestDto.getState());
        address.setZipCode(requestDto.getZipCode());

        addressRepos.save(address);

        return convertToUserResponse(employee);

    }

    @Override
    public boolean deleteEmployeeAddresses(Long employeeId, Long addressId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        if(employeeOptional.isEmpty()){
            throw new CustomException(CatchableErrors.INVALID_INPUT);
        }
        Employee employee = employeeOptional.get();
        Optional<Address> addressOptional = employee.getAddressList().stream().filter(address -> address.getId().equals(addressId)).findFirst();
        if(addressOptional.isEmpty()){
            throw new CustomException(CatchableErrors.INVALID_INPUT);
        }
        Address address = addressOptional.get();
        address.setStatus(false);
        Address check = addressRepos.save(address);
        if(Objects.isNull(check)){
            return false;
        }
        return true;
    }
}
