package com.htecgroup.skynest.exception.folder;

import org.springframework.http.HttpStatus;

public class FolderAlreadyRestoredException extends FolderException {

  private static final long serialVersionUID = -5401661207899785105L;
  public static final String MESSAGE = "Folder already restored";

  public FolderAlreadyRestoredException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
