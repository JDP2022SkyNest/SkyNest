package com.htecgroup.skynest.security;

import com.htecgroup.skynest.properties.ServerProperties;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CustomWebMvcConfigurer implements WebMvcConfigurer {
  private final ServerProperties serverProperties;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedMethods("*")
        .allowedOrigins(
            String.format(
                "http://%s:%s", serverProperties.getHost(), serverProperties.getFrontendPort()))
        .allowedOriginPatterns(
            String.format(
                "http://%s:%s/*", serverProperties.getHost(), serverProperties.getFrontendPort()))
        .allowedHeaders(
            JwtUtils.AUTH_HEADER, HttpHeaders.ORIGIN, HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT)
        .exposedHeaders(
            JwtUtils.AUTH_HEADER,
            HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
            HttpHeaders.ORIGIN,
            HttpHeaders.ACCEPT,
            HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD,
            HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
  }
}
