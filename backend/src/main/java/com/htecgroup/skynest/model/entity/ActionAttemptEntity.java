package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActionAttemptEntity implements Serializable {

  private static final long serialVersionUID = -7125007399589379176L;

  public static final String UNSUCCESSFUL_LOGIN_ATTEMPT = "unsuccessful login";

  @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
  private UUID userId;

  @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED)
  private String action;

  @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED)
  private LocalDateTime occurredAt = LocalDateTime.now();
}
