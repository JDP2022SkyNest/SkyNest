package com.htecgroup.skynest.service.impl;

import com.auth0.jwt.algorithms.Algorithm;
import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.request.UserPasswordResetRequest;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.JwtUtils;
import com.htecgroup.skynest.utils.UserDtoUtil;
import com.htecgroup.skynest.utils.UserEntityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

  @Mock private UserRepository userRepository;
  @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Mock private UserService userService;
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
    when(userRepository.save(any())).thenReturn(UserEntityUtil.getVerified());

    UserPasswordResetRequest userPasswordResetRequest = new UserPasswordResetRequest();
    userPasswordResetRequest.setToken(token);
    userPasswordResetRequest.setPassword("NewPassword123");

    Assertions.assertEquals(expectedResponse, authService.resetPassword(userPasswordResetRequest));
  }

  @Test
  void verifyEmail() {
    doReturn(false).when(authService).isActive(anyString());
    UserDto verifiedUser = authService.verifyUser(UserDtoUtil.getNotVerified());

    Assertions.assertTrue(verifiedUser.getVerified());
    Assertions.assertTrue(verifiedUser.getEnabled());
  }

  @Test
  void verifyUser_UserAlreadyVerified() {
    UserDto verifiedUserDto = UserDtoUtil.getVerified();
    String expectedErrorMessage = UserExceptionType.USER_ALREADY_REGISTERED.getMessage();

    doReturn(true).when(authService).isActive(anyString());

    Exception thrownException =
        Assertions.assertThrows(UserException.class, () -> authService.verifyUser(verifiedUserDto));
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
}
