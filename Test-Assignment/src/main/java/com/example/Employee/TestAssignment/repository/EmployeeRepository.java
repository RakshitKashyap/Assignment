package com.example.Employee.TestAssignment.repository;

import com.example.Employee.TestAssignment.models.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByFullNameAndStatusOrderByFullNameAsc(String fullName, boolean status);

    List<Employee> findByFullNameAndStatusOrderByFullNameDesc(String fullName , boolean status);

    List<Employee> findByStatus(boolean b);

    Optional<Employee> findByIdAndStatus(Long employeeId, boolean b);
}
