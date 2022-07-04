package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity(name = "action")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActionEntity implements Serializable {

  private static final long serialVersionUID = 5255421983616446497L;

  @Id @GeneratedValue private UUID id;

  @Column(insertable = false)
  private LocalDateTime performedOn;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "action_type_id", nullable = false)
  private ActionTypeEntity actionType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "revoked_by")
  private ActionEntity revokedBy;

  @ManyToMany
  @JoinTable(
      name = "action_on_object",
      joinColumns = @JoinColumn(name = "action_id"),
      inverseJoinColumns = @JoinColumn(name = "object_id"))
  private Set<ObjectEntity> objects;
}
