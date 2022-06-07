package com.htecgroup.skynest.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htecgroup.skynest.filter.CustomAuthenticationFilter;
import com.htecgroup.skynest.filter.CustomAuthorizationFilter;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.UrlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private final CustomUserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final ObjectMapper objectMapper;

  private final UserService userService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, UrlUtil.POST_URLS_WITHOUT_AUTH)
        .permitAll()
        .antMatchers(HttpMethod.GET, UrlUtil.GET_URLS_WITHOUT_AUTH)
        .permitAll()
        .antMatchers(HttpMethod.PUT, UrlUtil.PUT_URLS_WITHOUT_AUTH)
        .permitAll()
        .antMatchers(UrlUtil.ANY_URLS_WITHOUT_AUTH)
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilter(getAuthenticationFilter())
        .addFilterBefore(getAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  public CustomAuthenticationFilter getAuthenticationFilter() throws Exception {
    final CustomAuthenticationFilter filter =
        new CustomAuthenticationFilter(
            authenticationManager(),
            userDetailsService,
            bCryptPasswordEncoder,
            userService,
            objectMapper);
    filter.setFilterProcessesUrl(UrlUtil.USERS_CONTROLLER_URL + UrlUtil.LOG_IN_URL);
    return filter;
  }

  public CustomAuthorizationFilter getAuthorizationFilter() {
    return new CustomAuthorizationFilter();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }
}
