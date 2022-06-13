package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.UserRegisterRequest;
import com.htecgroup.skynest.model.response.UserResponse;
import com.htecgroup.skynest.repository.RoleRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.EmailService;
import com.htecgroup.skynest.util.EmailUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock private UserRepository userRepository;
  @Mock private RoleRepository roleRepository;
  @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Mock private EmailUtils emailUtils;
  @Spy private ModelMapper modelMapper;
  @Spy private EmailService emailService;

  @Spy @InjectMocks private UserServiceImpl userService;

  private UserEntity enabledWorkerEntity;
  private RoleEntity roleWorkerEntity;
  private UserRegisterRequest newUserRequest;

  @BeforeEach
  void setUp() {

    modelMapper = new ModelMapper();

    LocalDateTime currentDateTime = LocalDateTime.now();

    roleWorkerEntity = new RoleEntity(UUID.randomUUID(), RoleEntity.ROLE_WORKER);
    enabledWorkerEntity =
        new UserEntity(
            UUID.randomUUID(),
            currentDateTime,
            currentDateTime,
            null,
            "test@test.com",
            "$2a$12$R9h/cIPz0gi.URNNX3kh2OPST9/PgBkqquzi.Ss7KIUgO2t0jWMUW",
            "Name",
            "Surname",
            "Address",
            "381123456789",
            true,
            true,
            roleWorkerEntity,
            null);

    newUserRequest = new UserRegisterRequest();
    newUserRequest.setEmail("test@test.com");
    newUserRequest.setPassword("123456");
    newUserRequest.setName("Name");
    newUserRequest.setSurname("Surname");
    newUserRequest.setPhoneNumber("38112345689");
    newUserRequest.setAddress("Address");
  }

  /*@Test
  void registerUser() {

    UserEntity expectedUserEntity = enabledWorkerEntity;
    expectedUserEntity.setEnabled(false);
    expectedUserEntity.setVerified(false);
    UserDto expectedUserDto = new ModelMapper().map(expectedUserEntity, UserDto.class);

    when(userRepository.existsByEmail(anyString())).thenReturn(false);
    when(roleRepository.findByName(anyString()))
        .thenReturn(Optional.of(new RoleEntity(UUID.randomUUID(), RoleEntity.ROLE_WORKER)));
    when(userRepository.save(any())).thenReturn(expectedUserEntity);
    when(bCryptPasswordEncoder.encode(anyString()))
        .thenReturn(expectedUserDto.getEncryptedPassword());

    doNothing().when(userService).sendVerificationEmail(anyString());

    UserDto newUserDto = new ModelMapper().map(newUserRequest, UserDto.class);
    UserDto actualUserDto = userService.registerUser(newUserDto);

    Assertions.assertEquals(expectedUserDto, actualUserDto);
  }*/

  @Test
  void registerUser_AlreadyExists() {

    when(userRepository.existsByEmail(anyString())).thenReturn(true);

    Assertions.assertThrows(UserException.class, () -> userService.registerUser(newUserRequest));
  }

  /* @Test
  void registerUser_RoleNotFound() {

    when(userRepository.existsByEmail(anyString())).thenReturn(false);
    when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());

    UserDto newUserDto = new ModelMapper().map(newUserRequest, UserDto.class);

    Assertions.assertThrows(UserException.class, () -> userService.registerUser(newUserDto));
  }*/

  @Test
  void findUserByEmail() {

    when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(enabledWorkerEntity));

    UserDto expectedUserDto = userService.findUserByEmail(enabledWorkerEntity.getEmail());

    Assertions.assertEquals(
        new ModelMapper().map(enabledWorkerEntity, UserDto.class), expectedUserDto);
  }

  @Test
  void getUser_IdNotFound() {
    when(userRepository.findById(any())).thenReturn(Optional.empty());

    UserException ex =
        Assertions.assertThrows(UserException.class, () -> userService.getUser(UUID.randomUUID()));

    Assertions.assertEquals(UserExceptionType.USER_NOT_FOUND.getMessage(), ex.getMessage());
  }

  /*  @Test
  void getUser() {
    when(userRepository.findById(any())).thenReturn(Optional.of(enabledWorkerEntity));

    UserDto expectedUserDto = userService.getUser(enabledWorkerEntity.getId());

    Assertions.assertEquals(
        new ModelMapper().map(enabledWorkerEntity, UserDto.class), expectedUserDto);
  }*/

  @Test
  void findUserByEmail_NoSuchUser() {

    when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());

    Assertions.assertThrows(
        UsernameNotFoundException.class,
        () -> userService.findUserByEmail(enabledWorkerEntity.getEmail()));
  }

  @Test
  void confirmEmail() {
    String expectedResponse = "User verified successfully";

    UserEntity disabledWorkerEntity = enabledWorkerEntity;
    disabledWorkerEntity.setEnabled(false);
    disabledWorkerEntity.setVerified(false);
    when(emailUtils.getEmailFromJwtEmailToken(anyString()))
        .thenReturn(disabledWorkerEntity.getEmail());
    when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(disabledWorkerEntity));
    when(userRepository.save(any())).thenReturn(enabledWorkerEntity);

    Assertions.assertEquals(expectedResponse, userService.confirmEmail(anyString()));
  }

  @Test
  void confirmEmail_UserWithEmailNotFound() {
    UserEntity disabledWorkerEntity = enabledWorkerEntity;
    disabledWorkerEntity.setEnabled(false);
    disabledWorkerEntity.setVerified(false);
    when(emailUtils.getEmailFromJwtEmailToken(anyString()))
        .thenReturn(disabledWorkerEntity.getEmail());
    when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());

    Assertions.assertThrows(
        UsernameNotFoundException.class, () -> userService.confirmEmail(anyString()));
  }

  @Test
  void listAllUsers() {
    List<UserEntity> userEntityList = new ArrayList<>();
    userEntityList.add(enabledWorkerEntity);
    when(userRepository.findAll()).thenReturn(userEntityList);

    List<UserResponse> expectedResponse =
        userEntityList.stream()
            .map(e -> modelMapper.map(e, UserResponse.class))
            .collect(Collectors.toList());

    List<UserResponse> returnedResponse = userService.listAllUsers();

    Assertions.assertEquals(expectedResponse.size(), returnedResponse.size());
    Assertions.assertEquals(expectedResponse.get(0), returnedResponse.get(0));
    Assertions.assertEquals(expectedResponse.get(0).getEmail(), returnedResponse.get(0).getEmail());
  }

  @Test
  void resetPassword() {
    String expectedResponse = "Password was successfully reset";

    when(emailUtils.getEmailFromJwtEmailToken(anyString()))
        .thenReturn(enabledWorkerEntity.getEmail());
    when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(enabledWorkerEntity));
    when(userRepository.save(any())).thenReturn(enabledWorkerEntity);

    Assertions.assertEquals(expectedResponse, userService.resetPassword(anyString(), anyString()));
  }

  @Test
  void editUser() {
    // when(userRepository.findById(any())).thenReturn(Optional.of(enabledWorkerEntity));
    UserRegisterRequest expectedUser = new UserRegisterRequest();
    expectedUser.setName("Name2");
    expectedUser.setPassword("123456");
    expectedUser.setSurname("Surname2");
    expectedUser.setAddress("Address");
    modelMapper.map(expectedUser, enabledWorkerEntity);

    Assertions.assertEquals(expectedUser.getName(), enabledWorkerEntity.getName());
    Assertions.assertEquals(expectedUser.getEmail(), enabledWorkerEntity.getEmail());
    Assertions.assertEquals(expectedUser.getSurname(), enabledWorkerEntity.getSurname());
  }
}
