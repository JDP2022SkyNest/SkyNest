package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.request.UserPasswordResetRequest;

public interface AuthService {

  String confirmEmail(String token);

  String resetPassword(UserPasswordResetRequest userPasswordResetRequest);

  UserDto verifyUser(UserDto userDto);

  void sendVerificationEmail(String emailAddress);

  void sendPasswordResetEmail(String emailAddress);
}
