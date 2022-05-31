package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

  @Mock private JavaMailSender javaMailSender;

  @InjectMocks private EmailServiceImpl emailService;

  @Captor
  ArgumentCaptor<MimeMessage> captorMimeMessage;

  @BeforeEach
  void setUp() {}

  @Test
  void sendTest() {
    MimeMessage mimeMessage = mock(MimeMessage.class);
    String to = "test@yahoo.com";
    String email = "MailContent";
    when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    emailService.send(to, email);
    Mockito.verify(javaMailSender).send(mimeMessage);
  }

  @Test
  void sendFailsTest() {
    String to = "test@yahoo.com";
    String email = "MailContent";
    when(javaMailSender.createMimeMessage()).thenThrow(UserException.class);
    Assertions.assertThrows(UserException.class, () -> emailService.send(to, email));
  }

  @Test
  void checkMimeMessageHelperArgumentsInSend() throws MessagingException, IOException {
    String to = "test@yahoo.com";
    String emailText = "MailContent";
    String expectedSubject = "Confirm your email for SkyNest";
    MimeMessage mimeMessage = new MimeMessage((Session) null);
    when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    emailService.send(to, emailText);

    Mockito.verify(javaMailSender).send(captorMimeMessage.capture());

    MimeMessage capturedMessage = captorMimeMessage.getValue();
    Address[] recipients = capturedMessage.getRecipients(Message.RecipientType.TO);
    String recipient = recipients == null ? null : ((InternetAddress) recipients[0]).getAddress();
    Assertions.assertEquals(to, recipient);
    Assertions.assertEquals(expectedSubject, capturedMessage.getSubject());
    Assertions.assertEquals(emailText, capturedMessage.getContent());
  }


}
