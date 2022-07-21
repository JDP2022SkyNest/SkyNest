package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.request.RegistrationInviteRequest;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.service.InviteService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/invite")
@AllArgsConstructor
@Validated
@Log4j2
@Tag(name = "Invite API", description = "Invite-related operations")
public class InviteController {

  private InviteService inviteService;

  @Operation(summary = "Invite a user to register and join your company")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Registration invite successfully sent"),
        @ApiResponse(
            responseCode = "400",
            description = "Email invalid, or already in use",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"email already in use\"],"
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
            responseCode = "500",
            description = "Internal Server Error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Internal Server Error")})
            })
      })
  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN)")
  public void sendRegistrationInvite(
      @Valid @RequestBody RegistrationInviteRequest registrationInviteRequest) {
    inviteService.sendRegistrationInvite(registrationInviteRequest.getEmail());
  }
}
