package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.request.UserChangePasswordRequest;
import com.htecgroup.skynest.model.request.UserEditRequest;
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

import static com.htecgroup.skynest.util.UrlUtil.USERS_CONTROLLER_URL;

@RestController
@RequestMapping(USERS_CONTROLLER_URL)
@AllArgsConstructor
@Log4j2
@Tag(name = "User API", description = "User-related operations")
public class UserController {

  private UserService userService;

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
                                + "\"enabled\": \"true\","
                                + "\"verified\": \"true\"},"
                                + "{\"id\": \"u7yd987h-0a79-42dd-961s-7sfh564kdv2s\","
                                + "\"email\": \"username123@gmail.com\","
                                + "\"name\": \"Name\","
                                + "\"surname\": \"Surname\","
                                + "\"phoneNumber\": \"38166676767\","
                                + "\"address\": \"Local address\","
                                + "\"roleName\": \"role_worker\","
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

  @Operation(summary = "Get user with given uuid")
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
  @GetMapping("/{uuid}")
  public ResponseEntity<UserResponse> getUser(@PathVariable UUID uuid) {
    UserResponse userResponse = userService.getUser(uuid);
    ResponseEntity<UserResponse> userResponseEntity =
        new ResponseEntity<>(userResponse, HttpStatus.OK);
    return userResponseEntity;
  }

  @Operation(summary = "Edit user with given uuid")
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
  @PutMapping("/{uuid}")
  public ResponseEntity<UserResponse> editUser(
      @Valid @RequestBody UserEditRequest userEditRequest, @PathVariable UUID uuid) {
    ResponseEntity<UserResponse> responseEntity =
        new ResponseEntity<>(userService.editUser(uuid, userEditRequest), HttpStatus.OK);
    log.info("User is successfully edited.");
    return responseEntity;
  }

  @Operation(summary = "Delete user with given uuid")
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
  @DeleteMapping("/{uuid}")
  public ResponseEntity<String> deleteUser(@PathVariable UUID uuid) {
    userService.deleteUser(uuid);
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
  @PutMapping("/password-change/{uuid}")
  public ResponseEntity<Boolean> changePassword(
      @Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest,
      @PathVariable UUID uuid) {
    userService.authorizeAccessForChangePassword(uuid);
    userService.changePassword(userChangePasswordRequest, uuid);
    log.info("User with id {} successfully changed their password", uuid);
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
                            "{\"messages\":[\"An unverified user can't be enabled/disabled.\"],"
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
  @PutMapping("/{uuid}/enable")
  public ResponseEntity<Boolean> enableUser(@PathVariable UUID uuid) {
    userService.enableUser(uuid);
    log.info("User with id {} was successfully enabled", uuid);
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
                            "{\"messages\":[\"Not verified user can't be enabled/disabled.\"],"
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
  @PutMapping("/{uuid}/disable")
  public ResponseEntity<Boolean> disableUser(@PathVariable UUID uuid) {
    userService.disableUser(uuid);
    log.info("User with id {} was successfully disabled", uuid);
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
            description = "Can't promote user that is not a worker.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Can't promote user that is not a worker.\"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
      })
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN)")
  @PutMapping("/{uuid}/promote")
  public ResponseEntity<Boolean> promoteUser(@PathVariable UUID uuid) {
    userService.promoteUser(uuid);
    log.info("User with id {} was successfully promoted to manager", uuid);
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
            description = "Can't demote user that is not a manager.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Can't demote user that is not a manager.\"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
      })
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN)")
  @PutMapping("/{uuid}/demote")
  public ResponseEntity<Boolean> demoteUser(@PathVariable UUID uuid) {
    userService.demoteUser(uuid);
    log.info("User with id {} was successfully demoted to worker", uuid);
    return ResponseEntity.ok(true);
  }
}
