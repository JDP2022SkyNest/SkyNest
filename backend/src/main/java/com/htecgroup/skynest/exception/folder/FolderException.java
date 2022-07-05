package com.htecgroup.skynest.exception.folder;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class FolderException extends SkyNestBaseException {

  private static final long serialVersionUID = 8988934433128652415L;

  public FolderException(String message, HttpStatus status) {
    super(message, status);
  }
}
