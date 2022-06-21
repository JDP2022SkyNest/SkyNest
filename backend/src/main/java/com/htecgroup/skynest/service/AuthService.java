package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.request.UserPasswordResetRequest;

public interface AuthService {

  void sendVerificationEmail(String email);

  void sendPasswordResetEmail(String email);

  String resetPassword(UserPasswordResetRequest userPasswordResetRequest);

  String confirmEmail(String token);

  boolean isActive(String email);

  UserDto verifyUser(UserDto userDto);
}
