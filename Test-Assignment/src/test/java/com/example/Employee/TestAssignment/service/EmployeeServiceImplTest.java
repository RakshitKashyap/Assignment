package com.example.Employee.TestAssignment.service;

import com.example.Employee.TestAssignment.exceptions.CustomException;
import com.example.Employee.TestAssignment.models.RequestDto.AddressRequestDto;
import com.example.Employee.TestAssignment.models.RequestDto.EmployeeRequestDto;
import com.example.Employee.TestAssignment.models.RequestDto.SearchAndOrderEmployee;
import com.example.Employee.TestAssignment.models.RequestDto.UserCreateDto;
import com.example.Employee.TestAssignment.models.ResponseDto.MiniUserResponseDto;
import com.example.Employee.TestAssignment.models.ResponseDto.NewEmployeeResponse;
import com.example.Employee.TestAssignment.models.entity.Address;
import com.example.Employee.TestAssignment.models.entity.Employee;
import com.example.Employee.TestAssignment.repository.EmployeeRepository;
import com.example.Employee.TestAssignment.repository.AddressRepository;
import com.example.Employee.TestAssignment.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserService userService;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testViewAllEmployees_Success() {
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFullName("John Doe");
        employees.add(employee1);

        when(employeeRepository.findByStatus(true)).thenReturn(employees);

        List<MiniUserResponseDto> result = employeeService.viewAll();

        assertEquals(1, result.size());
        assertEquals(employee1.getFullName(), result.get(0).getFullName());
    }

    @Test
    public void testViewAllEmployees_NoEmployeesFound() {
        // Test case for viewAll method when no employees are found
        when(employeeRepository.findByStatus(true)).thenReturn(new ArrayList<>());

        assertThrows(CustomException.class, () -> {
            employeeService.viewAll();
        });
    }

    @Test
    public void testViewEmployeeById_Success() {
        // Test case for viewEmployeeById method when employee is found
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFullName("John Doe");

        when(employeeRepository.findByIdAndStatus(employeeId, true)).thenReturn(Optional.of(employee));

        MiniUserResponseDto result = employeeService.viewEmployeeById(employeeId);

        assertEquals(employee.getFullName(), result.getFullName());
    }

    @Test
    public void testViewEmployeeById_EmployeeNotFound() {
        // Test case for viewEmployeeById method when employee is not found
        Long employeeId = 1L;

        when(employeeRepository.findByIdAndStatus(employeeId, true)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> {
            employeeService.viewEmployeeById(employeeId);
        });
    }

    @Test
    public void testSearchAndSortEmployee_Success() {
        // Test case for searchAndSortEmployee method
        SearchAndOrderEmployee request = new SearchAndOrderEmployee("John Doe", true);

        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFullName("John Doe");
        employees.add(employee);

        when(employeeRepository.findByStatus(true)).thenReturn(employees);

        List<MiniUserResponseDto> result = employeeService.searchViaRequest(request);

        assertEquals(1, result.size());
        assertEquals(employee.getFullName(), result.get(0).getFullName());
    }

    @Test
    public void testAddNewEmployee_Success() {
        // Test case for addNewEmployee method
        EmployeeRequestDto request = new EmployeeRequestDto();
        request.setFullName("John Doe");
        request.setEmail("john@example.com");

        Employee savedEmployee = new Employee();
        savedEmployee.setId(1L);
        savedEmployee.setFullName("John Doe");

        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
        when(userService.registerNewUser(any(UserCreateDto.class))).thenReturn("Registered");

        NewEmployeeResponse result = employeeService.addNewEmployee(request);

        assertEquals(savedEmployee.getFullName(), result.getFullName());
        assertNotNull(result.getUser());
    }

    @Test
    public void testSoftDelete_Success() {
        // Test case for softDelete method
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setStatus(true);

        when(employeeRepository.findByIdAndStatus(employeeId, true)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        boolean result = employeeService.softDelete(employeeId);

        assertTrue(result);
        assertFalse(employee.isStatus());
    }

    @Test
    public void testAddNewEmployeeAddress_Success() {
        // Test case for addNewEmployeeAddress method
        Long employeeId = 1L;
        AddressRequestDto requestDto = new AddressRequestDto();
        requestDto.setTitle("Home");
        requestDto.setAddress1("123 Main St");
        requestDto.setCity("City");
        requestDto.setState("State");
        requestDto.setZipCode("12345");

        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(addressRepository.save(any(Address.class))).thenAnswer(invocation -> {
            Address address = invocation.getArgument(0);
            address.setId(1L); // Set address ID
            return address;
        });

        MiniUserResponseDto result = employeeService.addNewEmployeeAddress(employeeId, requestDto);

        assertNotNull(result);
        assertEquals(requestDto.getTitle(), result.getAddressList().get(0).getTitle());
    }

    @Test
    public void testAddNewEmployeeAddress_EmployeeNotFound() {
        // Test case for addNewEmployeeAddress method when employee is not found
        Long employeeId = 1L;
        AddressRequestDto requestDto = new AddressRequestDto();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> {
            employeeService.addNewEmployeeAddress(employeeId, requestDto);
        });
    }

    @Test
    public void testUpdateEmployeeAddresses_Success() {
        // Test case for updateEmployeeAddresses method
        Long employeeId = 1L;
        Long addressId = 1L;
        AddressRequestDto requestDto = new AddressRequestDto();
        requestDto.setTitle("Home");
        requestDto.setAddress1("123 Main St");
        requestDto.setCity("City");
        requestDto.setState("State");
        requestDto.setZipCode("12345");

        Employee employee = new Employee();
        employee.setId(employeeId);

        Address address = new Address();
        address.setId(addressId);

        employee.getAddressList().add(address);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        MiniUserResponseDto result = employeeService.updateEmployeeAddresses(employeeId, requestDto, addressId);

        assertNotNull(result);
        assertEquals(requestDto.getTitle(), result.getAddressList().get(0).getTitle());
    }

    @Test
    public void testUpdateEmployeeAddresses_EmployeeNotFound() {
        // Test case for updateEmployeeAddresses method when employee is not found
        Long employeeId = 1L;
        Long addressId = 1L;
        AddressRequestDto requestDto = new AddressRequestDto();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> {
            employeeService.updateEmployeeAddresses(employeeId, requestDto, addressId);
        });
    }

    @Test
    public void testUpdateEmployeeAddresses_AddressNotFound() {
        // Test case for updateEmployeeAddresses method when address is not found
        Long employeeId = 1L;
        Long addressId = 1L;
        AddressRequestDto requestDto = new AddressRequestDto();

        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        assertThrows(CustomException.class, () -> {
            employeeService.updateEmployeeAddresses(employeeId, requestDto, addressId);
        });
    }


    @Test
    public void testDeleteEmployeeAddresses_Success() {
        // Test case for deleteEmployeeAddresses method
        Long employeeId = 1L;
        Long addressId = 1L;

        Employee employee = new Employee();
        employee.setId(employeeId);

        Address address = new Address();
        address.setId(addressId);

        employee.getAddressList().add(address);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        boolean result = employeeService.deleteEmployeeAddresses(employeeId, addressId);

        assertTrue(result);
        assertFalse(address.isStatus());
    }

    @Test
    public void testDeleteEmployeeAddresses_EmployeeNotFound() {
        // Test case for deleteEmployeeAddresses method when employee is not found
        Long employeeId = 1L;
        Long addressId = 1L;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> {
            employeeService.deleteEmployeeAddresses(employeeId, addressId);
        });
    }

    @Test
    public void testDeleteEmployeeAddresses_AddressNotFound() {
        // Test case for deleteEmployeeAddresses method when address is not found
        Long employeeId = 1L;
        Long addressId = 1L;

        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        assertThrows(CustomException.class, () -> {
            employeeService.deleteEmployeeAddresses(employeeId, addressId);
        });
    }




}

