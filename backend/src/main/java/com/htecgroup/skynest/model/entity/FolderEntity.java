package com.htecgroup.skynest.model.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "folder")
public class FolderEntity extends ObjectEntity {

  @ManyToOne
  @JoinColumn(name = "parent_folder_id")
  private FolderEntity parentFolder;

  @ManyToOne
  @JoinColumn(name = "bucket_id", nullable = false)
  private BucketEntity bucket;
}
