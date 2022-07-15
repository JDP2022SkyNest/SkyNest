package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.lambda.LambdaType;
import com.htecgroup.skynest.model.response.ErrorMessage;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lambdas")
@AllArgsConstructor
@Log4j2
@Tag(name = "Lambda API", description = "Lambda operations")
public class LambdaController {

  private BucketService bucketService;

  @GetMapping
  public List<LambdaType> getAllLambdas() {
    return Arrays.stream(LambdaType.values()).collect(Collectors.toList());
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
}
