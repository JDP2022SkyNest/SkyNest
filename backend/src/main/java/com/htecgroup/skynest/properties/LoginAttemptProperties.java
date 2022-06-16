package com.htecgroup.skynest.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "login-attempts")
@ConfigurationPropertiesScan
@ConstructorBinding
@AllArgsConstructor
@Getter
public class LoginAttemptProperties {
  private final int maxAttemptsPerInterval;
  private final int intervalInSeconds;
}
