package com.htecgroup.skynest.util;

import com.htecgroup.skynest.model.entity.FolderEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderUtil {

  public static List<FolderEntity> getPathToFolder(FolderEntity folderEntity) {

    Deque<FolderEntity> path = new LinkedList<>();
    path.addFirst(folderEntity);
    FolderEntity parentFolder = folderEntity.getParentFolder();
    while (parentFolder != null) {
      path.addFirst(parentFolder);
      parentFolder = parentFolder.getParentFolder();
    }

    return (List<FolderEntity>) path;
  }
}
