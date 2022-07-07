package com.htecgroup.skynest.exception.folder;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class ParentFolderIsNotInTheSameBucketException extends SkyNestBaseException {

  public static final String MESSAGE = "Parent folder is not in the same bucket";

  public ParentFolderIsNotInTheSameBucketException() {
    super(MESSAGE, HttpStatus.EXPECTATION_FAILED);
  }
}
