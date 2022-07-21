package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.request.UserRegisterRequest;

public class UserRegisterRequestUtil extends BasicUtil {

  public static UserRegisterRequest get() {
    return new UserRegisterRequest(password, name, surname, phoneNumber, address);
  }
}
