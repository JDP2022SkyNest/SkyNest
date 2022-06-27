package com.htecgroup.skynest.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "bucket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class BucketEntity extends ObjectEntity {

  private static final long serialVersionUID = 7020161097724572834L;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyEntity company;

  private String description;

  @Column(insertable = false)
  private long size;

  @Column(name = "public")
  @With
  private Boolean isPublic;
}
