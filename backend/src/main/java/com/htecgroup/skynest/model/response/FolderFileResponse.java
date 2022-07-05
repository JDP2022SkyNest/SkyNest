package com.htecgroup.skynest.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FolderFileResponse {

  private List<FolderResponse> folderResponseList;
  private List<FileResponse> fileResponseList;
}
