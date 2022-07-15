package com.htecgroup.skynest.exception.folder;

import org.springframework.http.HttpStatus;

public class FolderParentIsDeletedException extends FolderException {

  private static final long serialVersionUID = -5745928446505974461L;
  public static final String MESSAGE = "Folder's parent is deleted";

  public FolderParentIsDeletedException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
