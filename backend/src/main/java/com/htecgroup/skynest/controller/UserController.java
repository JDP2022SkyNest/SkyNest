package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.request.UserChangePasswordRequest;
import com.htecgroup.skynest.model.request.UserEditRequest;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.model.response.LoggedUserResponse;
import com.htecgroup.skynest.model.response.UserResponse;
import com.htecgroup.skynest.service.CurrentUserService;
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

import static com.htecgroup.skynest.util.UrlUtil.USERS_CONTROLLER_URL;

@RestController
@RequestMapping(USERS_CONTROLLER_URL)
@AllArgsConstructor
@Log4j2
@Tag(name = "User API", description = "User-related operations")
public class UserController {

  private UserService userService;
  private CurrentUserService currentUserService;

  @Operation(summary = "Get all users")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Users returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "[{\"id\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "\"email\": \"username@gmail.com\","
                                + "\"name\": \"Name\","
                                + "\"surname\": \"Surname\","
                                + "\"phoneNumber\": \"38166575757\","
                                + "\"address\": \"Local address\","
                                + "\"roleName\": \"role_admin\","
                                + "\"positionInCompany\": \"Software Engineer\","
                                + "\"companyName\": \"HTEC Group\","
                                + "\"enabled\": \"true\","
                                + "\"verified\": \"true\"},"
                                + "{\"id\": \"u7yd987h-0a79-42dd-961s-7sfh564kdv2s\","
                                + "\"email\": \"username123@gmail.com\","
                                + "\"name\": \"Name\","
                                + "\"surname\": \"Surname\","
                                + "\"phoneNumber\": \"38166676767\","
                                + "\"address\": \"Local address\","
                                + "\"roleName\": \"role_worker\","
                                + "\"positionInCompany\": \"Software Engineer\","
                                + "\"companyName\": \"HTEC Group\","
                                + "\"enabled\": \"false\","
                                + "\"verified\": \"false\"}]")
                  })
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid session token",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Invalid session token\"],"
                                + " \"status\": \"401\","
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
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN)")
  @GetMapping
  public List<UserResponse> getUsers() {
    List<UserResponse> listOfUsers = userService.listAllUsers();
    return listOfUsers;
  }

  @Operation(summary = "Get user")
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
                                + "  \"address\": \"Local address\","
                                + "  \"roleName\": \"role_worker\","
                                + "  \"positionInCompany\": \"Software Engineer\","
                                + "  \"companyName\": \"HTEC Group\","
                                + "  \"enabled\": \"false\","
                                + "  \"verified\": \"true\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid session token",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Invalid session token\"],"
                                + " \"status\": \"401\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Unauthorized request",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Access denied\"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
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
                            "{\"messages\":[\"User not found\"],"
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
  @PreAuthorize(
      "hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN) or hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_WORKER)")
  @GetMapping("/{userId}")
  public ResponseEntity<UserResponse> getUser(@PathVariable UUID userId) {
    UserResponse userResponse = userService.getUser(userId);
    ResponseEntity<UserResponse> userResponseEntity =
        new ResponseEntity<>(userResponse, HttpStatus.OK);
    return userResponseEntity;
  }

  @Operation(summary = "Edit user")
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
                                + "  \"address\": \"Local address\","
                                + "  \"roleName\": \"role_worker\","
                                + "  \"positionInCompany\": \"Software Engineer\","
                                + "  \"companyName\": \"HTEC Group\","
                                + "  \"enabled\": \"false\","
                                + "  \"verified\": \"true\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid session token",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Invalid session token\"],"
                                + " \"status\": \"401\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Unauthorized request",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Access denied\"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
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
  @PreAuthorize(
      "hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN) or hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_WORKER)")
  @PutMapping("/{userId}")
  public ResponseEntity<UserResponse> editUser(
      @Valid @RequestBody UserEditRequest userEditRequest, @PathVariable UUID userId) {
    ResponseEntity<UserResponse> responseEntity =
        new ResponseEntity<>(userService.editUser(userId, userEditRequest), HttpStatus.OK);
    log.info("User is successfully edited.");
    return responseEntity;
  }

  @Operation(summary = "Delete user")
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
            responseCode = "401",
            description = "Invalid session token",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Invalid session token\"],"
                                + " \"status\": \"401\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Unauthorized request",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Access denied\"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
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
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN)")
  @DeleteMapping("/{userId}")
  public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
    userService.deleteUser(userId);
    String deleteSuccess = "User was successfully deleted from database";
    log.info(deleteSuccess);
    return ResponseEntity.ok(deleteSuccess);
  }

  @Operation(summary = "Change password for user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Password successfully changed",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "true")})
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Wrong password",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Wrong password\"],"
                                + " \"status\": \"400\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid session token",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Invalid session token\"],"
                                + " \"status\": \"401\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
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
      })
  @PreAuthorize(
      "hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN) or hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_WORKER)")
  @PutMapping("/{userId}/password-change")
  public ResponseEntity<Boolean> changePassword(
      @Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest,
      @PathVariable UUID userId) {
    userService.authorizeAccessForChangePassword(userId);
    userService.changePassword(userChangePasswordRequest, userId);
    log.info("User with id {} successfully changed their password", userId);
    return ResponseEntity.ok(true);
  }

  @Operation(summary = "Enable user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User successfully enabled",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "true")})
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
            responseCode = "403",
            description = "User not verified",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"An unverified user can't be enabled/disabled.  /  Access Denied\"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "User already enabled",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User is already enabled.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            })
      })
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN)")
  @PutMapping("/{userId}/enable")
  public ResponseEntity<Boolean> enableUser(@PathVariable UUID userId) {
    userService.enableUser(userId);
    log.info("User with id {} was successfully enabled", userId);
    return ResponseEntity.ok(true);
  }

  @Operation(summary = "Disable user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User successfully disabled",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "true")})
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
            responseCode = "403",
            description = "User not verified",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"An unverified user can't be enabled/disabled.  /  Access Denied\"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "User already disabled",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User is already disabled.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            })
      })
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN)")
  @PutMapping("/{userId}/disable")
  public ResponseEntity<Boolean> disableUser(@PathVariable UUID userId) {
    userService.disableUser(userId);
    log.info("User with id {} was successfully disabled", userId);
    return ResponseEntity.ok(true);
  }

  @Operation(summary = "Promote user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User successfully promoted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "true")})
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
            responseCode = "401",
            description = "Invalid session token",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Invalid session token\"],"
                                + " \"status\": \"401\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Can't promote user that is not a worker",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Can't promote user that is not a worker\"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
      })
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN)")
  @PutMapping("/{userId}/promote")
  public ResponseEntity<Boolean> promoteUser(@PathVariable UUID userId) {
    userService.promoteUser(userId);
    log.info("User with id {} was successfully promoted to manager", userId);
    return ResponseEntity.ok(true);
  }

  @Operation(summary = "Demote user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User successfully demoted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "true")})
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
            responseCode = "401",
            description = "Invalid session token",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Invalid session token\"],"
                                + " \"status\": \"401\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Can't demote user that is not a manager",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Can't demote user that is not a manager\"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
      })
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN)")
  @PutMapping("/{userId}/demote")
  public ResponseEntity<Boolean> demoteUser(@PathVariable UUID userId) {
    userService.demoteUser(userId);
    log.info("User with id {} was successfully demoted to worker", userId);
    return ResponseEntity.ok(true);
  }

  @Operation(summary = "Get logged user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = LoggedUserResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"uuid\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + " \"username\": \"username@gmail.com\","
                                + "  \"name\": \"Name\","
                                + "  \"surname\": \"Surname\","
                                + "  \"positionInCompany\": \"Software Engineer\","
                                + "  \"company\": \"null\","
                                + "  \"roles\": [\"role_worker\"]}")
                  })
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid session token",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Invalid session token\"],"
                                + " \"status\": \"401\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            })
      })
  @GetMapping("/me")
  public ResponseEntity<LoggedUserResponse> getLoggedUser() {
    LoggedUserResponse loggedUser = currentUserService.getUserResponseForLoggedUser();
    return ResponseEntity.ok(loggedUser);
  }

  @Operation(summary = "Add user to current user's company")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully added company for user",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "true")})
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
            responseCode = "401",
            description = "Invalid session token",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Invalid session token\"],"
                                + " \"status\": \"401\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "403",
            description =
                "Admin and user company don't match, or User Already Has a Company, or Logged user is not an admin",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Admin and user company don't match \","
                                + " \"User already has a company\"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
      })
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN)")
  @PutMapping("/{userId}/company/add")
  public ResponseEntity<Boolean> addCompanyForUser(@PathVariable UUID userId) {
    userService.addCompanyForUser(userId);
    log.info("Successfully added company to user {}", userId);
    return ResponseEntity.ok(true);
  }

  @Operation(summary = "Remove user from current user's company")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully removed company for user",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "true")})
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
            responseCode = "401",
            description = "Invalid session token",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Invalid session token\"],"
                                + " \"status\": \"401\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Admin and user are not in the same company",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Admin and user are not in the same company \"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
      })
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN)")
  @PutMapping("/{userId}/company/remove")
  public ResponseEntity<Boolean> removeCompanyForUser(@PathVariable UUID userId) {
    userService.removeCompany(userId);
    log.info("Successfully removed company for user {}", userId);
    return ResponseEntity.ok(true);
  }
}
