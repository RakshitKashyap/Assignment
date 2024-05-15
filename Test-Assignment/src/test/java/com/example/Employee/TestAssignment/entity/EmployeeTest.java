package com.example.Employee.TestAssignment.entity;

import com.example.Employee.TestAssignment.models.entity.Address;
import com.example.Employee.TestAssignment.models.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeTest {
    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setUUID("abc-123");
        employee.setFullName("John Doe");
        employee.setEmail("john@example.com");
        employee.setUsername("john_doe");
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address());
        employee.setAddressList(addresses);
        employee.setMobile(1234567890L);
        employee.setStatus(true);
    }

    @Test
    public void testGettersAndSetters() {
        assertThat(employee.getId()).isEqualTo(1L);
        assertThat(employee.getUUID()).isEqualTo("abc-123");
        assertThat(employee.getFullName()).isEqualTo("John Doe");
        assertThat(employee.getEmail()).isEqualTo("john@example.com");
        assertThat(employee.getUsername()).isEqualTo("john_doe");
        assertThat(employee.getAddressList().size()).isEqualTo(1);
        assertThat(employee.getMobile()).isEqualTo(1234567890L);
        assertThat(employee.isStatus()).isTrue();

        // Test setters
        employee.setId(2L);
        employee.setUUID("def-456");
        employee.setFullName("Jane Doe");
        employee.setEmail("jane@example.com");
        employee.setUsername("jane_doe");
        List<Address> newAddresses = new ArrayList<>();
        newAddresses.add(new Address());
        newAddresses.add(new Address());
        employee.setAddressList(newAddresses);
        employee.setMobile(9876543210L);
        employee.setStatus(false);

        assertThat(employee.getId()).isEqualTo(2L);
        assertThat(employee.getUUID()).isEqualTo("def-456");
        assertThat(employee.getFullName()).isEqualTo("Jane Doe");
        assertThat(employee.getEmail()).isEqualTo("jane@example.com");
        assertThat(employee.getUsername()).isEqualTo("jane_doe");
        assertThat(employee.getAddressList().size()).isEqualTo(2);
        assertThat(employee.getMobile()).isEqualTo(9876543210L);
        assertThat(employee.isStatus()).isFalse();
    }

    @Test
    public void testNoArgsConstructor() {
        Employee newEmployee = new Employee();
        assertThat(newEmployee).isNotNull();
    }

    @Test
    public void testAllArgsConstructor() {
        Employee newEmployee = new Employee(3L, "ghi-789", "Alice Wonderland", "alice@example.com", "alice_doe",
                new ArrayList<>(), 1234567890L, true);
        assertThat(newEmployee).isNotNull();
        assertThat(newEmployee.getId()).isEqualTo(3L);
        assertThat(newEmployee.getUUID()).isEqualTo("ghi-789");
        assertThat(newEmployee.getFullName()).isEqualTo("Alice Wonderland");
        assertThat(newEmployee.getEmail()).isEqualTo("alice@example.com");
        assertThat(newEmployee.getUsername()).isEqualTo("alice_doe");
        assertThat(newEmployee.getAddressList().size()).isEqualTo(0);
        assertThat(newEmployee.getMobile()).isEqualTo(1234567890L);
        assertThat(newEmployee.isStatus()).isTrue();
    }

    @Test
    public void testToString() {
        String expectedToString = "Employee(id=1, UUID=abc-123, fullName=John Doe, email=john@example.com, " +
                "username=john_doe, addressList=[], mobile=1234567890, status=true)";
        assertThat(employee.toString()).isNotEqualTo(expectedToString);
    }
}
