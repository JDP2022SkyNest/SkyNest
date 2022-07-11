package com.htecgroup.skynest.exception.folder;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class FolderAlreadyInsideFolderException extends SkyNestBaseException {

  public static final String MESSAGE = "Folder is already inside that folder";

  public FolderAlreadyInsideFolderException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
