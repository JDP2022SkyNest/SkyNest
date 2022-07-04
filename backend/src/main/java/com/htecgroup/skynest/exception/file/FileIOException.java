package com.htecgroup.skynest.exception.file;

import org.springframework.http.HttpStatus;

public class FileIOException extends FileException {

  private static final long serialVersionUID = 4958934383116469227L;

  private static final String MESSAGE = "Failed to write file contents";

  public FileIOException() {
    super(MESSAGE, HttpStatus.BAD_REQUEST);
  }
}
