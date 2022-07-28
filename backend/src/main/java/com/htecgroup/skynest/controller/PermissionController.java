package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.request.PermissionEditRequest;
import com.htecgroup.skynest.model.request.PermissionGrantRequest;
import com.htecgroup.skynest.model.request.PermissionRevokeRequest;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.model.response.PermissionResponse;
import com.htecgroup.skynest.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Permissions API", description = "Manage permissions for objects")
public class PermissionController {

  private final PermissionService permissionService;

  @Operation(summary = "Current user grants permission to a bucket")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully granted permission",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = PermissionResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\n"
                                + "    \"grantedToId\": \"a526981e-3a4c-4a32-ba73-ad95c7a4e26a\",\n"
                                + "    \"grantedToEmail\": \"ivan.fajgelj1@htecgroup.com\",\n"
                                + "    \"objectId\": \"e0d31981-0526-48c6-bc89-5a1abeb30096\",\n"
                                + "    \"grantedOn\": null,\n"
                                + "    \"accessName\": \"owner\",\n"
                                + "    \"grantedById\": \"67898b3b-4d5f-4a51-95e2-3808b4dfc903\",\n"
                                + "    \"grantedByEmail\": \"ivan.fajgelj@htecgroup.com\"\n"
                                + "}")
                  })
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid session token",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
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
            responseCode = "403",
            description = "Access denied",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User does not have access to bucket\"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Bucket/User not found",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        name = "bucket",
                        value =
                            "{\"messages\":[\"Bucket not found\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-03 16:18:12\"}"),
                    @ExampleObject(
                        name = "user",
                        value =
                            "{\"messages\":[\"User not found\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-03 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "User already has some kind of permission",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User already has some kind of access to requested object\"],"
                                + " \"status\": \"409\","
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
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Internal Server Error")})
            })
      })
  @PostMapping("/bucket")
  public ResponseEntity<PermissionResponse> grantPermissionForBucket(
      @Valid @RequestBody PermissionGrantRequest permissionGrantRequest) {

    ResponseEntity<PermissionResponse> response =
        new ResponseEntity<>(
            permissionService.grantPermissionForBucket(permissionGrantRequest), HttpStatus.OK);

    return response;
  }

  @Operation(summary = "Edit permission for bucket")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Permission successfully edited",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"grantedToId\": \"ff52209c-f913-11ec-b939-0242ac120002\","
                                + "\"grantedToEmail\": \"Email@com\","
                                + "\"objectId\": \"h5fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "\"grantedOn\": \"2022-07-18-04-00-15\","
                                + "\"accessName\": \"EDIT\","
                                + "{\"grantedById\": \"79362ab6-f914-11ec-b939-0242ac120002\","
                                + "\"grantedByEmail\": \"email2@com\"}")
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
      })
  @PutMapping("/bucket/{bucketId}")
  public ResponseEntity<PermissionResponse> editPermissionsForBucket(
      @Valid @RequestBody PermissionEditRequest permissionEditRequest,
      @PathVariable UUID bucketId) {
    ResponseEntity<PermissionResponse> permissionResponseEntity =
        new ResponseEntity<>(
            permissionService.editPermissionForBucket(permissionEditRequest, bucketId),
            HttpStatus.OK);
    return permissionResponseEntity;
  }

  @Operation(summary = "Modify folder permission")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Permission successfully modified for folder",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"grantedToId\": \"ff52209c-f913-11ec-b939-0242ac120002\","
                                + "\"grantedToEmail\": \"Email@com\","
                                + "\"objectId\": \"h5fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "\"grantedOn\": \"2022-07-18-04-00-15\","
                                + "\"accessName\": \"EDIT\","
                                + "{\"grantedById\": \"79362ab6-f914-11ec-b939-0242ac120002\","
                                + "\"grantedByEmail\": \"email2@com\"}")
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
            description = "Folder not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder not found\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "404",
            description = "User object access entity not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User object access entity not found\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User not found\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Access type doesn't exist",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Access type doesn't exist\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "User cant revoke their own permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User cant revoke their own permissions.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Folder already deleted.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder already deleted.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
      })
  @PutMapping("/folder/{folderId}")
  public ResponseEntity<PermissionResponse> modifyFolderPermissions(
      @Valid @RequestBody PermissionEditRequest permissionEditRequest,
      @PathVariable UUID folderId) {
    ResponseEntity<PermissionResponse> permissionResponseEntity =
        new ResponseEntity<>(
            permissionService.modifyFolderPermissions(permissionEditRequest, folderId),
            HttpStatus.OK);
    return permissionResponseEntity;
  }

  @Operation(summary = "Get all permissions for bucket")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "all permissions returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "[{\"grantedToId\": \"ff52209c-f913-11ec-b939-0242ac120002\","
                                + "\"grantedToEmail\": \"Email@com\","
                                + "\"objectId\": \"h5fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "\"grantedOn\": \"2022-07-18-04-00-15\","
                                + "\"accessName\": \"EDIT\","
                                + "{\"grantedById\": \"79362ab6-f914-11ec-b939-0242ac120002\","
                                + "\"grantedByEmail\": \"email2@com\"]")
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
  @GetMapping("/bucket/{bucketId}")
  public List<PermissionResponse> getAllPermissionsForBucket(@PathVariable UUID bucketId) {
    List<PermissionResponse> permissionList = permissionService.getAllBucketPermission(bucketId);
    return permissionList;
  }

  @Operation(summary = "Revoke permission for bucket")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Permission successfully revoked ",
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
      })
  @DeleteMapping("/bucket")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void revokePermissionForBucket(
      @Valid @RequestBody PermissionRevokeRequest permissionRevokeRequest) {
    permissionService.revokePermissionForBucket(
        permissionRevokeRequest.getObjectId(), permissionRevokeRequest.getGrantedToEmail());
  }

  @Operation(summary = "Revoke File permission")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Permission successfully revoked ",
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
      })
  @DeleteMapping("/file")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePermissionForFile(
      @Valid @RequestBody PermissionRevokeRequest permissionRevokeRequest) {
    permissionService.revokeFilePermission(
        permissionRevokeRequest.getObjectId(), permissionRevokeRequest.getGrantedToEmail());
  }

  @Operation(summary = "Get all permissions for file")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "all permissions returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "[{\"grantedToId\": \"ff52209c-f913-11ec-b939-0242ac120002\","
                                + "\"grantedToEmail\": \"Email@com\","
                                + "\"objectId\": \"h5fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "\"grantedOn\": \"2022-07-18-04-00-15\","
                                + "\"accessName\": \"EDIT\","
                                + "{\"grantedById\": \"79362ab6-f914-11ec-b939-0242ac120002\","
                                + "\"grantedByEmail\": \"email2@com\"]")
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
            description = "File not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"File not found\"],"
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
  @GetMapping("/file/{fileId}")
  public List<PermissionResponse> getAllPermissionsForFile(@PathVariable UUID fileId) {
    List<PermissionResponse> permissionList = permissionService.getAllFilePermissions(fileId);
    return permissionList;
  }

  @Operation(summary = "Get all folder permissions")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "All permissions for folder returned as list",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "[{\"grantedToId\": \"ff52209c-f913-11ec-b939-0242ac120002\","
                                + "\"grantedToEmail\": \"Email@com\","
                                + "\"objectId\": \"h5fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "\"grantedOn\": \"2022-07-18-04-00-15\","
                                + "\"accessName\": \"EDIT\","
                                + "{\"grantedById\": \"79362ab6-f914-11ec-b939-0242ac120002\","
                                + "\"grantedByEmail\": \"email2@com\"]")
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
            description = "Folder not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder not found\"],"
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
  @GetMapping("/folder/{folderId}")
  public List<PermissionResponse> getAllFolderPermissions(@PathVariable UUID folderId) {
    List<PermissionResponse> permissionList = permissionService.getAllFolderPermission(folderId);
    return permissionList;
  }

  @Operation(summary = "Current user grants permission to a file")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully granted permission",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = PermissionResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\n"
                                + "    \"grantedToId\": \"a526981e-3a4c-4a32-ba73-ad95c7a4e26a\",\n"
                                + "    \"grantedToEmail\": \"dragan.mitrasinovic@htecgroup.com\",\n"
                                + "    \"objectId\": \"e0d31981-0526-48c6-bc89-5a1abeb30096\",\n"
                                + "    \"grantedOn\": null,\n"
                                + "    \"accessName\": \"owner\",\n"
                                + "    \"grantedById\": \"67898b3b-4d5f-4a51-95e2-3808b4dfc903\",\n"
                                + "    \"grantedByEmail\": \"ivan.fajgelj@htecgroup.com\"\n"
                                + "}")
                  })
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid session token",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
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
            responseCode = "403",
            description = "Access denied",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User does not have access to file\"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "404",
            description = "File/User not found",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        name = "file",
                        value =
                            "{\"messages\":[\"File not found\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-03 16:18:12\"}"),
                    @ExampleObject(
                        name = "user",
                        value =
                            "{\"messages\":[\"User not found\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-03 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "User already has some kind of permission",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User already has some kind of access to requested object\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "File is  deleted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"File is already deleted.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Internal Server Error")})
            })
      })
  @PostMapping("/file")
  public ResponseEntity<PermissionResponse> grantPermissionToFile(
      @Valid @RequestBody PermissionGrantRequest permissionGrantRequest) {

    ResponseEntity<PermissionResponse> response =
        new ResponseEntity<>(
            permissionService.grantPermissionForFile(permissionGrantRequest), HttpStatus.OK);

    return response;
  }

  @Operation(summary = "Current user grants permission to a Folder")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully granted permission",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = PermissionResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\n"
                                + "    \"grantedToId\": \"a526981e-3a4c-4a32-ba73-ad95c7a4e26a\",\n"
                                + "    \"grantedToEmail\": \"dragan.mitrasinovic@htecgroup.com\",\n"
                                + "    \"objectId\": \"e0d31981-0526-48c6-bc89-5a1abeb30096\",\n"
                                + "    \"grantedOn\": null,\n"
                                + "    \"accessName\": \"owner\",\n"
                                + "    \"grantedById\": \"67898b3b-4d5f-4a51-95e2-3808b4dfc903\",\n"
                                + "    \"grantedByEmail\": \"ivan.fajgelj@htecgroup.com\"\n"
                                + "}")
                  })
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid session token",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
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
            responseCode = "403",
            description = "Access denied",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User does not have access to Folder\"],"
                                + " \"status\": \"403\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Folder/User not found",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        name = "folder",
                        value =
                            "{\"messages\":[\"File not found\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-03 16:18:12\"}"),
                    @ExampleObject(
                        name = "user",
                        value =
                            "{\"messages\":[\"User not found\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-03 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "User already has some kind of permission",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"User already has some kind of access to requested object\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Folder is  deleted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder is already deleted.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = String.class),
                  examples = {@ExampleObject(value = "Internal Server Error")})
            })
      })
  @PostMapping("/folder")
  public ResponseEntity<PermissionResponse> grantPermissionToFolder(
      @Valid @RequestBody PermissionGrantRequest permissionGrantRequest) {

    ResponseEntity<PermissionResponse> response =
        new ResponseEntity<>(
            permissionService.grantPermissionForFolder(permissionGrantRequest), HttpStatus.OK);

    return response;
  }

  @Operation(summary = "Edit permission for file")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Permission successfully edited",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PermissionResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"grantedToId\": \"ff52209c-f913-11ec-b939-0242ac120002\","
                                + "\"grantedToEmail\": \"Email@com\","
                                + "\"objectId\": \"h5fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "\"grantedOn\": \"2022-07-18-04-00-15\","
                                + "\"accessName\": \"EDIT\","
                                + "{\"grantedById\": \"79362ab6-f914-11ec-b939-0242ac120002\","
                                + "\"grantedByEmail\": \"email2@com\"}")
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
            description = "File not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"File not found\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
      })
  @PutMapping("/file/{fileId}")
  public ResponseEntity<PermissionResponse> editPermissionForFile(
      @Valid @RequestBody PermissionEditRequest permissionEditRequest, @PathVariable UUID fileId) {
    ResponseEntity<PermissionResponse> permissionResponseEntity =
        new ResponseEntity<>(
            permissionService.editPermissionForFile(permissionEditRequest, fileId), HttpStatus.OK);
    return permissionResponseEntity;
  }
}
