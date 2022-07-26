package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.service.DropboxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/public")
public class DropboxController {
  private DropboxService dropboxService;

  @Operation(summary = "Start dropbox authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Returns the URL as a String in the response entity to which the frontend app needs to redirect the user.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "https://www.dropbox.com/oauth2/authorize?response_type=code&client_id=7y0fr9h4lw3lsgz")
                  })
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized request",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Access denied\"],"
                                + " \"status\": \"401\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
      })
  @GetMapping("/dropbox-auth-start")
  public ResponseEntity<String> startAuthorizeUserDropbox() {
    // The redirect_uri is optional with the code flow - if unspecified, the authorization code is
    // displayed on dropbox.com for the user to copy and paste to your app.
    // This extra step is less convenient for end users, but appropriate for apps that cannot
    // support a redirect.
    String authorizePageUrl = dropboxService.startAuthorizeUserDropbox();
    return ResponseEntity.ok(authorizePageUrl);
  }

  @Operation(summary = "Finish dropbox authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description =
                "Finishes the authorization for the user and saves the dropbox access token"),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized request",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Access denied\"],"
                                + " \"status\": \"401\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Dropbox authorization failed",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"There was an issue with the dropbox code\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
      })
  @GetMapping("/dropbox-auth-finish")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void finishAuthorizeUserDropboxFinish(@RequestParam String code) {
    dropboxService.finishAuthorizeUserDropbox(code);
  }
}
