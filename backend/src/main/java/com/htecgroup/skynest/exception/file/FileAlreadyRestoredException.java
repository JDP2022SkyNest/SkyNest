package com.htecgroup.skynest.exception.file;

import org.springframework.http.HttpStatus;

public class FileAlreadyRestoredException extends FileException {

  public static final String MESSAGE = "File already restored";

  public FileAlreadyRestoredException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
