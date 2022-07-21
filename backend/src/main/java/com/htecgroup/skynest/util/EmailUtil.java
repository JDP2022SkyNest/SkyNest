package com.htecgroup.skynest.util;

import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.email.Email;

import java.util.Map;

public class EmailUtil {

  public static Email createVerificationEmail(UserDto userDto, String token) {
    Map<String, String> arguments =
        Map.of("name", userDto.getName(), "link", UrlUtil.getEmailVerificationLink(token));

    return new Email(userDto.getEmail(), EmailType.EMAIL_VERIFICATION, arguments, true);
  }

  public static Email createPasswordResetEmail(UserDto userDto, String token) {
    Map<String, String> arguments =
        Map.of("name", userDto.getName(), "link", UrlUtil.getPasswordResetLink(token));

    return new Email(userDto.getEmail(), EmailType.PASSWORD_RESET, arguments, true);
  }

  public static Email createPasswordChangeNotificationEmail(UserDto userDto) {
    Map<String, String> arguments = Map.of("name", userDto.getName());
    return new Email(userDto.getEmail(), EmailType.PASSWORD_CHANGE_NOTIFICATION, arguments, true);
  }

  public static Email createRegistrationInviteEmail(
      LoggedUserDto userDto, String newUserEmail, String token) {
    Map<String, String> arguments =
        Map.of(
            "name",
            userDto.getName(),
            "surname",
            userDto.getSurname(),
            "companyName",
            userDto.getCompany().getName(),
            "link",
            UrlUtil.getRegistrationInviteLink(token, newUserEmail, userDto.getCompany().getName()));
    return new Email(newUserEmail, EmailType.REGISTRATION_INVITE, arguments, true);
  }
}
