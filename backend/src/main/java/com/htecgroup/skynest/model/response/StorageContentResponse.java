package com.htecgroup.skynest.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StorageContentResponse {

  private List<FolderResponse> folders;
  private List<FileResponse> files;
}
