package com.example.schoolmanagement.service;

import com.example.schoolmanagement.repository.MarkRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FileGenerateService {

    @Autowired
    private MarkRepository markRepository;

    public byte[] generate() throws IOException {

        List<Object[]> studentDetails = this.markRepository.generatePdf();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sample Data");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Student ID", "Student Name", "Standard Id", "Student Attendance Percentage", "Teacher name", "Exam Name", "Total Marks"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderStyle(workbook));
        }
        int dataRowIndex = 1;

        for (Object[] data : studentDetails) {
            Row dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(((String) data[0]).toLowerCase());
            dataRow.createCell(1).setCellValue(((String) data[1]).toLowerCase());
            dataRow.createCell(2).setCellValue(((String) data[2]).toLowerCase());
            dataRow.createCell(3).setCellValue((data[3].toString().toLowerCase()));
            dataRow.createCell(4).setCellValue(((String) data[4]).toLowerCase());
            dataRow.createCell(5).setCellValue(((String) data[5]).toLowerCase());
            dataRow.createCell(6).setCellValue((data[6].toString()).toLowerCase());

        }
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();

    }


    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        style.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

}
