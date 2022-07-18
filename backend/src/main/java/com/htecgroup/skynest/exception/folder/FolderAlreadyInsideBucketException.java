package com.htecgroup.skynest.exception.folder;

import org.springframework.http.HttpStatus;

public class FolderAlreadyInsideBucketException extends FolderException {

  public static final String MESSAGE = "Folder is already inside the root";

  public FolderAlreadyInsideBucketException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
