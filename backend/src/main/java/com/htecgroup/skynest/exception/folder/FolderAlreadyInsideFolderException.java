package com.htecgroup.skynest.exception.folder;

import org.springframework.http.HttpStatus;

public class FolderAlreadyInsideFolderException extends FolderException {

  public static final String MESSAGE = "Folder is already inside that folder";

  public FolderAlreadyInsideFolderException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
