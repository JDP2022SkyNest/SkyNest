package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.lambda.LambdaType;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.service.BucketService;
import com.htecgroup.skynest.service.CurrentUserService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lambdas")
@AllArgsConstructor
@Log4j2
@Tag(name = "Lambda API", description = "Lambda operations")
public class LambdaController {

  private BucketService bucketService;

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
                  schema = @Schema(implementation = Map.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\n"
                                + "    \"S_B_S_T_E_L\": \"Send bucket stats to e-mail\",\n"
                                + "    \"U_F_T_E_S_L\": \"Upload file to dropbox lambda\"\n"
                                + "}")
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
  public Map<String, String> getAllLambdas() {
    return Arrays.stream(LambdaType.values())
        .collect(Collectors.toMap(LambdaType::getDatabaseCode, LambdaType::getName));
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
  public void deactivateLambdaForBucket(@PathVariable UUID bucketId, @RequestParam String code) {
    LambdaType lambda = LambdaType.getLambda(code);
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
                  schema = @Schema(implementation = Map.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\n"
                                + "    \"S_B_S_T_E_L\": \"Send bucket stats to e-mail\",\n"
                                + "    \"U_F_T_E_S_L\": \"Upload file to dropbox lambda\"\n"
                                + "}")
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
  public Map<String, String> getActiveLambdasForBucket(@PathVariable UUID bucketId) {
    List<LambdaType> activeLambdas = bucketService.getActiveLambdas(bucketId);
    log.info("Successfully got {} active lambdas for bucket {}", activeLambdas, bucketId);
    return activeLambdas.stream()
        .collect(Collectors.toMap(LambdaType::getDatabaseCode, LambdaType::getName));
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
  public void activateLambdaForBucket(@PathVariable UUID bucketId, @RequestParam String code) {
    LambdaType lambda = LambdaType.getLambda(code);
    bucketService.activateLambda(bucketId, lambda);
    log.info(
        "Activated lambda {} for bucket {} by owner with id {}",
        lambda.toString(),
        bucketId.toString(),
        currentUserService.getLoggedUser().getUuid());
  }
}
