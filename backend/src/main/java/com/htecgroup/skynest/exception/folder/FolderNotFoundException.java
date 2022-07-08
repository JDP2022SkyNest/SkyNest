package com.htecgroup.skynest.exception.folder;

import org.springframework.http.HttpStatus;

public class FolderNotFoundException extends FolderException {

  private static final long serialVersionUID = 828559124951834449L;

  private static final String MESSAGE = "Folder not found";

  public FolderNotFoundException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
