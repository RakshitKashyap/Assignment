package com.example.Employee.TestAssignment.repository;

import com.example.Employee.TestAssignment.models.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {}
