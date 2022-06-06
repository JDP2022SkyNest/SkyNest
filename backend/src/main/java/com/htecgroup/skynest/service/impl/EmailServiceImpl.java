package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.email.Email;
import com.htecgroup.skynest.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

@Service
@Log4j2
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
  private JavaMailSender javaMailSender;

  @Override
  @Async
  public void send(Email email) {
    try {
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
      helper.setText(email.getEmailBody(), email.getHtml());
      helper.setTo(email.getTo());
      helper.setSubject(email.getSubject());
      javaMailSender.send(mimeMessage);
    } catch (AddressException e) {
      log.error("Illegal mail address", e);
      throw new UserException(UserExceptionType.ILLEGAL_EMAIL);
    } catch (MessagingException messagingException) {
      log.error("Failed to send email", messagingException);
      throw new UserException(UserExceptionType.EMAIL_FAILED_TO_SEND);
    }
  }
}
