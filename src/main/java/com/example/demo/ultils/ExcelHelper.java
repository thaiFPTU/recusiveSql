package com.example.demo.ultils;

import com.example.demo.dto.request.EmployeeExcelRequest;
import com.example.demo.dto.response.EmployeeResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {
    private final Workbook workbook = new XSSFWorkbook();
    private final Sheet sheet = workbook.createSheet("Employees");

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
                employee.setManagerId(row.getCell(1).getCellType() != null ? (long) row.getCell(1).getNumericCellValue() : null);
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

    public void writeHeader() {
        Row headerRow = sheet.createRow(0);
       CellStyle headerStyle = createHeaderStyle();
        String[] headers = {"ID","Name", "ReportTo","Designation"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(headers[i]);
        }
    }
    public void writeData(List<EmployeeResponse> employeeList) {
        int rowIndex = 1;
        for (EmployeeResponse employee : employeeList) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getName());
            if (employee.getManagerId() != null) {row.createCell(2).setCellValue(employee.getManagerId());}
            row.createCell(3).setCellValue(employee.getDesignation());
        }
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    private CellStyle createHeaderStyle() {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
    public void convertEmployeeListToExcel(List<EmployeeResponse> employeeList, HttpServletResponse response) throws IOException {
        writeHeader();
        writeData(employeeList);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
