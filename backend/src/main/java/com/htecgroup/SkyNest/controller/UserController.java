package com.htecgroup.SkyNest.controller;

import com.htecgroup.SkyNest.model.dto.UserDto;
import com.htecgroup.SkyNest.model.request.UserRegisterRequest;
import com.htecgroup.SkyNest.model.response.UserResponse;
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

    UserDto userDto =
        userService.registerUser(new ModelMapper().map(userRegisterRequest, UserDto.class));

    return new ModelMapper().map(userDto, UserResponse.class);
  }

  @GetMapping
  public String getUser() {
    //  TODO
    return "test";
  }
}
