package com.example.schoolmanagement.service;

import com.example.schoolmanagement.repository.MarkRepository;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

    public ByteArrayOutputStream generateExcelSheet() throws IOException {

        List<Object[]> studentDetails = this.markRepository.generatePdf();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Retrieve Student Details");

        Font boldfont = workbook.createFont();
        boldfont.setBold(true);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THICK);
        cellStyle.setBorderBottom(BorderStyle.THICK);
        cellStyle.setBorderRight(BorderStyle.THICK);
        cellStyle.setBorderLeft(BorderStyle.THICK);
        cellStyle.setFont(boldfont);

        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("STUDENT ID");
        row.createCell(1).setCellValue("STUDENT NAME");
        row.createCell(2).setCellValue("STANDARD ID");
        row.createCell(3).setCellValue("STUDENT ATTENDANCE PERCENTAGE");
        row.createCell(4).setCellValue("TEACHER NAME");
        row.createCell(5).setCellValue("EXAM NAME");
        row.createCell(6).setCellValue("TOTAL MARKS");

        for (int i = 0; i < 7; i++) {
            row.getCell(i).setCellStyle(cellStyle);
        }

        int dataRowIndex = 1;

        for (Object[] data : studentDetails) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue((String) data[0]);
            dataRow.createCell(1).setCellValue((String) data[1]);
            dataRow.createCell(2).setCellValue((String) data[2]);
            dataRow.createCell(3).setCellValue(data[3].toString());
            dataRow.createCell(4).setCellValue((String) data[4]);
            dataRow.createCell(5).setCellValue((String) data[5]);
            dataRow.createCell(6).setCellValue(data[6].toString());

        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();

        return byteArrayOutputStream;
    }

    public byte[] generate() throws IOException {

        List<Object[]> studentDetails = this.markRepository.generatePdf();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sample Data");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Student ID", "Student Name", "Standard Id","Student Attendance Percentage","Teacher name","Exam Name","Total Marks"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderStyle(workbook));
        }
        int dataRowIndex = 1;

        for (Object[] data : studentDetails) {
            Row dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue((String) data[0]);
            dataRow.createCell(1).setCellValue((String) data[1]);
            dataRow.createCell(2).setCellValue((String) data[2]);
            dataRow.createCell(3).setCellValue(data[3].toString());
            dataRow.createCell(4).setCellValue((String) data[4]);
            dataRow.createCell(5).setCellValue((String) data[5]);
            dataRow.createCell(6).setCellValue(data[6].toString());

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
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

}
