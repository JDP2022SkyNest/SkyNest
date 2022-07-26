package com.htecgroup.skynest.exception.file;

import org.springframework.http.HttpStatus;

public class FileParentIsDeletedException extends FileException {

  public static final String MESSAGE = "File's parent is deleted";

  public FileParentIsDeletedException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
