package com.example.Employee.TestAssignment.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String UUID;

    private String fullName;

    private String email;

    private String username;

    @OneToMany( cascade = CascadeType.ALL)
    private List<Address> addressList;

    private Long mobile;

    private boolean status;

}
