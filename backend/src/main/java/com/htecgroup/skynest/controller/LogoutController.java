package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.service.InvalidJwtService;
import com.htecgroup.skynest.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users/logout")
@AllArgsConstructor
@Tag(name = "Logout API", description = "Operations related to logout of users")
public class LogoutController {

  private InvalidJwtService invalidJwtService;

  @Operation(summary = "Logout user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Logout successful",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Logout successful")})
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Logout - provided token failed validation, algorithm is null",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "JWT algorithm is null")})
            })
      })
  @PostMapping
  public ResponseEntity<String> logout(HttpServletRequest request) {
    String authorizationHeader = request.getHeader(JwtUtils.AUTH_HEADER);
    String token = authorizationHeader.replace(JwtUtils.TOKEN_PREFIX, "");
    invalidJwtService.invalidate(token);
    return ResponseEntity.ok("Logout successful");
  }
}
