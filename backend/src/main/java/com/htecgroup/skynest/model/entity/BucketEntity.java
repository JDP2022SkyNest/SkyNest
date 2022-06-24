package com.htecgroup.skynest.model.entity;

import lombok.With;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "bucket")
public class BucketEntity extends ObjectEntity {

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyEntity company;

  private String description;
  private long size;

  @Column(name = "public")
  @With
  private Boolean isPublic;
}
