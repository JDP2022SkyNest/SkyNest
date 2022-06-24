package com.htecgroup.skynest.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "object")
@Inheritance(strategy = InheritanceType.JOINED)
public class ObjectEntity implements Serializable {

  private static final long serialVersionUID = 145843022185009528L;

  @Id @GeneratedValue private UUID id;

  @Column(insertable = false)
  private LocalDateTime createdOn;

  @Column(insertable = false)
  private LocalDateTime modifiedOn;

  private LocalDateTime deletedOn;

  @ManyToOne
  @JoinColumn(name = "created_by", nullable = false)
  private UserEntity createdBy;

  private String name;
}
