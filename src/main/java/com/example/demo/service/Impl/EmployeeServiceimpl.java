package com.example.demo.service.Impl;

import com.example.demo.common.BaseResponse;
import com.example.demo.contant.EmployeeConstant;
import com.example.demo.dto.request.EmployeeExcelRequest;
import com.example.demo.dto.response.EmployeeResponse;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.ultils.ExcelHelper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceimpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public BaseResponse<?> findAllEmloyeePathById(Long employeeId) {
        if (employeeId == null) {
            return new BaseResponse<>(
                    400,
                    EmployeeConstant.EMPLOYEE_ID_NULL,
                    null
            );
        }
        List<EmployeeResponse> employeeResponseList = employeeRepository.findAllEmployeeInPath(employeeId);
        if (employeeResponseList.isEmpty()) {
            return new BaseResponse<>(
                    404,
                    EmployeeConstant.EMPLOYEE_LIST_EMPTY,
                    null
            );
        }
        return new BaseResponse<>(200, EmployeeConstant.EMPLOYEE_LIST_SUCCESS, employeeResponseList);
    }

    @Override
    public BaseResponse<?> importEmployeeFromExcel(MultipartFile file) {
        List<Employee> employeeList = new ArrayList<>();
        List<EmployeeExcelRequest> employeeExcelRequests = ExcelHelper.convertExcelToEmployeeList(file);
        if (employeeExcelRequests.isEmpty()) {
            return new BaseResponse<>(
                    400,
                    EmployeeConstant.EMPLOYEE_EXCEL_EMPTY,
                    null
            );
        }
        for (EmployeeExcelRequest employeeExcelRequest : employeeExcelRequests) {
            Employee employee = new Employee();
            employee.setName(employeeExcelRequest.getName());
            employee.setManagerId(employeeExcelRequest.getManagerId());
            employee.setSalary(employeeExcelRequest.getSalary());
            employee.setDesignation(employeeExcelRequest.getDesignation());
            employee.setEmail(employeeExcelRequest.getEmail());
            employee.setPhone(employeeExcelRequest.getPhone());
            employeeList.add(employee);
        }
        employeeRepository.saveAll(employeeList);
        return new BaseResponse<>(
                200,
                "Employees imported successfully",
                employeeList
        );
    }

    @Override
    public BaseResponse<?> exportEmployeePathToExcel(Long employeeId, HttpServletResponse response) throws IOException {
        if (employeeId == null) {
            return new BaseResponse<>(
                    400,
                    EmployeeConstant.EMPLOYEE_ID_NULL,
                    null
            );
        }
        List<EmployeeResponse> employeeResponseList = employeeRepository.findAllEmployeeInPath(employeeId);
        if (employeeResponseList.isEmpty()) {
            return new BaseResponse<>(
                    404,
                    EmployeeConstant.EMPLOYEE_LIST_EMPTY,
                    null
            );
        }
        ExcelHelper excelHelper = new ExcelHelper();
        excelHelper.convertEmployeeListToExcel(employeeResponseList,response);
        return new BaseResponse<>(200, EmployeeConstant.EMPLOYEE_LIST_SUCCESS, employeeResponseList);
    }

}
