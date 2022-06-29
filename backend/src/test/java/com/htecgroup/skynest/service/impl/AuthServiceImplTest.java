package com.htecgroup.skynest.service.impl;

import com.auth0.jwt.algorithms.Algorithm;
import com.htecgroup.skynest.exception.register.UserAlreadyVerifiedException;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.email.Email;
import com.htecgroup.skynest.model.request.UserPasswordResetRequest;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.EmailService;
import com.htecgroup.skynest.service.PasswordEncoderService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.EmailUtil;
import com.htecgroup.skynest.util.JwtUtils;
import com.htecgroup.skynest.utils.UserDtoUtil;
import com.htecgroup.skynest.utils.UserEntityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

  @Mock private UserRepository userRepository;
  @Mock private PasswordEncoderService passwordEncoderService;
  @Mock private UserService userService;
  @Mock private EmailService emailService;
  @Spy private ModelMapper modelMapper;
  @Spy @InjectMocks private AuthServiceImpl authService;

  @Test
  void confirmEmail() {
    String expectedResponse = "User verified successfully";
    String testEmail = "confirmEmail@email.com";
    JwtUtils.ALGORITHM = Algorithm.HMAC512("test secret");
    String token = JwtUtils.generateEmailVerificationToken(testEmail);

    when(userService.findUserByEmail(anyString())).thenReturn(UserDtoUtil.getNotVerified());
    when(userRepository.save(any())).thenReturn(UserEntityUtil.getNotVerified());

    Assertions.assertEquals(expectedResponse, authService.confirmEmail(token));
  }

  @Test
  void resetPassword() {
    String expectedResponse = "Password was successfully reset";
    String testEmail = "confirmEmail@email.com";
    JwtUtils.ALGORITHM = Algorithm.HMAC512("test secret");
    String token = JwtUtils.generatePasswordResetToken(testEmail);

    when(userService.findUserByEmail(anyString())).thenReturn(UserDtoUtil.getVerified());
    when(passwordEncoderService.encode(anyString())).thenReturn("encodedPassword");
    when(userRepository.save(any())).thenReturn(UserEntityUtil.getVerified());

    UserPasswordResetRequest userPasswordResetRequest = new UserPasswordResetRequest();
    userPasswordResetRequest.setToken(token);
    userPasswordResetRequest.setPassword("NewPassword123");

    Assertions.assertEquals(expectedResponse, authService.resetPassword(userPasswordResetRequest));
  }

  @Test
  void verifyEmail() {
    doReturn(false).when(authService).isVerified(anyString());
    UserDto verifiedUser = authService.verifyUser(UserDtoUtil.getNotVerified());

    Assertions.assertTrue(verifiedUser.getVerified());
    Assertions.assertTrue(verifiedUser.getEnabled());
  }

  @Test
  void verifyUser_UserAlreadyVerified() {
    UserDto verifiedUserDto = UserDtoUtil.getVerified();
    String expectedErrorMessage = new UserAlreadyVerifiedException().getMessage();

    doReturn(true).when(authService).isVerified(anyString());

    Exception thrownException =
        Assertions.assertThrows(
            UserAlreadyVerifiedException.class, () -> authService.verifyUser(verifiedUserDto));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void isActive_True() {
    UserDto verifiedUserDto = UserDtoUtil.getVerified();
    doReturn(verifiedUserDto).when(userService).findUserByEmail(anyString());
    boolean returnedValue = authService.isActive(verifiedUserDto.getEmail());
    Assertions.assertTrue(returnedValue);
  }

  @Test
  void isActive_NotVerifiedUser() {
    UserDto deletedUserDto = UserDtoUtil.getNotVerified();
    deletedUserDto.setDeletedOn(LocalDateTime.now());
    doReturn(deletedUserDto).when(userService).findUserByEmail(anyString());
    boolean returnedValue = authService.isActive(deletedUserDto.getEmail());
    Assertions.assertFalse(returnedValue);
  }

  @Test
  void when_VerifiedUser_sendVerificationEmail_ShouldThrowUserExceptionAlreadyRegistered() {
    UserDto userDto = UserDtoUtil.getVerified();
    doReturn(userDto).when(userService).findUserByEmail(anyString());
    String expectedErrorMessage = new UserAlreadyVerifiedException().getMessage();
    Exception thrownException =
        Assertions.assertThrows(
            UserAlreadyVerifiedException.class,
            () -> authService.sendVerificationEmail(userDto.getEmail()));

    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void when_NotVerifiedUser_sendVerificationEmail_ShouldInvokeEmailSendMethod() {
    UserDto userDto = UserDtoUtil.getNotVerified();
    doReturn(userDto).when(userService).findUserByEmail(anyString());
    try (MockedStatic<JwtUtils> utilities = Mockito.mockStatic(JwtUtils.class)) {
      utilities
          .when(() -> JwtUtils.generateEmailVerificationToken(anyString()))
          .thenReturn("TOKEN");

      try (MockedStatic<EmailUtil> emailUtilities = Mockito.mockStatic(EmailUtil.class)) {
        emailUtilities
            .when(() -> EmailUtil.createVerificationEmail(any(), anyString()))
            .thenReturn(mock(Email.class));
        authService.sendVerificationEmail(userDto.getEmail());
        verify(emailService).send(any());
      }
    }
  }

  @Test
  void when_EverythingFine_sendVerificationEmail_ShouldInvokeEmailSendMethod() {
    UserDto userDto = UserDtoUtil.getNotVerified();
    doReturn(userDto).when(userService).findUserByEmail(anyString());
    try (MockedStatic<JwtUtils> utilities = Mockito.mockStatic(JwtUtils.class)) {
      utilities.when(() -> JwtUtils.generatePasswordResetToken(anyString())).thenReturn("TOKEN");

      try (MockedStatic<EmailUtil> emailUtilities = Mockito.mockStatic(EmailUtil.class)) {
        emailUtilities
            .when(() -> EmailUtil.createPasswordResetEmail(any(), anyString()))
            .thenReturn(mock(Email.class));
        authService.sendPasswordResetEmail(userDto.getEmail());
        verify(emailService).send(any());
      }
    }
  }

  @Test
  void when_VerifiedUser_verifyUser_ShouldReturnTrue() {
    UserDto userDto = UserDtoUtil.getVerified();
    when(userService.findUserByEmail(anyString())).thenReturn(userDto);
    Assertions.assertTrue(authService.isVerified(anyString()));
  }

  @Test
  void when_NotVerifiedUser_verifyUser_ShouldReturnFalse() {
    UserDto userDto = UserDtoUtil.getNotVerified();
    when(userService.findUserByEmail(anyString())).thenReturn(userDto);
    Assertions.assertFalse(authService.isVerified(anyString()));
  }
}
