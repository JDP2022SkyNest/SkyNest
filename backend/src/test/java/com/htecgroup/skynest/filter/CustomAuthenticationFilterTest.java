package com.htecgroup.skynest.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htecgroup.skynest.exception.auth.UserNotActiveException;
import com.htecgroup.skynest.exception.login.EmailNotVerifiedException;
import com.htecgroup.skynest.exception.login.TooManyAttemptsException;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.request.UserLoginRequest;
import com.htecgroup.skynest.service.AuthService;
import com.htecgroup.skynest.service.LoginAttemptService;
import com.htecgroup.skynest.util.JwtUtils;
import com.htecgroup.skynest.utils.LoggedUserDtoUtil;
import com.htecgroup.skynest.utils.UserLoginRequestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationFilterTest {

  @Mock private AuthenticationManager authenticationManager;
  @Mock private UserDetailsService userDetailsService;
  @Mock private AuthService authService;
  @Mock private ObjectMapper objectMapper;
  @Mock private LoginAttemptService loginAttemptService;

  @Mock private MockHttpServletRequest request;
  @Mock private MockHttpServletResponse response;

  @Mock private MockFilterChain filterChain;

  @InjectMocks private CustomAuthenticationFilter customAuthenticationFilter;

  @BeforeEach
  void setUp() {
    CustomAuthenticationFilter customAuthenticationFilter =
        new CustomAuthenticationFilter(
            authenticationManager,
            userDetailsService,
            authService,
            objectMapper,
            loginAttemptService);
  }

  @Test
  void when_HasTooManyAttempts_attemptAuthentication_ShouldThrowTooManyAttempts()
      throws IOException {
    UserLoginRequest userLoginRequest = UserLoginRequestUtil.get();
    InputStream inputStream = request.getInputStream();
    String expectedErrorMessage = new TooManyAttemptsException().getMessage();
    doReturn(userLoginRequest).when(objectMapper).readValue(inputStream, UserLoginRequest.class);
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
    when(loginAttemptService.hasTooManyAttempts(mock(UserDetails.class).getUsername()))
        .thenReturn(true);
    Exception thrownException =
        Assertions.assertThrows(
            TooManyAttemptsException.class,
            () -> customAuthenticationFilter.attemptAuthentication(request, response));

    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void when_UserNotVerified_attemptAuthentication_ShouldThrowUserNotVerified() throws IOException {
    UserLoginRequest userLoginRequest = UserLoginRequestUtil.get();
    InputStream inputStream = request.getInputStream();
    String expectedErrorMessage = new EmailNotVerifiedException().getMessage();
    doReturn(userLoginRequest).when(objectMapper).readValue(inputStream, UserLoginRequest.class);
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
    when(loginAttemptService.hasTooManyAttempts(mock(UserDetails.class).getUsername()))
        .thenReturn(false);
    when(authService.isVerified(mock(UserDetails.class).getUsername())).thenReturn(false);
    Exception thrownException =
        Assertions.assertThrows(
            EmailNotVerifiedException.class,
            () -> customAuthenticationFilter.attemptAuthentication(request, response));

    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void when_UserNotActive_attemptAuthentication_ShouldThrowUserNotActive() throws IOException {
    UserLoginRequest userLoginRequest = UserLoginRequestUtil.get();
    InputStream inputStream = request.getInputStream();
    String expectedErrorMessage = new UserNotActiveException().getMessage();
    doReturn(userLoginRequest).when(objectMapper).readValue(inputStream, UserLoginRequest.class);
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
    when(loginAttemptService.hasTooManyAttempts(mock(UserDetails.class).getUsername()))
        .thenReturn(false);
    when(authService.isVerified(mock(UserDetails.class).getUsername())).thenReturn(true);
    when(authService.isActive(mock(UserDetails.class).getUsername())).thenReturn(false);
    Exception thrownException =
        Assertions.assertThrows(
            UserNotActiveException.class,
            () -> customAuthenticationFilter.attemptAuthentication(request, response));

    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void when_EverythingWorks_attemptAuthentication_ShouldInvokeAuthenticateMethod()
      throws IOException {
    UserLoginRequest userLoginRequest = UserLoginRequestUtil.get();
    InputStream inputStream = request.getInputStream();
    doReturn(userLoginRequest).when(objectMapper).readValue(inputStream, UserLoginRequest.class);
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
    when(loginAttemptService.hasTooManyAttempts(mock(UserDetails.class).getUsername()))
        .thenReturn(false);
    when(authService.isVerified(mock(UserDetails.class).getUsername())).thenReturn(true);
    when(authService.isActive(mock(UserDetails.class).getUsername())).thenReturn(true);
    customAuthenticationFilter.attemptAuthentication(request, response);
    verify(authenticationManager).authenticate(any());
  }

  @Test
  void successfulAuthentication() {
    Authentication authentication = mock(Authentication.class);
    LoggedUserDto loggedUser = LoggedUserDtoUtil.getLoggedWorkerUser();
    when(authentication.getPrincipal()).thenReturn(loggedUser);
    try (MockedStatic<JwtUtils> utilities = Mockito.mockStatic(JwtUtils.class)) {
      utilities.when(() -> JwtUtils.generateAccessToken(any(), anyList())).thenReturn("TOKEN");
      customAuthenticationFilter.successfulAuthentication(
          request, response, filterChain, authentication);
      verify(response, times(2)).addHeader(anyString(), anyString());
    } catch (ServletException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
