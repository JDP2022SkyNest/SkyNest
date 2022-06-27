package com.htecgroup.skynest.filter;

import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.security.CustomUserDetailsService;
import com.htecgroup.skynest.service.InvalidJwtService;
import com.htecgroup.skynest.util.JwtUtils;
import com.htecgroup.skynest.util.UrlUtil;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomAuthorizationFilterTest {

  @Mock private InvalidJwtService invalidJwtService;

  @Mock private CustomUserDetailsService customUserDetailsService;

  @Mock private MockHttpServletResponse response;

  @Mock private MockHttpServletRequest request;
  @Mock private MockFilterChain filterChain;

  @InjectMocks private CustomAuthorizationFilter customAuthorizationFilter;

  @BeforeEach
  void setUp() {
    customAuthorizationFilter =
        new CustomAuthorizationFilter(invalidJwtService, customUserDetailsService);
  }

  @Test
  void when_PublicURL_doFilterInternal_ShouldDoFilterInternal()
      throws ServletException, IOException {
    when(request.getServletPath())
        .thenReturn(String.format("%s/something", UrlUtil.PUBLIC_CONTROLLER_URL));
    customAuthorizationFilter.doFilterInternal(request, response, filterChain);
    Mockito.verify(filterChain)
        .doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
  }

  @Test
  void when_AuthHeaderNull_doFilterInternal_ShouldDoFilterInternal()
      throws ServletException, IOException {
    when(request.getServletPath()).thenReturn("false");
    when(request.getHeader(anyString())).thenReturn(null);
    customAuthorizationFilter.doFilterInternal(request, response, filterChain);
    Mockito.verify(filterChain)
        .doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
  }

  @Test
  void when_DoesNotStartWithBearer_doFilterInternal_ShouldDoFilterInternal()
      throws ServletException, IOException {
    when(request.getServletPath()).thenReturn("false");
    when(request.getHeader(anyString())).thenReturn("something");
    customAuthorizationFilter.doFilterInternal(request, response, filterChain);
    Mockito.verify(filterChain)
        .doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
  }

  @Test
  void when_TokenInInvalidTokensDatabase_doFilterInternal_ShouldDoFilterInternalAndLog()
      throws ServletException, IOException {
    when(request.getServletPath()).thenReturn("false");
    when(request.getHeader(anyString())).thenReturn("Bearer TOKEN");
    when(invalidJwtService.isInvalid(anyString())).thenReturn(true);
    customAuthorizationFilter.doFilterInternal(request, response, filterChain);
    Mockito.verify(filterChain)
        .doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
  }

  @Test
  void when_EverythingIsFine_doFilterInternal_ShouldDoFilterInternal()
      throws ServletException, IOException {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken("User", "Credentials");
    when(request.getServletPath()).thenReturn("false");
    when(request.getHeader(anyString())).thenReturn("Bearer TOKEN");
    when(invalidJwtService.isInvalid(anyString())).thenReturn(false);
    try (MockedStatic<JwtUtils> utilities = Mockito.mockStatic(JwtUtils.class)) {
      utilities
          .when(() -> JwtUtils.getFrom(anyString()))
          .thenReturn(usernamePasswordAuthenticationToken);
      when(customUserDetailsService.loadUserByUsername(anyString()))
          .thenReturn(mock(LoggedUserDto.class));
      customAuthorizationFilter.doFilterInternal(request, response, filterChain);
      Mockito.verify(filterChain)
          .doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }
  }
}
