package com.htecgroup.SkyNest.controller;

import com.htecgroup.SkyNest.dto.UserDto;
import com.htecgroup.SkyNest.io.request.UserLoginRequest;
import com.htecgroup.SkyNest.io.request.UserRegisterRequest;
import com.htecgroup.SkyNest.io.response.UserResponse;
import com.htecgroup.SkyNest.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired private UserService userService;

  @PostMapping("/register")
  public UserResponse registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
    UserResponse response = new UserResponse();

    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userRegisterRequest, userDto);

    userDto = userService.registerUser(userDto);
    BeanUtils.copyProperties(userDto, response);

    return response;
  }

  @GetMapping
  public String getUser(){
    //  TODO
    return "test";
  }
}
