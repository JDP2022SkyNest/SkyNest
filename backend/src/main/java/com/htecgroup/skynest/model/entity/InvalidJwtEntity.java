package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InvalidJwtEntity implements Serializable {
  private static final long serialVersionUID = 531939571712024162L;
  @PrimaryKey private String token;
}
