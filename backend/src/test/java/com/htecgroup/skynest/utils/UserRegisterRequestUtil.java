package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.request.UserRegisterRequest;

public class UserRegisterRequestUtil extends BasicUtil {

  public static UserRegisterRequest get() {
    return new UserRegisterRequest(email, password, name, surname, phoneNumber, address);
  }

  public static UserRegisterRequest edit() {
    return new UserRegisterRequest(
        "editedEmail@email.com",
        "editedPassword",
        "editedName",
        "editedSurname",
        "1221",
        "editedAddress");
  }
}
