package com.example.schoolmanagement.service;


import com.example.schoolmanagement.repository.MarkRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private FileGenerateService fileGenerateService;

    @Autowired
    private MarkRepository markRepository;

    @Value("$(spring.mail.username)")
    private String fromEmailId;

    @Scheduled(fixedRate = 900000)
    public void generateXlsxFile() throws IOException, MessagingException {

        byte[] xlxFile = fileGenerateService.generate();

        sendEmail(xlxFile);
    }

    public void sendEmail(byte[] file) throws MessagingException {

        String subject = "Student Attendance Details With Marks Reports";

        String body = "Dear Principal,\n" +
                "I hope this message finds you well. Attached is the student report for students with high attendance percentages.";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmailId);
        helper.setTo("achudha.senthilkumar@fyndus.com");
        helper.setSubject(subject);
        helper.setText(body);
        helper.addAttachment("StudentDetails.xlsx", new ByteArrayResource(file, "application/octet-stream"));

        javaMailSender.send(message);
    }

}
