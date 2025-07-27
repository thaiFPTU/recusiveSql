package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeExcelRequest {
    private String name;
    private Long managerId;
    private int salary;
    private String designation;
    private String email;
    private String phone;
}
