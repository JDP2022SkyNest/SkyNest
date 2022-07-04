package com.htecgroup.skynest.exception.file;

import org.springframework.http.HttpStatus;

public class FileAlreadyExistsException extends FileException {

  private static final long serialVersionUID = -2544528103036824968L;

  private static final String MESSAGE = "File with the same name already exists in folder";

  public FileAlreadyExistsException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
