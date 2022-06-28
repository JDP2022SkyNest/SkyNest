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

@Entity(name = "action_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActionTypeEntity implements Serializable {

  private static final long serialVersionUID = -6690441865629866762L;

  @Id @GeneratedValue private UUID id;
  private String name;
}
