package com.example.demo.controller;

import com.example.demo.common.BaseResponse;
import com.example.demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("/getAllEmployeeUnderById/{employeeId}")
    public ResponseEntity<?> getAllEmployeeUnderById(@PathVariable Long employeeId) {
            return ResponseEntity.ok().body(employeeService.findAllEmloyeeUnderById(employeeId));
    }
    @PostMapping("/upload")
    public ResponseEntity<?> importEmployeeFromExcel(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(employeeService.importEmployeeFromExcel(file));
    }
}
