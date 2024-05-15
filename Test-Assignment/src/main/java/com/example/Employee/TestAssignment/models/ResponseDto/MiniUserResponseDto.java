package com.example.Employee.TestAssignment.models.ResponseDto;

import com.example.Employee.TestAssignment.models.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MiniUserResponseDto {

    private Long id;
    private String fullName;
    private String email;
    private String username;
    private List<Address> addressList;
    private Long mobile;

}
