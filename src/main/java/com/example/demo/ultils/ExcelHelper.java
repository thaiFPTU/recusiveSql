package com.example.demo.ultils;

import com.example.demo.dto.request.EmployeeExcelRequest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {
    public static List<EmployeeExcelRequest> convertExcelToEmployeeList(MultipartFile file) {
        List<EmployeeExcelRequest> employeeList = new ArrayList<>();
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);

            int rowNumber = 0;
            for (Row row : sheet) {
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                EmployeeExcelRequest employee = new EmployeeExcelRequest();
                employee.setName(row.getCell(0).getStringCellValue());
                System.out.println(row.getCell(0).getStringCellValue());
                employee.setManagerId(row.getCell(1).getCellType() != null ? Long.parseLong(row.getCell(1).getStringCellValue()) : null);
                employee.setSalary((int) row.getCell(2).getNumericCellValue());
                employee.setDesignation(row.getCell(3).getStringCellValue());
                System.out.println(row.getCell(3).getStringCellValue());
                employee.setEmail(row.getCell(4).getStringCellValue());
                employee.setPhone(row.getCell(5).getStringCellValue());
                employeeList.add(employee);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }
}
