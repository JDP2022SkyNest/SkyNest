package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.request.UserRegisterRequest;
import com.htecgroup.skynest.model.response.UserResponse;
import com.htecgroup.SkyNest.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired private UserService userService;

  @PostMapping("/register")
  public UserResponse registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {

    ModelMapper modelMapper = new ModelMapper();

    UserDto userDto = modelMapper.map(userRegisterRequest, UserDto.class);
    userDto = userService.registerUser(userDto);

    // for testing

    return modelMapper.map(userDto, UserResponse.class);
  }

  @GetMapping("/confirm")
  public Boolean confirmEmail(@RequestParam String token) {
    return userService.confirmEmail(token);
  }

  @GetMapping
  public String getUser() {
    //  TODO
    return "test";
  }
}
