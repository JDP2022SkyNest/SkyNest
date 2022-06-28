package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.response.BucketResponse;
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
                            "[{\"createdById\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "\"name\": \"Name\","
                                + "\"companyId\": \"agfad95-0g60-42ff-935f-2b9g4ff72f95\","
                                + "\"description\": \"Description\","
                                + "\"size\": \"1000\"},"
                                + "{\"createdById\": \"b6fd6d95-0b60-43ff-961f-2b9b2ff72f95\","
                                + "\"name\": \"Name2\","
                                + "\"companyId\": \"lkfad95-0p60-42ff-935f-2b9g4ff72f95\","
                                + "\"description\": \"Description2\","
                                + "\"size\": \"104124\"}]")
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
  @GetMapping
  public List<BucketResponse> getBuckets() {
    List<BucketResponse> listOfBuckets = bucketService.listAllBuckets();
    return listOfBuckets;
  }
}
