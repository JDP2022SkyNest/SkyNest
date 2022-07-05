package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserObjectAccessKey implements Serializable {

  private static final long serialVersionUID = -1295532429399577410L;

  @ManyToOne
  @JoinColumn(name = "granted_to", nullable = false)
  private UserEntity grantedTo;

  @ManyToOne
  @JoinColumn(name = "object_id", nullable = false)
  private ObjectEntity object;
}
