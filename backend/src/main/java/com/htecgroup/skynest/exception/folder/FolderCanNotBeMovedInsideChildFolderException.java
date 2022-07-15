package com.htecgroup.skynest.exception.folder;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class FolderCanNotBeMovedInsideChildFolderException extends SkyNestBaseException {

  public static final String MESSAGE = "Folder Cannot be moved inside child folder";

  public FolderCanNotBeMovedInsideChildFolderException() {
    super(MESSAGE, HttpStatus.METHOD_NOT_ALLOWED);
  }
}
