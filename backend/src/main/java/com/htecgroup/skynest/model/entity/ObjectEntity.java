package com.htecgroup.skynest.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "object")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectEntity implements Serializable {

  private static final long serialVersionUID = 145843022185009528L;

  @Id @GeneratedValue private UUID id;

  @Column(insertable = false, updatable = false)
  private LocalDateTime createdOn;

  @Column(insertable = false, updatable = false)
  private LocalDateTime modifiedOn;

  private LocalDateTime deletedOn;

  @ManyToOne
  @JoinColumn(name = "created_by", nullable = false)
  @With
  private UserEntity createdBy;

  private String name;

  public boolean isDeleted() {
    return deletedOn != null;
  }

  public void delete() {
    deletedOn = LocalDateTime.now();
  }

  public void restore() {
    deletedOn = null;
  }
}
