package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity implements Serializable {

  @Id @GeneratedValue private UUID id;

  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  private CompanyEntity company;

  private String name;
  private String rgb;
}
