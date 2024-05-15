package com.example.Employee.TestAssignment.models.ResponseDto;

import com.example.Employee.TestAssignment.models.entity.Address;
import com.example.Employee.TestAssignment.models.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class NewEmployeeResponse {
    private Long id;
    private String fullName;
    private String email;
    private String username;
    private List<Address> addressList;
    private Long mobile;

    private User user;
}
