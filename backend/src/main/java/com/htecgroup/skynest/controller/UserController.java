package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.request.UserRegisterRequest;
import com.htecgroup.skynest.model.response.UserResponse;
import com.htecgroup.skynest.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sun.activation.registries.LogSupport.log;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Log4j2
public class UserController {

  private UserService userService;
  private ModelMapper modelMapper;

  @PostMapping("/register")
  public UserResponse registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {

    UserDto userDto = userService.registerUser(modelMapper.map(userRegisterRequest, UserDto.class));

    return modelMapper.map(userDto, UserResponse.class);
  }

  @GetMapping("/confirm")
  public ResponseEntity<String> confirmEmail(@RequestParam String token) {
    String response = userService.confirmEmail(token);
    log("User successfully confirmed");
    return ResponseEntity.ok(response);
  }

  @PostMapping("/resendEmail")
  public ResponseEntity<String> resendUserEmail(@RequestParam String email){
    userService.sendVerificationEmail(email);
    String response = "Email resent successfully";
    log("Email resend successfully");
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public String getUser() {
    //  TODO
    return "test";
  }
}
