package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Log4j2
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
  private JavaMailSender javaMailSender;
  @Override
  @Async
  public void send(String to, String emailText, String subject) {
    try {
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
      helper.setText(emailText, true);
      helper.setTo(to);
      helper.setSubject(subject);
      javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
      log.error("Failed to send email", e);
      throw new UserException(UserExceptionType.EMAIL_FAILED_TO_SEND);
    }
  }
}
