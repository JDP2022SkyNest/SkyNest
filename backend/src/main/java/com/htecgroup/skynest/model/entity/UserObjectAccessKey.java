package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserObjectAccessKey implements Serializable {

  private static final long serialVersionUID = -1295532429399577410L;

  @Column(name = "granted_to")
  private UUID grantedToId;

  @Column(name = "object_id")
  private UUID objectId;
}
