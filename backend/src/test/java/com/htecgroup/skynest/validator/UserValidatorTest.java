package com.htecgroup.skynest.validator;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.UserDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

  private UserValidator userValidator;
  private UserDto userDto;
  private UserException userException;

  @BeforeEach
  void setUp() {
    userValidator = new UserValidator();
    userDto = new UserDto();
    userDto.setEmail("username@gmail.com");
    userDto.setPassword("Mm1asdass");
    userDto.setName("Name");
    userDto.setSurname("Surname");
    userDto.setAddress("Cara Lazara 5");
    userDto.setPhoneNumber("0641234567");
  }

  @Test
  void isUserEmailValidNUll() {

    userDto.setEmail(null);

    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.EMAIL_NOT_VALID.getMessage(), userException.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"", " "})
  void isUserEmailValidBlank(String mail) {

    userDto.setEmail(mail);

    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.EMAIL_NOT_VALID.getMessage(), userException.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"username", "username@", "username@gmail"})
  void isUserEmailValidBadFormat(String mail) {

    userDto.setEmail(mail);

    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.EMAIL_NOT_VALID.getMessage(), userException.getMessage());
  }

  @Test
  void isUserPasswordValidNUll() {

    userDto.setPassword(null);

    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(
        UserExceptionType.INVALID_PASSWORD_FORMAT.getMessage(), userException.getMessage());
  }

  @ParameterizedTest
  @ValueSource(
      strings = {"abcdabc", "abcdabcd", "abcdabcD", "abcdabc1", "ABCDABCD", "12341234", "ABCDABC1"})
  void isUserPasswordValidBadFormat(String pass) {

    userDto.setPassword(pass);
    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(
        UserExceptionType.INVALID_PASSWORD_FORMAT.getMessage(), userException.getMessage());
  }

  @Test
  void isUserNameValidNUll() {

    userDto.setName(null);

    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.NAME_NOT_VALID.getMessage(), userException.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"", " "})
  void isUserNameValidEmpty(String name) {

    userDto.setName(name);
    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.NAME_NOT_VALID.getMessage(), userException.getMessage());
  }

  @Test
  void isUserNameValidTooLong() {

    String name = RandomStringUtils.random(51, true, false);
    userDto.setName(name);

    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.NAME_NOT_VALID.getMessage(), userException.getMessage());
  }

  @Test
  void isUserSurameValidNUll() {

    userDto.setSurname(null);

    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.SURNAME_NOT_VALID.getMessage(), userException.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"", " "})
  void isUserSurnameValidEmpty(String surname) {

    userDto.setSurname(surname);
    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.SURNAME_NOT_VALID.getMessage(), userException.getMessage());
  }

  @Test
  void isUserSurnameValidTooLong() {

    String surname = RandomStringUtils.random(101, true, false);
    userDto.setSurname(surname);

    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.SURNAME_NOT_VALID.getMessage(), userException.getMessage());
  }

  @Test
  void isUserAddressValidNUll() {

    userDto.setAddress(null);

    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.ADRESS_NOT_VALID.getMessage(), userException.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"", " "})
  void isUserAddressValidEmpty(String address) {

    userDto.setAddress(address);
    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.ADRESS_NOT_VALID.getMessage(), userException.getMessage());
  }

  @Test
  void isUserAddressValidTooLong() {

    String address = RandomStringUtils.random(255, true, false);
    userDto.setAddress(address);

    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.ADRESS_NOT_VALID.getMessage(), userException.getMessage());
  }

  @Test
  void isUserPhoneNumberValidNUll() {

    userDto.setPhoneNumber(null);

    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.PHONE_NOT_VALID.getMessage(), userException.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"", " "})
  void isUserPhoneNumberValidBlank(String phoneNumber) {

    userDto.setPhoneNumber(phoneNumber);

    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.PHONE_NOT_VALID.getMessage(), userException.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"+121352", "username"})
  void isUserPhoneNumberValidBadFormat(String phoneNumber) {

    userDto.setPhoneNumber(phoneNumber);

    userException = assertThrows(UserException.class, () -> userValidator.isUserValid(userDto));
    assertEquals(UserExceptionType.PHONE_NOT_VALID.getMessage(), userException.getMessage());
  }
}
