package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "user_object_access")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserObjectAccessKey.class)
public class UserObjectAccessEntity implements Serializable {

  private static final long serialVersionUID = 3938317098320384416L;

  @Id private UserEntity grantedTo;

  @Id private ObjectEntity object;

  @Column(insertable = false)
  private LocalDateTime grantedOn;

  @ManyToOne
  @JoinColumn(name = "access_id", nullable = false)
  private AccessTypeEntity access;

  @ManyToOne
  @JoinColumn(name = "granted_by", nullable = false)
  private UserEntity grantedBy;
}
