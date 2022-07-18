package com.htecgroup.skynest;

import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.DriverConfigLoaderBuilderCustomizer;
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
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public DriverConfigLoaderBuilderCustomizer defaultProfile() {
    return builder ->
        builder
            .withString(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, "20 seconds")
            .build();
  }

  @Bean("dropboxClient")
  public DbxClientV2 dropboxClient() throws DbxException {
    String ACCESS_TOKEN =
        "sl.BLp2qV7oDuSt6-K6XQ9pBj7ZH8fLCX5As_Xxxb6hb2HY8Q5v-c6asb1nzkH1m462xN6gD2ZmNqq-uXXyOc5YfFuFbelbwFIr_WF2Vm2zZ4dFxfKTqxUfGdG5ROK4rTJ2VskLIjw";
    DbxRequestConfig config = new DbxRequestConfig("/skynest/test/dropbox");
    DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
    return client;
  }
}
