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

@Entity(name = "access")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessTypeEntity implements Serializable {

  private static final long serialVersionUID = -8558052696146894005L;

  @Id @GeneratedValue private UUID id;
  private String name;
}
