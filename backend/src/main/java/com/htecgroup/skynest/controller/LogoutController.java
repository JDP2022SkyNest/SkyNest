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
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/logout")
@AllArgsConstructor
@Tag(name = "Logout API", description = "Operations related to logout of users")
@Log4j2
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
      })
  @PostMapping
  public ResponseEntity<String> logout(
      @RequestHeader(name = "Authorization") String authorizationHeader) {
    String token = authorizationHeader.replace(JwtUtils.TOKEN_PREFIX, "");
    invalidJwtService.invalidate(token);
    return ResponseEntity.ok("User logged out successfully");
  }
}
