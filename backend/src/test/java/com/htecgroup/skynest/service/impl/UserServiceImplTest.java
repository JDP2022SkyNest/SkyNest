package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.RoleDto;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.UserRegisterRequest;
import com.htecgroup.skynest.model.response.UserResponse;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.RoleService;
import com.htecgroup.skynest.util.EmailUtils;
import com.htecgroup.skynest.utils.UserDtoUtil;
import com.htecgroup.skynest.utils.UserEntityUtil;
import com.htecgroup.skynest.utils.UserRegisterRequestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock private UserRepository userRepository;
  @Mock private RoleService roleService;
  @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Mock private EmailUtils emailUtils;
  @Spy private ModelMapper modelMapper;

  @Spy @InjectMocks private UserServiceImpl userService;

  @Test
  void registerUser() {

    UserEntity expectedUserEntity = UserEntityUtil.getNotVerified();

    when(userRepository.existsByEmail(anyString())).thenReturn(false);
    when(roleService.findByName(anyString())).thenReturn(mock(RoleDto.class));
    when(userRepository.save(any())).thenReturn(expectedUserEntity);
    when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encryptedPassword");

    doNothing().when(userService).sendVerificationEmail(anyString());

    UserRegisterRequest userRegisterRequest = UserRegisterRequestUtil.get();
    UserResponse actualUserResponse = userService.registerUser(userRegisterRequest);

    this.assertUserEntityAndUserResponse(expectedUserEntity, actualUserResponse);
  }

  @Test
  void registerUser_AlreadyExistsByEmail() {

    when(userRepository.existsByEmail(anyString())).thenReturn(true);
    String expectedErrorMessage = UserExceptionType.EMAIL_ALREADY_IN_USE.getMessage();

    UserRegisterRequest userRegisterRequest = UserRegisterRequestUtil.get();

    Exception thrownException =
        Assertions.assertThrows(
            UserException.class, () -> userService.registerUser(userRegisterRequest));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void registerUser_AlreadyExistsByPhone() {

    when(userRepository.existsByEmail(anyString())).thenReturn(false);
    when(userRepository.existsByPhoneNumber(anyString())).thenReturn(true);
    String expectedErrorMessage = UserExceptionType.PHONE_NUMBER_ALREADY_IN_USE.getMessage();

    UserRegisterRequest userRegisterRequest = UserRegisterRequestUtil.get();

    Exception thrownException =
        Assertions.assertThrows(
            UserException.class, () -> userService.registerUser(userRegisterRequest));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void findUserByEmail() {
    UserEntity userEntity = UserEntityUtil.getNotVerified();
    when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(userEntity));

    UserDto actualUserDto = userService.findUserByEmail(userEntity.getEmail());
    this.assertUserDtoAndUserEntity(userEntity, actualUserDto);
  }

  @Test
  void getUser_IdNotFound() {
    when(userRepository.findById(any())).thenReturn(Optional.empty());
    UUID uuid = UUID.randomUUID();
    UserException ex =
        Assertions.assertThrows(UserException.class, () -> userService.getUser(uuid));

    Assertions.assertEquals(UserExceptionType.USER_NOT_FOUND.getMessage(), ex.getMessage());
  }

  @Test
  void getUser() {
    UserEntity userEntity = UserEntityUtil.getNotVerified();
    when(userRepository.findById(any())).thenReturn(Optional.of(userEntity));

    UserResponse actualUserResponse = userService.getUser(userEntity.getId());

    this.assertUserEntityAndUserResponse(userEntity, actualUserResponse);
  }

  @Test
  void findUserByEmail_NoSuchUser() {

    when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());
    String expectedErrorMessage = UserExceptionType.USER_NOT_FOUND.getMessage();
    Exception thrownException =
        Assertions.assertThrows(
            UserException.class, () -> userService.findUserByEmail("email@email.com"));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void confirmEmail() {
    String expectedResponse = "User verified successfully";
    String testEmail = "confirmEmail@email.com";
    UserDto userMock = mock(UserDto.class);
    when(emailUtils.getEmailFromJwtEmailToken(anyString())).thenReturn(testEmail);
    doReturn(userMock).when(userService).findUserByEmail(anyString());
    doReturn(userMock).when(userService).verifyUser(any());
    when(userRepository.save(any())).thenReturn(mock(UserEntity.class));

    Assertions.assertEquals(expectedResponse, userService.confirmEmail("Token"));
  }

  @Test
  void listAllUsers() {
    List<UserEntity> userEntityList = new ArrayList<>();
    userEntityList.add(UserEntityUtil.getVerified());
    when(userRepository.findAll()).thenReturn(userEntityList);

    List<UserEntity> expectedResponse = new ArrayList<>(userEntityList);

    List<UserResponse> actualResponse = userService.listAllUsers();

    Assertions.assertEquals(expectedResponse.size(), actualResponse.size());
    this.assertUserEntityAndUserResponse(expectedResponse.get(0), actualResponse.get(0));
  }

  @Test
  void resetPassword() {
    String expectedResponse = "Password was successfully reset";
    UserEntity verifiedUserEntity = UserEntityUtil.getVerified();
    when(emailUtils.getEmailFromJwtEmailToken(anyString()))
        .thenReturn(verifiedUserEntity.getEmail());
    when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(verifiedUserEntity));
    when(userRepository.save(any())).thenReturn(verifiedUserEntity);

    Assertions.assertEquals(expectedResponse, userService.resetPassword(anyString(), anyString()));
  }

  @Test
  void verifyEmail() {
    doReturn(false).when(userService).isActive(anyString());
    UserDto verifiedUser = userService.verifyUser(UserDtoUtil.getNotVerified());

    Assertions.assertTrue(verifiedUser.getVerified());
    Assertions.assertTrue(verifiedUser.getEnabled());
  }

  @Test
  void verifyUser_UserAlreadyVerified() {
    UserDto verifiedUserDto = UserDtoUtil.getVerified();
    String expectedErrorMessage = UserExceptionType.USER_ALREADY_REGISTERED.getMessage();

    doReturn(true).when(userService).isActive(anyString());

    Exception thrownException =
        Assertions.assertThrows(UserException.class, () -> userService.verifyUser(verifiedUserDto));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void isActive_True() {
    UserDto verifiedUserDto = UserDtoUtil.getVerified();
    doReturn(verifiedUserDto).when(userService).findUserByEmail(anyString());
    boolean returnedValue = userService.isActive(verifiedUserDto.getEmail());
    Assertions.assertTrue(returnedValue);
  }

  @Test
  void isActive_NotVerifiedUser() {
    UserDto deletedUserDto = UserDtoUtil.getNotVerified();
    deletedUserDto.setDeletedOn(LocalDateTime.now());
    doReturn(deletedUserDto).when(userService).findUserByEmail(anyString());
    boolean returnedValue = userService.isActive(deletedUserDto.getEmail());
    Assertions.assertFalse(returnedValue);
  }

  @Test
  void deleteUser_UserDoesNotExist() {
    when(userRepository.existsById(any())).thenReturn(false);
    UUID uuid = UUID.randomUUID();
    String expectedErrorMessage = String.format("User with id %s doesn't exist", uuid);
    Exception thrownException =
        Assertions.assertThrows(UserException.class, () -> userService.deleteUser(uuid));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void editUser() {
    UserRegisterRequest editedUser = UserRegisterRequestUtil.edit();
    UserEntity userEntityThatShouldBeEdited = UserEntityUtil.getVerified();

    when(userRepository.findById(any())).thenReturn(Optional.of(userEntityThatShouldBeEdited));
    when(userRepository.save(any())).thenReturn(userEntityThatShouldBeEdited);
    UserResponse userResponse = userService.editUser(editedUser, UUID.randomUUID());

    this.assertUserEntityAndUserResponse(userEntityThatShouldBeEdited, userResponse);
  }

  private void assertUserEntityAndUserResponse(
      UserEntity expectedUserEntity, UserResponse actualUserResponse) {
    Assertions.assertEquals(expectedUserEntity.getId().toString(), actualUserResponse.getId());
    Assertions.assertEquals(expectedUserEntity.getEmail(), actualUserResponse.getEmail());
    Assertions.assertEquals(expectedUserEntity.getName(), actualUserResponse.getName());
    Assertions.assertEquals(expectedUserEntity.getSurname(), actualUserResponse.getSurname());
    Assertions.assertEquals(expectedUserEntity.getAddress(), actualUserResponse.getAddress());
    Assertions.assertEquals(
        expectedUserEntity.getPhoneNumber(), actualUserResponse.getPhoneNumber());
  }

  private void assertUserDtoAndUserEntity(UserEntity expectedUserEntity, UserDto actualUserDto) {
    if (expectedUserEntity.getCompany() != null && actualUserDto.getCompany() != null) {
      Assertions.assertEquals(
          expectedUserEntity.getCompany().getId(), actualUserDto.getCompany().getId());
    }
    Assertions.assertEquals(expectedUserEntity.getId(), actualUserDto.getId());
    Assertions.assertEquals(expectedUserEntity.getCreatedOn(), actualUserDto.getCreatedOn());
    Assertions.assertEquals(expectedUserEntity.getModifiedOn(), actualUserDto.getModifiedOn());
    Assertions.assertEquals(expectedUserEntity.getDeletedOn(), actualUserDto.getDeletedOn());
    Assertions.assertEquals(expectedUserEntity.getEmail(), actualUserDto.getEmail());
    Assertions.assertEquals(
        expectedUserEntity.getEncryptedPassword(), actualUserDto.getEncryptedPassword());
    Assertions.assertEquals(expectedUserEntity.getName(), actualUserDto.getName());
    Assertions.assertEquals(expectedUserEntity.getSurname(), actualUserDto.getSurname());
    Assertions.assertEquals(expectedUserEntity.getAddress(), actualUserDto.getAddress());
    Assertions.assertEquals(expectedUserEntity.getPhoneNumber(), actualUserDto.getPhoneNumber());
    Assertions.assertEquals(expectedUserEntity.getVerified(), actualUserDto.getVerified());
    Assertions.assertEquals(expectedUserEntity.getEnabled(), actualUserDto.getEnabled());
    Assertions.assertEquals(expectedUserEntity.getRole().getId(), actualUserDto.getRole().getId());
  }
}
