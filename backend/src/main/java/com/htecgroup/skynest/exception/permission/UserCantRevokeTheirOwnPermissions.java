package com.htecgroup.skynest.exception.permission;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class UserCantRevokeTheirOwnPermissions extends SkyNestBaseException {

  public static final String MESSAGE = "User cant revoke their own permissions.";

  public UserCantRevokeTheirOwnPermissions() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
