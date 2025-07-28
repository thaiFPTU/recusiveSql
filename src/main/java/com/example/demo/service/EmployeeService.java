package com.example.demo.service;

import com.example.demo.common.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EmployeeService {
    BaseResponse<?> findAllEmloyeePathById(Long employeeId);

    BaseResponse<?> importEmployeeFromExcel(MultipartFile file);
    BaseResponse<?> exportEmployeePathToExcel(Long employeeId, HttpServletResponse response) throws IOException;
}
