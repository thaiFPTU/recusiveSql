package com.example.demo.controller;

import com.example.demo.common.BaseResponse;
import com.example.demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("/getAllEmployeeUnderById/{employeeId}")
    public ResponseEntity<?> getAllEmployeeUnderById(@PathVariable Long employeeId) {
            return ResponseEntity.ok().body(employeeService.findAllEmloyeeUnderById(employeeId));
    }
}
