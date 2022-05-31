package com.htecgroup.skynest.validator;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidator {

  private static final String PASSWORD_PATTERN =
      "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,50}$";
  private static final String PHONE_NUMBER_PATTERN = "[\\d]{1,30}$";

  private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
  private static final Pattern phoneNumberPattern = Pattern.compile(PHONE_NUMBER_PATTERN);

  public void validateUser(UserDto userDto) {

    String email = userDto.getEmail();

    if (!EmailValidator.getInstance().isValid(email) || email.length() > 254) {
      throw new UserException(UserExceptionType.EMAIL_NOT_VALID);
    }

    String password = userDto.getPassword();
    if (password == null || !passwordPattern.matcher(password).matches()) {
      throw new UserException(UserExceptionType.INVALID_PASSWORD_FORMAT);
    }

    String name = userDto.getName();
    if (StringUtils.isBlank(name) || name.length() > 50) {
      throw new UserException(UserExceptionType.NAME_NOT_VALID);
    }

    String surname = userDto.getSurname();
    if (StringUtils.isBlank(surname) || surname.length() > 100) {
      throw new UserException(UserExceptionType.SURNAME_NOT_VALID);
    }

    String address = userDto.getAddress();
    if (StringUtils.isBlank(address) || address.length() > 254) {
      throw new UserException(UserExceptionType.ADRESS_NOT_VALID);
    }

    String phoneNumber = userDto.getPhoneNumber();
    if (phoneNumber == null || !phoneNumberPattern.matcher(phoneNumber).matches()) {
      throw new UserException(UserExceptionType.PHONE_NOT_VALID);
    }
  }
}
