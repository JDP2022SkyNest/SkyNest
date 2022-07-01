package com.htecgroup.skynest.exception.folder;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class FolderNotFoundException extends SkyNestBaseException {

  public static final String MESSAGE = "Folder not found";

  public FolderNotFoundException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
