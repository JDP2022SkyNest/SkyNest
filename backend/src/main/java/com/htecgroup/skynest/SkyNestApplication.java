package com.htecgroup.skynest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htecgroup.skynest.filter.CustomAuthorizationFilter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ConfigurationPropertiesScan("com.htecgroup.skynest.properties")
public class SkyNestApplication {

  public static void main(String[] args) {
    SpringApplication.run(SkyNestApplication.class, args);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CustomAuthorizationFilter customAuthorizationFilter() {
    return new CustomAuthorizationFilter();
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
