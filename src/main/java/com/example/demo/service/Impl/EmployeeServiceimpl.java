package com.example.demo.service.Impl;

import com.example.demo.common.BaseResponse;
import com.example.demo.contant.EmployeeConstant;
import com.example.demo.dto.response.EmployeeResponse;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceimpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public BaseResponse<?> findAllEmloyeeUnderById(Long employeeId) {
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
}
