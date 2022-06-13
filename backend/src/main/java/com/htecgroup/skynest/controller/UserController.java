package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.request.UserPasswordResetRequest;
import com.htecgroup.skynest.model.request.UserRegisterRequest;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.model.response.UserResponse;
import com.htecgroup.skynest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.htecgroup.skynest.util.UrlUtil.*;

@RestController
@RequestMapping(USERS_CONTROLLER_URL)
@AllArgsConstructor
@Log4j2
@Tag(name = "User API", description = "Operations to manipulate user")
public class UserController {

  private UserService userService;

  @Operation(summary = "Register new user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Registration successful",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"id\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + " \"email\": \"username@gmail.com\","
                                + "  \"name\": \"Name\","
                                + "  \"surname\": \"Surname\","
                                + "  \"phoneNumber\": \"38166575757\","
                                + "  \"address\": \"Local address\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Input fields are not in valid format",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"password format not valid\","
                                + " \"name cannot be null or empty\","
                                + " \"surname cannot be null or empty\","
                                + " \"email format is not valid\","
                                + " \"phoneNumber format not valid\"],"
                                + " \"status\": \"400\","
                                + " \"timestamp\": \"2022-06-03 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Email or phone number already in use",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"This email is already in use\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-03 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Internal Server Error")})
            })
      })
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content =
          @Content(
              schema = @Schema(implementation = UserRegisterRequest.class),
              mediaType = "application/json",
              examples = {
                @ExampleObject(
                    value =
                        "{\"email\": \"username@gmail.com\","
                            + " \"password\": \"paSword1\","
                            + "  \"name\": \"Name\","
                            + "  \"surname\": \"Surname\","
                            + "  \"phoneNumber\": \"38166575757\","
                            + "  \"address\": \"Local address\"}")
              }))
  @PostMapping(REGISTER_URL)
  public ResponseEntity<UserResponse> registerUser(
      @Valid @RequestBody UserRegisterRequest userRegisterRequest) {

    ResponseEntity<UserResponse> responseEntity =
        new ResponseEntity<>(userService.registerUser(userRegisterRequest), HttpStatus.OK);
    return responseEntity;
  }

  @Operation(summary = "Confirm email")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Email confirmed",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "User verified successfully")})
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Email confirmation failed",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "Jwt token failed the validation. For more information check the logger")
                  })
            })
      })
  @GetMapping(CONFIRM_EMAIL_URL)
  public ResponseEntity<String> confirmEmail(@RequestParam String token) {
    String response = userService.confirmEmail(token);
    log.info(response);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Resend email for verification")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Email resent",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Email resent successfully")})
            }),
        @ApiResponse(
            responseCode = "500",
            description =
                "User already registered, not a valid email address or failed to resend email",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Failed to send email")})
            })
      })
  @PostMapping(RESEND_EMAIL_URL)
  public ResponseEntity<String> resendUserEmail(@RequestParam String email) {
    userService.sendVerificationEmail(email);
    String response = "Email resent successfully";
    log.info(response);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Request for password reset")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Password reset email successfully sent",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Password reset email sent")})
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Email does not exist",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "This email is not in use")})
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Access token is invalid")})
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Not a valid email address or failed to send email",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Failed to send email")})
            })
      })
  @PostMapping(PASSWORD_RESET_URL)
  public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
    userService.sendPasswordResetEmail(email);
    String response = "Password reset email sent";
    log.info(response);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Confirm password reset")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Password reset confirmed",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Password was successfully reset")})
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Access token is invalid")})
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Internal Server Error")})
            })
      })
  @PutMapping(PASSWORD_RESET_URL)
  public ResponseEntity<String> confirmPasswordReset(
      @Valid @RequestBody UserPasswordResetRequest userPasswordResetRequest) {
    String response =
        userService.resetPassword(
            userPasswordResetRequest.getToken(), userPasswordResetRequest.getPassword());
    log.info(response);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Get Users")
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_WORKER)")
  @GetMapping
  public List<UserResponse> getUsers() {
    List<UserResponse> listOfUsers = userService.listAllUsers();
    return listOfUsers;
  }

  @Operation(summary = "Get User with that Id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"id\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + " \"email\": \"username@gmail.com\","
                                + "  \"name\": \"Name\","
                                + "  \"surname\": \"Surname\","
                                + "  \"phoneNumber\": \"38166575757\","
                                + "  \"address\": \"Local address\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User with id a6fd6d95-0a60-43ff-961f-2b9b2ff72f95 doesn't exist\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Internal Server Error")})
            })
      })
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_WORKER)")
  @GetMapping("/{uuid}")
  public ResponseEntity<UserResponse> getUser(@PathVariable UUID uuid) {
    UserResponse userResponse = userService.getUser(uuid);
    ResponseEntity<UserResponse> userResponseEntity =
        new ResponseEntity<>(userResponse, HttpStatus.OK);
    return userResponseEntity;
  }

  @Operation(summary = "Edit User")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Edited User returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"id\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + " \"email\": \"username@gmail.com\","
                                + "  \"name\": \"Name\","
                                + "  \"surname\": \"Surname\","
                                + "  \"phoneNumber\": \"38166575757\","
                                + "  \"address\": \"Local address\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User with id a6fd6d95-0a60-43ff-961f-2b9b2ff72f95 doesn't exist\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Internal Server Error")})
            })
      })
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_WORKER)")
  @PutMapping("/edit/{uuid}")
  public ResponseEntity<UserResponse> editUser(
      @Valid @RequestBody UserRegisterRequest userEditRequest, @PathVariable UUID uuid) {
    ResponseEntity<UserResponse> responseEntity =
        new ResponseEntity<>(userService.editUser(userEditRequest, uuid), HttpStatus.OK);
    log.info("User {} is successfully edited.", userEditRequest.getEmail());
    return responseEntity;
  }

  @Operation(summary = "Delete User with that id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User successfully deleted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {
                    @ExampleObject(value = "User was successfully deleted from database")
                  })
            }),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User with id a6fd6d95-0a60-43ff-961f-2b9b2ff72f95 doesn't exist\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Internal Server Error")})
            })
      })
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_WORKER)")
  @DeleteMapping("/{uuid}")
  public ResponseEntity<String> deleteUser(@PathVariable UUID uuid) {
    userService.deleteUser(uuid);
    String deleteSuccess = "User was successfully deleted from database";
    log.info(deleteSuccess);
    return ResponseEntity.ok(deleteSuccess);
  }
}
