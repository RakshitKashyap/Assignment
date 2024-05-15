package com.example.Employee.TestAssignment.models.RequestDto;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Validated
public class AddressRequestDto {

    @NonNull
    private String title;
    @NonNull
    private String address1;
    @NonNull
    private String city;
    @NonNull
    private String state;
    @NonNull
    private String zipCode;
}
