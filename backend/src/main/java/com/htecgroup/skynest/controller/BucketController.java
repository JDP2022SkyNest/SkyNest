package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.request.BucketCreateRequest;
import com.htecgroup.skynest.model.request.BucketEditRequest;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.model.response.StorageContentResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/buckets")
@AllArgsConstructor
@Log4j2
@Tag(name = "Bucket API", description = "Bucket-related operations")
public class BucketController {

  private BucketService bucketService;

  @Operation(summary = "Create new bucket")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Bucket successfully created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BucketResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"bucketId\": \"ff52209c-f913-11ec-b939-0242ac120002\","
                                + "\"createdById\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "  \"name\": \"Name\","
                                + "  \"companyId\": \"h5fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "  \"description\": \"Description\","
                                + "  \"size\": \"1000\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"name cannot be null or empty\","
                                + " \"description cannot be null or empty\"],"
                                + " \"status\": \"400\","
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
  public ResponseEntity<BucketResponse> createBucket(
      @Valid @RequestBody BucketCreateRequest bucketCreateRequest) {

    ResponseEntity<BucketResponse> bucketResponseEntity =
        new ResponseEntity<>(bucketService.createBucket(bucketCreateRequest), HttpStatus.OK);

    return bucketResponseEntity;
  }

  @Operation(summary = "Get bucket details")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Bucket returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BucketResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"bucketId\": \"ff52209c-f913-11ec-b939-0242ac120002\","
                                + "\"createdById\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "  \"name\": \"Name\","
                                + "  \"companyId\": \"h5fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "  \"description\": \"Description\","
                                + "  \"size\": \"1000\"}")
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
                                + " \"status\": \"404\","
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
  @GetMapping("/{bucketId}/info")
  public ResponseEntity<BucketResponse> getBucket(@PathVariable UUID bucketId) {
    BucketResponse bucketResponse = bucketService.getBucketDetails(bucketId);
    ResponseEntity<BucketResponse> bucketResponseEntity =
        new ResponseEntity<>(bucketResponse, HttpStatus.OK);
    return bucketResponseEntity;
  }

  @Operation(summary = "Get all buckets")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Buckets returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BucketResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "[{\"bucketId\": \"ff52209c-f913-11ec-b939-0242ac120002\","
                                + "\"id\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "\"name\": \"Name\","
                                + "\"companyId\": \"h5fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "\"description\": \"Description\","
                                + "\"size\": \"1000\"},"
                                + "{\"bucketId\": \"79362ab6-f914-11ec-b939-0242ac120002\","
                                + "\"id\": \"u7yd987h-0a79-42dd-961s-7sfh564kdv2s\","
                                + "\"name\": \"Name2\","
                                + "\"companyId\": \"b2d6b109-624c-4509-8544-5ad8d3a2a88f\","
                                + "\"description\": \"Description2\","
                                + "\"size\": \"1200\"}]")
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

  @Operation(summary = "Delete bucket")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Bucket successfully deleted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "true")})
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
                            "{\"messages\":[\"Bucket with id a6fd6d95-0a60-43ff-961f-2b9b2ff72f95 doesn't exist\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Bucket already deleted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Bucket is already disabled.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            })
      })
  @PutMapping("/{bucketId}/delete")
  public ResponseEntity<Boolean> deleteBucket(@PathVariable UUID bucketId) {
    bucketService.deleteBucket(bucketId);
    return ResponseEntity.ok(true);
  }

  @Operation(summary = "Restore bucket")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Bucket successfully restored",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "true")})
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
                            "{\"messages\":[\"Bucket with id a6fd6d95-0a60-43ff-961f-2b9b2ff72f95 doesn't exist\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Bucket already restored",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Bucket is already restored.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            })
      })
  @PutMapping("/{bucketId}/restore")
  public ResponseEntity<Boolean> restoreBucket(@PathVariable UUID bucketId) {
    bucketService.restoreBucket(bucketId);
    return ResponseEntity.ok(true);
  }

  @Operation(summary = "Edit bucket")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Bucket successfully edited",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BucketResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"bucketId\": \"ff52209c-f913-11ec-b939-0242ac120002\","
                                + "\"createdById\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "  \"name\": \"Name\","
                                + "  \"companyId\": \"h5fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "  \"description\": \"Description\","
                                + "  \"size\": \"1000\"}")
                  }),
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
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Bucket already deleted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Bucket is already deleted.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            })
      })
  @PutMapping("/{bucketId}")
  public ResponseEntity<BucketResponse> editBucket(
      @Valid @RequestBody BucketEditRequest bucketEditRequest, @PathVariable UUID bucketId) {
    ResponseEntity<BucketResponse> bucketResponseEntity =
        new ResponseEntity<>(bucketService.editBucket(bucketEditRequest, bucketId), HttpStatus.OK);
    return bucketResponseEntity;
  }

  @Operation(summary = "Get bucket contents")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Bucket contents returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = StorageContentResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"folders\":["
                                + "{\"id\": \"0b3f68e4-fc6e-11ec-8ee4-0242ac120003\","
                                + "  \"createdOn\": \"2022-07-05T14:23:30\","
                                + "  \"modifiedOn\": \"2022-07-05T14:23:30\","
                                + "  \"deletedOn\": null,"
                                + "  \"name\": \"folder2\","
                                + "  \"createdById\": \"55ff7452-5513-47f3-be82-59c34cb80140\","
                                + "  \"parentFolderId\": null,"
                                + "  \"bucketId\": \"0b091ce5-8b42-4dfe-878a-7cb7382ebae6\"}],"
                                + "\"files\":["
                                + " {\"id\": \"6759ea31-ae80-4224-a4e2-9e24fdfeebcb\","
                                + "   \"createdOn\": \"2022-07-05T12:44:28\","
                                + "   \"modifiedOn\": \"2022-07-05T12:44:28\","
                                + "   \"deletedOn\": null,"
                                + "   \"name\": \"MicrosoftTeams-image (6).png\","
                                + "   \"createdById\": \"55ff7452-5513-47f3-be82-59c34cb80140\","
                                + "   \"parentFolderId\": null,"
                                + "   \"bucketId\": \"0b091ce5-8b42-4dfe-878a-7cb7382ebae6\","
                                + "   \"type\": \"image/png\","
                                + "   \"size\": \"82786\"},"
                                + " {\"id\": \"be318599-df9b-42a9-95eb-6ec3e74a7a07\","
                                + "   \"createdOn\": \"2022-07-05T12:32:24\","
                                + "   \"modifiedOn\": \"2022-07-05T12:32:24\","
                                + "   \"deletedOn\": null,"
                                + "   \"name\": \"SkyNest Sprint 2 (1).pptx\","
                                + "   \"createdById\": \"55ff7452-5513-47f3-be82-59c34cb80140\","
                                + "   \"parentFolderId\": null,"
                                + "   \"bucketId\": \"0b091ce5-8b42-4dfe-878a-7cb7382ebae6\","
                                + "   \"type\": \"application/vnd.openxmlformats-officedocument.presentationml.presentation\","
                                + "   \"size\": \"1946683\"}]}")
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
                                + " \"status\": \"404\","
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
  @GetMapping("/{bucketId}")
  public ResponseEntity<StorageContentResponse> getBucketContent(@PathVariable UUID bucketId) {
    StorageContentResponse storageContentResponse = bucketService.getBucketContent(bucketId);
    ResponseEntity<StorageContentResponse> storageContentResponseEntity =
        new ResponseEntity<>(storageContentResponse, HttpStatus.OK);
    return storageContentResponseEntity;
  }
}
