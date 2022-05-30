package com.htecgroup.skynest.model.enitity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

  private LocalDateTime deletedOn;
  private String name;
  private String address;
  private String domain;
}
