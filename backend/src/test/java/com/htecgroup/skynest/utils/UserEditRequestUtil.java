package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.request.UserEditRequest;

public class UserEditRequestUtil {
  public static UserEditRequest get() {
    return new UserEditRequest("editedName", "editedSurname", "1221", "editedAddress");
  }
}
