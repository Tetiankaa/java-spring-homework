package com.example.javaspringhomework.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailService {
  private final JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String mailFrom;

  public void sendEmail(String receiver, String text, String subject, Optional<Path> imagePath, String attachmentName) throws MessagingException, IOException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

    mimeMessageHelper.setTo(receiver);
    mimeMessageHelper.setFrom(mailFrom);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(text);

    if (imagePath.isPresent()){
      byte[] bytes = Files.readAllBytes(imagePath.get());

      ByteArrayDataSource dataSource = new ByteArrayDataSource(bytes, "image/png");
      mimeMessageHelper.addAttachment(attachmentName,dataSource);
    }

    mailSender.send(mimeMessage);
  }
}
