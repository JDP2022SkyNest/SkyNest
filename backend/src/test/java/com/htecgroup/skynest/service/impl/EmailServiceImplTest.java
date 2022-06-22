package com.htecgroup.skynest.service.impl;

import com.auth0.jwt.algorithms.Algorithm;
import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.model.email.Email;
import com.htecgroup.skynest.util.EmailUtil;
import com.htecgroup.skynest.util.JwtUtils;
import com.htecgroup.skynest.utils.UserDtoUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

  @Mock private JavaMailSender javaMailSender;
  @Mock private ITemplateEngine templateEngine;

  @InjectMocks private EmailServiceImpl emailService;

  @Captor ArgumentCaptor<MimeMessage> captorMimeMessage;

  private Email email;

  @BeforeEach
  void setUp() {
    JwtUtils.ALGORITHM = Algorithm.HMAC512("test secret");
    String token = JwtUtils.generateEmailVerificationToken(UserDtoUtil.getNotVerified().getEmail());
    email = EmailUtil.createVerificationEmail(UserDtoUtil.getNotVerified(), token);
  }

  @Test
  void sendTest() {
    String emailText = "Email text";
    when(templateEngine.process(anyString(), any(Context.class))).thenReturn(emailText);
    MimeMessage mimeMessage = mock(MimeMessage.class);
    when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    emailService.send(email);
    Mockito.verify(javaMailSender).send(mimeMessage);
  }

  @Test
  void sendFailsTest() {
    String emailText = "Email text";
    when(templateEngine.process(anyString(), any())).thenReturn(emailText);
    when(javaMailSender.createMimeMessage()).thenThrow(UserException.class);
    Assertions.assertThrows(UserException.class, () -> emailService.send(email));
  }

  @Test
  void testMimeMessageHelperArgumentsInSend() throws MessagingException, IOException {
    String expectedTo = email.getTo();
    String expectedEmailText = "Email text";
    String expectedSubject = email.getEmailType().getSubject();
    MimeMessage mimeMessage = new MimeMessage((Session) null);
    when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    when(templateEngine.process(anyString(), any())).thenReturn(expectedEmailText);
    emailService.send(email);

    Mockito.verify(javaMailSender).send(captorMimeMessage.capture());

    MimeMessage capturedMessage = captorMimeMessage.getValue();
    Address[] recipients = capturedMessage.getRecipients(Message.RecipientType.TO);
    String recipient = recipients == null ? null : ((InternetAddress) recipients[0]).getAddress();
    Assertions.assertEquals(expectedTo, recipient);
    Assertions.assertEquals(expectedSubject, capturedMessage.getSubject());
    Assertions.assertEquals(expectedEmailText, capturedMessage.getContent());
  }
}
