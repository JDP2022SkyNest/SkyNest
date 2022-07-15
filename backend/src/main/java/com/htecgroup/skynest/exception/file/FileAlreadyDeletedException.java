package com.htecgroup.skynest.exception.file;

import org.springframework.http.HttpStatus;

public class FileAlreadyDeletedException extends FileException {

  public static final String MESSAGE = "File already deleted";

  public FileAlreadyDeletedException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
