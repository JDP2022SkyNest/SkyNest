package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Entity(name = "tier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TierEntity implements Serializable {

  private static final long serialVersionUID = -41839637756579466L;

  @Id @GeneratedValue private UUID id;
  private String name;
}
