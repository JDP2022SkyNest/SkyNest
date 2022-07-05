package com.htecgroup.skynest.exception.folder;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class ParentFolderDoesntExistException extends SkyNestBaseException {

  public static final String MESSAGE = "Parent folder doesn't exist";

  public ParentFolderDoesntExistException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
