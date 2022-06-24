package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.service.RefreshTokenService;
import com.htecgroup.skynest.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/token/refresh")
@AllArgsConstructor
@Tag(name = "Token Refresh API", description = "Operations related to refresh of the token")
@Log4j2
public class RefreshController {

  private RefreshTokenService refreshTokenService;

  @Operation(summary = "Request for token refresh")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "new access token successfully sent",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {
                    @ExampleObject(value = "New Access Token successfully created and sent")
                  })
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
                  examples = {
                    @ExampleObject(value = "Failed to send new access and refresh tokens")
                  })
            })
      })
  @GetMapping()
  public void refreshToken(
      @RequestHeader(JwtUtils.REFRESH_TOKEN_HEADER) String refresh_token,
      HttpServletResponse response) {
    String token = refreshTokenService.refreshToken(refresh_token);
    response.addHeader(JwtUtils.AUTH_HEADER, String.format("%s%s", JwtUtils.TOKEN_PREFIX, token));
    response.addHeader(JwtUtils.REFRESH_TOKEN_HEADER, refresh_token);
  }
}
