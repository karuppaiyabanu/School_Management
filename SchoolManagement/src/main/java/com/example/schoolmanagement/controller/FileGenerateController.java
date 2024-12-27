package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.repository.MarkRepository;
import com.example.schoolmanagement.service.FileGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileGenerateController {

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private FileGenerateService fileGenerateService;

    @GetMapping("/")
    public Object get() {
        return this.markRepository.generatePdf();
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> generateFile() throws IOException {
        ByteArrayOutputStream excelFile = this.fileGenerateService.generateExcelSheet();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=student attendance.xls");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(excelFile.toByteArray());
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadExcel() {
        try {
            byte[] excelFile = this.fileGenerateService.generate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=sample-data.xlsx");
            return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
