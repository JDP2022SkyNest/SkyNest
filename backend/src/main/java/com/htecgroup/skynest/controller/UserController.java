package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.request.UserRegisterRequest;
import com.htecgroup.skynest.model.response.UserResponse;
import com.htecgroup.skynest.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired private UserService userService;
  @Autowired private ModelMapper modelMapper;

  @PostMapping("/register")
  public UserResponse registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {

    UserDto userDto = userService.registerUser(modelMapper.map(userRegisterRequest, UserDto.class));

    return modelMapper.map(userDto, UserResponse.class);
  }

  @GetMapping
  public String getUser() {
    //  TODO
    return "test";
  }
}
