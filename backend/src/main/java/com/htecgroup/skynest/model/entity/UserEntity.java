package com.htecgroup.skynest.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {

  private static final long serialVersionUID = 8310242347168695452L;

  @Id @GeneratedValue private UUID id;

  @Column(insertable = false)
  private LocalDateTime createdOn;

  @Column(insertable = false)
  private LocalDateTime modifiedOn;

  private LocalDateTime deletedOn;
  private String email;
  private String encryptedPassword;
  private String name;
  private String surname;
  private String address;
  private String phoneNumber;
  private Boolean verified;
  private Boolean enabled;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private RoleEntity role;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyEntity company;
}
