package com.htecgroup.skynest.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class TagFilteredStorageResponse {

  private List<BucketResponse> buckets;
  private List<FolderResponse> folders;
  private List<FileResponse> files;
}
