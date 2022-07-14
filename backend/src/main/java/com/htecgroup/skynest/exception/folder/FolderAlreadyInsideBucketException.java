package com.htecgroup.skynest.exception.folder;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class FolderAlreadyInsideBucketException extends SkyNestBaseException {

  public static final String MESSAGE = "Folder is already inside the root";

  public FolderAlreadyInsideBucketException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
