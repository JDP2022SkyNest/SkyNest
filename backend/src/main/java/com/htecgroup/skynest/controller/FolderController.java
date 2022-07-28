package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.request.FolderEditRequest;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.model.response.StorageContentResponse;
import com.htecgroup.skynest.service.FolderService;
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
import java.util.UUID;

@RestController
@RequestMapping("/folders")
@AllArgsConstructor
@Log4j2
@Tag(name = "Folder API", description = "Folder-related operations")
public class FolderController {

  private FolderService folderService;

  @Operation(summary = "Create new folder")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Folder successfully created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = FolderResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"bucketId\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "  \"name\": \"Name\","
                                + "  \"parentFolderId\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\"}")
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
                                + " \"bucketId cannot be null or empty\"],"
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
  public ResponseEntity<FolderResponse> createFolder(
      @Valid @RequestBody FolderCreateRequest folderCreateRequest) {

    ResponseEntity<FolderResponse> folderResponseEntity =
        new ResponseEntity<>(folderService.createFolder(folderCreateRequest), HttpStatus.OK);

    return folderResponseEntity;
  }

  @Operation(summary = "Get folder info")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Folder returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = FolderResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"id\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "  \"name\": \"Name\","
                                + "  \"createdById\": \"db8ca962-ccb3-44d7-98ba-0692dab47d35\","
                                + "  \"parentFolderId\": \"c58f6502-9de1-418b-9b04-dddc94f58bba\","
                                + "  \"bucketId\": \"31f66147-e491-4cdc-9e8f-b4d5c1652d23\"}")
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
  @GetMapping("/{folderId}/info")
  public ResponseEntity<FolderResponse> getFolderDetails(@PathVariable UUID folderId) {
    FolderResponse folderResponse = folderService.getFolderDetails(folderId);
    ResponseEntity<FolderResponse> folderResponseEntity =
        new ResponseEntity<>(folderResponse, HttpStatus.OK);
    return folderResponseEntity;
  }

  @Operation(summary = "Remove Folder")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Folder successfully removed",
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
            description = "Folder not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder with id a6fd6d95-0a60-43ff-961f-2b9b2ff72f95 doesn't exist\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Folder already removed",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder is already removed.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            })
      })
  @PutMapping("delete/{folderId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeFolder(@PathVariable UUID folderId) {
    folderService.removeFolder(folderId);
  }

  @Operation(summary = "Move Folder to root")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Folder successfully moved",
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
            description = "Folder not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder with id a6fd6d95-0a60-43ff-961f-2b9b2ff72f95 doesn't exist\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Folder is already inside the folder",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder is already inside the folder.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            })
      })
  @PutMapping("/{folderId}/move")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void moveFolderToRoot(@PathVariable UUID folderId) {
    folderService.moveFolderToRoot(folderId);
  }

  @Operation(summary = "Move Folder to folder")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Folder successfully moved",
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
            description = "Folder not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder with id a6fd6d95-0a60-43ff-961f-2b9b2ff72f95 doesn't exist\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "405",
            description = "Method not allowed",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder cannot be moved inside child folder\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Folder is already inside the folder",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder is already inside the folder.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            })
      })
  @PutMapping("/{folderId}/move/{destinationFolderId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void moveFolderToFolder(
      @PathVariable UUID folderId, @PathVariable UUID destinationFolderId) {
    folderService.moveFolderToFolder(folderId, destinationFolderId);
  }

  @Operation(summary = "Restore Folder")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Folder successfully removed",
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
            description = "Folder not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder with id a6fd6d95-0a60-43ff-961f-2b9b2ff72f95 doesn't exist\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Folder already restored/Folder's parent is deleted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder is already restored.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}",
                        name = "folder already restored"),
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder's parent is deleted.\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}",
                        name = "folder's parent deleted")
                  })
            })
      })
  @PutMapping("{folderId}/restore")
  public ResponseEntity<FolderResponse> restoreFolder(@PathVariable UUID folderId) {
    FolderResponse folderResponse = folderService.restoreFolder(folderId);
    ResponseEntity<FolderResponse> response = new ResponseEntity<>(folderResponse, HttpStatus.OK);
    return response;
  }

  @Operation(summary = "Get folder content")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Folder contents returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = StorageContentResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\n"
                                + "    \"bucketId\": \"e0d31981-0526-48c6-bc89-5a1abeb30096\",\n"
                                + "    \"folders\": [\n"
                                + "        {\n"
                                + "            \"id\": \"798d7d8b-dd7a-4b38-adcb-acbad8ee8e3e\",\n"
                                + "            \"createdOn\": \"2022-07-15T10:52:49\",\n"
                                + "            \"modifiedOn\": \"2022-07-15T10:52:49\",\n"
                                + "            \"deletedOn\": null,\n"
                                + "            \"name\": \"folder123\",\n"
                                + "            \"createdById\": \"67898b3b-4d5f-4a51-95e2-3808b4dfc903\",\n"
                                + "            \"parentFolderId\": \"b7e033a5-f910-425c-b7f1-11284436e1dc\",\n"
                                + "            \"bucketId\": \"e0d31981-0526-48c6-bc89-5a1abeb30096\"\n"
                                + "        }\n"
                                + "    ],\n"
                                + "    \"files\": [\n"
                                + "        {\n"
                                + "            \"id\": \"2a70cd2b-c504-42f8-8a04-c29b4fe73b4d\",\n"
                                + "            \"createdOn\": \"2022-07-08T12:43:04\",\n"
                                + "            \"modifiedOn\": \"2022-07-08T12:43:04\",\n"
                                + "            \"deletedOn\": null,\n"
                                + "            \"name\": \"init-companies.sql\",\n"
                                + "            \"createdById\": \"67898b3b-4d5f-4a51-95e2-3808b4dfc903\",\n"
                                + "            \"parentFolderId\": \"b7e033a5-f910-425c-b7f1-11284436e1dc\",\n"
                                + "            \"bucketId\": \"e0d31981-0526-48c6-bc89-5a1abeb30096\",\n"
                                + "            \"type\": \"application/x-sql\",\n"
                                + "            \"size\": \"599\"\n"
                                + "        },\n"
                                + "        {\n"
                                + "            \"id\": \"7cf100d6-f5ff-4cdc-bd66-a01431a4d7f5\",\n"
                                + "            \"createdOn\": \"2022-07-13T07:55:09\",\n"
                                + "            \"modifiedOn\": \"2022-07-13T07:55:09\",\n"
                                + "            \"deletedOn\": null,\n"
                                + "            \"name\": \"init-companies.sql\",\n"
                                + "            \"createdById\": \"67898b3b-4d5f-4a51-95e2-3808b4dfc903\",\n"
                                + "            \"parentFolderId\": \"b7e033a5-f910-425c-b7f1-11284436e1dc\",\n"
                                + "            \"bucketId\": \"e0d31981-0526-48c6-bc89-5a1abeb30096\",\n"
                                + "            \"type\": \"application/x-sql\",\n"
                                + "            \"size\": \"599\"\n"
                                + "        },\n"
                                + "        {\n"
                                + "            \"id\": \"e3370ca8-fd35-4689-a1b3-2e39eaaa6467\",\n"
                                + "            \"createdOn\": \"2022-07-14T14:32:29\",\n"
                                + "            \"modifiedOn\": \"2022-07-14T14:32:29\",\n"
                                + "            \"deletedOn\": null,\n"
                                + "            \"name\": \"bucket scripts.sql\",\n"
                                + "            \"createdById\": \"67898b3b-4d5f-4a51-95e2-3808b4dfc903\",\n"
                                + "            \"parentFolderId\": \"b7e033a5-f910-425c-b7f1-11284436e1dc\",\n"
                                + "            \"bucketId\": \"e0d31981-0526-48c6-bc89-5a1abeb30096\",\n"
                                + "            \"type\": \"application/x-sql\",\n"
                                + "            \"size\": \"1887\"\n"
                                + "        }\n"
                                + "    ],\n"
                                + "    \"path\": []\n"
                                + "}",
                        name = "root folder"),
                    @ExampleObject(
                        value =
                            "{\n"
                                + "    \"bucketId\": \"e0d31981-0526-48c6-bc89-5a1abeb30096\",\n"
                                + "    \"folders\": [\n"
                                + "        {\n"
                                + "            \"id\": \"84b4cd3b-e6d2-4b36-a10c-5eedd516e901\",\n"
                                + "            \"createdOn\": \"2022-07-15T10:53:29\",\n"
                                + "            \"modifiedOn\": \"2022-07-15T10:53:29\",\n"
                                + "            \"deletedOn\": null,\n"
                                + "            \"name\": \"folder1234\",\n"
                                + "            \"createdById\": \"67898b3b-4d5f-4a51-95e2-3808b4dfc903\",\n"
                                + "            \"parentFolderId\": \"798d7d8b-dd7a-4b38-adcb-acbad8ee8e3e\",\n"
                                + "            \"bucketId\": \"e0d31981-0526-48c6-bc89-5a1abeb30096\"\n"
                                + "        }\n"
                                + "    ],\n"
                                + "    \"files\": [],\n"
                                + "    \"path\": [\n"
                                + "        {\n"
                                + "            \"id\": \"b7e033a5-f910-425c-b7f1-11284436e1dc\",\n"
                                + "            \"name\": \"folder2\"\n"
                                + "        }\n"
                                + "    ]\n"
                                + "}",
                        name = "nested folder")
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
  @GetMapping("/{folderId}")
  public ResponseEntity<StorageContentResponse> getFolderContent(@PathVariable UUID folderId) {
    StorageContentResponse storageContentResponse = folderService.getFolderContent(folderId);
    ResponseEntity<StorageContentResponse> storageContentResponseEntity =
        new ResponseEntity<>(storageContentResponse, HttpStatus.OK);
    return storageContentResponseEntity;
  }

  @Operation(summary = "Edit folder name")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Folder successfully edited",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = FolderResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"id\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "  \"createdOn\": \"2022-07-12T13:25:24\","
                                + "  \"modifiedOn\": \"2022-07-12T13:25:24\","
                                + "  \"deletedOn\": \"null\","
                                + "  \"name\": \"Name\","
                                + "  \"createdById\": \"db8ca962-ccb3-44d7-98ba-0692dab47d35\","
                                + "  \"parentFolderId\": \"c58f6502-9de1-418b-9b04-dddc94f58bba\","
                                + "  \"bucketId\": \"31f66147-e491-4cdc-9e8f-b4d5c1652d23\"}")
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
                            "{\"messages\":[\"Folder with id ff52209c-f913-11ec-b939-0242ac120002 doesn't exist\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Folder already deleted",
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
            })
      })
  @PutMapping("/{folderId}")
  public ResponseEntity<FolderResponse> editFolder(
      @Valid @RequestBody FolderEditRequest folderEditRequest, @PathVariable UUID folderId) {
    ResponseEntity<FolderResponse> folderResponseEntity =
        new ResponseEntity<>(folderService.editFolder(folderEditRequest, folderId), HttpStatus.OK);
    return folderResponseEntity;
  }
}
