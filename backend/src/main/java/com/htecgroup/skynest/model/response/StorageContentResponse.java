package com.htecgroup.skynest.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class StorageContentResponse {

  UUID bucketId;
  private List<FolderResponse> folders;
  private List<FileResponse> files;
}
