package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.model.email.Email;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

  @Mock private JavaMailSender javaMailSender;

  @InjectMocks private EmailServiceImpl emailService;

  @Captor ArgumentCaptor<MimeMessage> captorMimeMessage;

  private Email email;

  @BeforeEach
  void setUp() {
    String to = "test@yahoo.com";
    String emailText = "MailContent";
    String subject = "Confirm your email for SkyNest";
    this.email = new Email(to, subject, emailText, true);
  }

  @Test
  void sendTest() {
    MimeMessage mimeMessage = mock(MimeMessage.class);
    when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    emailService.send(email);
    Mockito.verify(javaMailSender).send(mimeMessage);
  }

  @Test
  void sendFailsTest() {
    Email mockedEmail = mock(Email.class);
    when(javaMailSender.createMimeMessage()).thenThrow(UserException.class);
    Assertions.assertThrows(UserException.class, () -> emailService.send(mockedEmail));
  }

  @Test
  void testMimeMessageHelperArgumentsInSend() throws MessagingException, IOException {
    String expectedTo = "test@yahoo.com";
    String expectedEmailText = "MailContent";
    String expectedSubject = "Confirm your email for SkyNest";
    MimeMessage mimeMessage = new MimeMessage((Session) null);
    when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
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
