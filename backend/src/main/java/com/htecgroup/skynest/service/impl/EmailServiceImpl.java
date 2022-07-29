package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.email.NonExistingEmailAddressException;
import com.htecgroup.skynest.exception.email.SendingEmailFailedException;
import com.htecgroup.skynest.model.email.Email;
import com.htecgroup.skynest.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

@Service
@Log4j2
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
  private JavaMailSender javaMailSender;
  private ITemplateEngine templateEngine;

  @Override
  @Async
  public void send(Email email) {
    Context context = new Context();
    context.setVariable("args", email.getArgs());
    context.setVariable("files", email.getFileStatsEmailResponses());
    String template = email.getEmailType().getTemplate();
    String subject = email.getEmailType().getSubject();
    String emailText = templateEngine.process(template, context);

    try {
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
      helper.setText(emailText, email.getHtml());
      helper.setTo(email.getTo());
      helper.setSubject(subject);
      javaMailSender.send(mimeMessage);
    } catch (AddressException e) {
      log.error("Illegal mail address", e);
      throw new NonExistingEmailAddressException();
    } catch (MessagingException messagingException) {
      log.error("Failed to send email", messagingException);
      throw new SendingEmailFailedException();
    }
  }
}
