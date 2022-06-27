package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.response.UserResponse;
import com.htecgroup.skynest.service.BucketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buckets")
@AllArgsConstructor
@Log4j2
@Tag(name = "User API", description = "User-related operations")
public class BucketsController {

  private BucketService bucketService;

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
  public List<BucketResponse> getBuckets() {
    List<BucketResponse> listOfBuckets = bucketService.listAllBuckets();
    return listOfBuckets;
  }
}
