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
public class ObjectToTagKey implements Serializable {

  private static final long serialVersionUID = -1295532429399577410L;

  @Column(name = "tag_id")
  private UUID tagId;

  @Column(name = "object_id")
  private UUID objectId;
}
