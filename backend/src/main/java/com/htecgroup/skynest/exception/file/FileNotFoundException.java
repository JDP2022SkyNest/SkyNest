package com.htecgroup.skynest.exception.file;

import org.springframework.http.HttpStatus;

public class FileNotFoundException extends FileException {

  private static final long serialVersionUID = -3074825161860180655L;

  private static final String MESSAGE = "File not found";

  public FileNotFoundException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
