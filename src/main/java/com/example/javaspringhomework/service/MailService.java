package com.example.javaspringhomework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
  private final MailSender mailSender;

  @Value("${spring.mail.username}")
  private String mailFrom;
  public void sendEmail(String receiver, String text, String subject){
    SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject(subject);
    message.setText(text);
    message.setTo(receiver);
    message.setFrom(mailFrom);

    mailSender.send(message);
  }
}
