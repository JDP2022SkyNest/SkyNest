package com.htecgroup.skynest.exception.file;

import org.springframework.http.HttpStatus;

public class FileAlreadyDeletedException extends FileException {

  private static final String MESSAGE = "File already deleted";

  public FileAlreadyDeletedException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
