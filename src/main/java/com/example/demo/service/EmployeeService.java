package com.example.demo.service;

import com.example.demo.common.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {
    BaseResponse<?> findAllEmloyeeUnderById(Long employeeId);

    BaseResponse<?> importEmployeeFromExcel(MultipartFile file);
}
