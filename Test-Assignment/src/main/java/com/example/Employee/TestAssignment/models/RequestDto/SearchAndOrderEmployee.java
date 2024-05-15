package com.example.Employee.TestAssignment.models.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SearchAndOrderEmployee {

    @NonNull
    private String name;

    private boolean ascending;
}
