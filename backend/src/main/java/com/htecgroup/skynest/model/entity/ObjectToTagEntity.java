package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "object_to_tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectToTagEntity implements Serializable {

  private static final long serialVersionUID = 1500478643242053252L;

  @EmbeddedId private ObjectToTagKey id;

  @MapsId("tagId")
  @ManyToOne
  @JoinColumn(name = "tag_id", nullable = false)
  private TagEntity tag;

  @MapsId("objectId")
  @ManyToOne
  @JoinColumn(name = "object_id", nullable = false)
  private ObjectEntity object;
}
