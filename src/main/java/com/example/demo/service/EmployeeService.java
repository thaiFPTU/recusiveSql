package com.example.demo.service;

import com.example.demo.common.BaseResponse;

public interface EmployeeService {
BaseResponse<?> findAllEmloyeeUnderById(Long employeeId);
}
