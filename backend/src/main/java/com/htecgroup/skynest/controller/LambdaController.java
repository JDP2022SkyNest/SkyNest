package com.htecgroup.skynest.controller;

import com.dropbox.core.*;
import com.htecgroup.skynest.lambda.LambdaType;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.service.BucketService;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.DropboxUtil;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
@Log4j2
@Tag(name = "Lambda API", description = "Lambda operations")
public class LambdaController {

  private BucketService bucketService;
  private UserService userService;

  private CurrentUserService currentUserService;

  @Operation(summary = "Get all lambdas")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "All lambdas returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = LambdaType.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "[\"UPLOAD_FILE_TO_EXTERNAL_SERVICE_LAMBDA\","
                                + "\"SOME_OTHER_LAMBDA\"]")
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
  @GetMapping
  public List<LambdaType> getAllLambdas() {
    return Arrays.stream(LambdaType.values()).collect(Collectors.toList());
  }

  @Operation(summary = "Deactivate lambda for bucket")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Lambda successfully deactivated for given bucket"),
        @ApiResponse(
            responseCode = "404",
            description = "Bucket not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Bucket not found\"],"
                                + " \"status\": \"401\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
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
  @PutMapping("/bucket/{bucketId}/deactivate")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deactivateLambdaForBucket(
      @PathVariable UUID bucketId, @RequestParam LambdaType lambda) {
    bucketService.deactivateLambda(bucketId, lambda);
    log.info(
        "Deactivated lambda {} for bucket {} by owner with id {}",
        lambda.toString(),
        bucketId.toString(),
        currentUserService.getLoggedUser().getUuid());
  }

  @Operation(summary = "Get all active lambdas for bucket")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "All active lambdas returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = LambdaType.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "[\"UPLOAD_FILE_TO_EXTERNAL_SERVICE_LAMBDA\","
                                + "\"SOME_OTHER_LAMBDA\"]")
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
  @GetMapping("/active/bucket/{bucketId}")
  public List<LambdaType> getActiveLambdasForBucket(@PathVariable UUID bucketId) {
    List<LambdaType> activeLambdas = bucketService.getActiveLambdas(bucketId);
    log.info("Successfully got {} active lambdas for bucket {}", activeLambdas, bucketId);
    return activeLambdas;
  }

  @Operation(summary = "Activate lambda for bucket")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Lambda successfully activated for given bucket"),
        @ApiResponse(
            responseCode = "404",
            description = "Bucket not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Bucket not found\"],"
                                + " \"status\": \"401\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
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
  @PutMapping("/bucket/{bucketId}/activate")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void activateLambdaForBucket(
      @PathVariable UUID bucketId, @RequestParam LambdaType lambda) {
    bucketService.activateLambda(bucketId, lambda);
    log.info(
        "Activated lambda {} for bucket {} by owner with id {}",
        lambda.toString(),
        bucketId.toString(),
        currentUserService.getLoggedUser().getUuid());
  }

  @GetMapping("/dropbox-auth-start")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void authorizeUserDropbox(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    // Select a spot in the session for DbxWebAuth to store the CSRF token.
    HttpSession session = request.getSession(true);
    String sessionKey = "dropbox-auth-csrf-token";
    DbxSessionStore csrfTokenStore = new DbxStandardSessionStore(session, sessionKey);
    // Build an auth request
    DbxWebAuth.Request authRequest =
        DbxWebAuth.newRequestBuilder()
            .withRedirectUri(DropboxUtil.getRedirectUrl(), csrfTokenStore)
            .build();
    // Start authorization.
    String authorizePageUrl = DropboxUtil.getAuth().authorize(authRequest);
    // Redirect the user to the Dropbox website so they can approve our application.
    // The Dropbox website will send them back to "http://my-server.com/dropbox-auth-finish"
    // when they're done.
    response.sendRedirect(authorizePageUrl);
  }

  @GetMapping("/dropbox-auth-finish")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void authorizeUserDropboxFinish(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    // Fetch the session to verify our CSRF token
    HttpSession session = request.getSession(true);
    String sessionKey = "dropbox-auth-csrf-token";
    DbxSessionStore csrfTokenStore = new DbxStandardSessionStore(session, sessionKey);
    String redirectUri = DropboxUtil.getRedirectUrl();
    DbxAuthFinish authFinish;
    try {
      authFinish =
          DropboxUtil.getAuth()
              .finishFromRedirect(redirectUri, csrfTokenStore, request.getParameterMap());
    } catch (DbxWebAuth.BadRequestException ex) {
      log.info("On /dropbox-auth-finish: Bad request: {}", ex.getMessage());
      response.sendError(400);
      return;
    } catch (DbxWebAuth.BadStateException ex) {
      // Send them back to the start of the auth flow.
      response.sendRedirect("http://localhost:8080/dropbox-auth-start");
      return;
    } catch (DbxWebAuth.CsrfException ex) {
      log.info("On /dropbox-auth-finish: CSRF mismatch: " + ex.getMessage());
      response.sendError(403, "Forbidden.");
      return;
    } catch (DbxWebAuth.NotApprovedException ex) {
      // When Dropbox asked "Do you want to allow this app to access your
      // Dropbox account?", the user clicked "No".
      return;
    } catch (DbxWebAuth.ProviderException ex) {
      log.info("On /dropbox-auth-finish: Auth failed: " + ex.getMessage());
      response.sendError(503, "Error communicating with Dropbox.");
      return;
    } catch (DbxException ex) {
      log.info("On /dropbox-auth-finish: Error getting token: " + ex.getMessage());
      response.sendError(503, "Error communicating with Dropbox.");
      return;
    }
    String accessToken = authFinish.getAccessToken();
    log.info(
        "Successfully got dropbox access token for user {}",
        currentUserService.getLoggedUser().getUuid());
    // Save the access token somewhere (probably in your database) so you
    // don't need to send the user through the authorization process again.
    userService.saveDropboxAccessToken(accessToken);
  }
}
