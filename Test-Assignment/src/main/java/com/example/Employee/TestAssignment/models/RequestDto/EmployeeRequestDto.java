package com.example.Employee.TestAssignment.models.RequestDto;

import com.example.Employee.TestAssignment.models.entity.Address;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Validated
@NoArgsConstructor
@Setter
@Getter
public class EmployeeRequestDto {
    @NonNull
    private String fullName;

    @NonNull
    private String email;

    @NonNull
    private List<Address> addressList;

    @NonNull
    private Long mobile;
}
