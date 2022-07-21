package com.htecgroup.skynest.exception.folder;

import org.springframework.http.HttpStatus;

public class FolderCanNotBeMovedInsideChildFolderException extends FolderException {

  public static final String MESSAGE = "Folder Cannot be moved inside child folder";

  public FolderCanNotBeMovedInsideChildFolderException() {
    super(MESSAGE, HttpStatus.METHOD_NOT_ALLOWED);
  }
}
