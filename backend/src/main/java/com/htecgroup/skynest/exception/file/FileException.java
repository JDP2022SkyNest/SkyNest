package com.htecgroup.skynest.exception.file;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class FileException extends SkyNestBaseException {

  private static final long serialVersionUID = -1918628912463423537L;

  protected FileException(String message, HttpStatus status) {
    super(message, status);
  }
}
