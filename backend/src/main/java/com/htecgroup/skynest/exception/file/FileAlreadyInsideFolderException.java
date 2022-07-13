package com.htecgroup.skynest.exception.file;

import org.springframework.http.HttpStatus;

public class FileAlreadyInsideFolderException extends FileException {

  private static final String MESSAGE = "File is already inside the folder";

  public FileAlreadyInsideFolderException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
