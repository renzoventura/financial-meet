package com.financialmeet.service.impl;

import com.financialmeet.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

  private JavaMailSender javaMailSender;

  @Autowired
  public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Override
  @Async
  public void sendEmail(SimpleMailMessage email) {
    javaMailSender.send(email);

  }
}
