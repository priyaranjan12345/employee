package com.tut.employee.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private String email;
}
