package com.htecgroup.skynest.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "server-info")
@ConfigurationPropertiesScan
@ConstructorBinding
@AllArgsConstructor
@Getter
public class ServerProperties {
  private final String host;
  private final int backendPort;
  private final int frontendPort;
}
