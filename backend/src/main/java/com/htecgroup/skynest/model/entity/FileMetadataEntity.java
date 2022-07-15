package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadataEntity extends ObjectEntity {

  private static final long serialVersionUID = -7537913364251109324L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_folder_id")
  private FolderEntity parentFolder;

  @ManyToOne
  @JoinColumn(name = "bucket_id", nullable = false)
  private BucketEntity bucket;

  private String type;
  private long size;
  private String contentId;

  @Override
  public boolean isDeleted() {
    return super.isDeleted()
        || (parentFolder == null ? bucket.isDeleted() : parentFolder.isDeleted());
  }
}
