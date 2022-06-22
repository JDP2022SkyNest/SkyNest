package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.request.UserLoginRequest;

public class UserLoginRequestUtil extends BasicUtil {

  public static UserLoginRequest get() {
    return new UserLoginRequest(name, password);
  }
}
