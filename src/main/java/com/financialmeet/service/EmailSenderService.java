package com.financialmeet.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {

  void sendEmail(SimpleMailMessage email);
}
