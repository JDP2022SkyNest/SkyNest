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

@Entity(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity implements Serializable {

  private static final long serialVersionUID = -6954026563601179057L;
  public static final String ROLE_WORKER = "role_worker";
  public static final String ROLE_MANAGER = "role_manager";
  public static final String ROLE_ADMIN = "role_admin";

  @Id @GeneratedValue private UUID id;
  private String name;
}
