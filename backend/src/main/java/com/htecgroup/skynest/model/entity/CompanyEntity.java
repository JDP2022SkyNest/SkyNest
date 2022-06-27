package com.htecgroup.skynest.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity implements Serializable {

  private static final long serialVersionUID = -503083094116937084L;

  @Id @GeneratedValue private UUID id;

  @Column(insertable = false)
  private LocalDateTime createdOn;

  @Column(insertable = false)
  private LocalDateTime modifiedOn;

  @With private LocalDateTime deletedOn;

  private String pib;
  private String name;
  private String address;
  private String phoneNumber;
  private String email;

  @ManyToOne
  @JoinColumn(name = "tier_id", nullable = false)
  private TierEntity tier;
}
