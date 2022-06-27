package com.htecgroup.skynest.service;

public interface PasswordEncoderService {
  String encode(String passwordToEncode);

  Boolean matches(CharSequence rawPassword, String encodedPassword);
}
