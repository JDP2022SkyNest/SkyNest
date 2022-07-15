package com.htecgroup.skynest.exception.file;

import org.springframework.http.HttpStatus;

public class FileAlreadyInsideRootException extends FileException {

  public static final String MESSAGE = "File is already inside the root of the bucket";

  public FileAlreadyInsideRootException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
