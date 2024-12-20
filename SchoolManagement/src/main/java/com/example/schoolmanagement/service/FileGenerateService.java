package com.example.schoolmanagement.service;

import com.example.schoolmanagement.repository.MarkRepository;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("STUDENT_ID");
        row.createCell(1).setCellValue("STUDENT_NAME");
        row.createCell(2).setCellValue("STANDARD_ID");
        row.createCell(3).setCellValue("STUDENT_ATTENDANCE_PERCENTAGE");
        row.createCell(4).setCellValue("TEACHER_NAME");
        row.createCell(5).setCellValue("EXAM_NAME");
        row.createCell(6).setCellValue("TOTAL_MARKS");

        int dataRowIndex = 1;

        for (Object[] data : studentDetails) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue((String) data[0]);
            dataRow.createCell(1).setCellValue((String) data[1]);
            dataRow.createCell(2).setCellValue((String) data[2]);
            dataRow.createCell(3).setCellValue( data[3].toString());
            dataRow.createCell(4).setCellValue((String) data[4]);
            dataRow.createCell(5).setCellValue((String) data[5]);
            dataRow.createCell(6).setCellValue( data[6].toString());

        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();

        return byteArrayOutputStream;
    }
}
