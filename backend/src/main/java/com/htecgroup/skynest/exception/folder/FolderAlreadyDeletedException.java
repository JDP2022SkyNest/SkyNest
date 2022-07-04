package com.htecgroup.skynest.exception.folder;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class FolderAlreadyDeletedException extends SkyNestBaseException {
  public static final String MESSAGE = "Folder already deleted";

  public FolderAlreadyDeletedException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
