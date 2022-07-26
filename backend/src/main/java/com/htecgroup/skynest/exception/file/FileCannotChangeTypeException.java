package com.htecgroup.skynest.exception.file;

import org.springframework.http.HttpStatus;

public class FileCannotChangeTypeException extends FileException {

  private static final long serialVersionUID = 5632178536591315853L;
  private static final String MESSAGE =
      "Cannot change file type, please upload content as a new file.";

  public FileCannotChangeTypeException() {
    super(MESSAGE, HttpStatus.BAD_REQUEST);
  }
}
