package com.example.Employee.TestAssignment.repository;

import com.example.Employee.TestAssignment.models.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindByFullNameAndStatusOrderByFullNameAsc() {
        // Test case for findByFullNameAndStatusOrderByFullNameAsc method
        // Given
        String fullName = "John Doe";
        boolean status = true;
        Employee employee = new Employee();
        employee.setFullName(fullName);
        employee.setStatus(status);
        employeeRepository.save(employee);

        // When
        List<Employee> foundEmployees = employeeRepository.findByFullNameAndStatusOrderByFullNameAsc(fullName, status);

        // Then
        assertNotNull(foundEmployees);
        assertTrue(foundEmployees.size() > 0);
        assertEquals(fullName, foundEmployees.get(0).getFullName());
    }

    @Test
    public void testFindByFullNameAndStatusOrderByFullNameDesc() {
        // Test case for findByFullNameAndStatusOrderByFullNameDesc method
        // Given
        String fullName = "Jane Doe";
        boolean status = true;
        Employee employee = new Employee();
        employee.setFullName(fullName);
        employee.setStatus(status);
        employeeRepository.save(employee);

        // When
        List<Employee> foundEmployees = employeeRepository.findByFullNameAndStatusOrderByFullNameDesc(fullName, status);

        // Then
        assertNotNull(foundEmployees);
        assertTrue(foundEmployees.size() > 0);
        assertEquals(fullName, foundEmployees.get(0).getFullName());
    }

    @Test
    public void testFindByStatus() {
        // Test case for findByStatus method
        // Given
        boolean status = true;
        Employee employee = new Employee();
        employee.setStatus(status);
        employeeRepository.save(employee);

        // When
        List<Employee> foundEmployees = employeeRepository.findByStatus(status);

        // Then
        assertNotNull(foundEmployees);
        assertTrue(foundEmployees.size() > 0);
        assertEquals(status, foundEmployees.get(0).isStatus());
    }

    @Test
    public void testFindByIdAndStatus() {
        // Test case for findByIdAndStatus method
        // Given
        boolean status = true;
        Employee employee = new Employee();
        employee.setStatus(status);
        Employee savedEmployee = employeeRepository.save(employee);

        // When
        Optional<Employee> foundEmployeeOptional = employeeRepository.findByIdAndStatus(savedEmployee.getId(), status);

        // Then
        assertTrue(foundEmployeeOptional.isPresent());
        assertEquals(status, foundEmployeeOptional.get().isStatus());
    }
}

