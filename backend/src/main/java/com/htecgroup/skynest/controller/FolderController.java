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

    log.info("Folder {} was successfully created", folderCreateRequest.getName());
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

  @Operation(summary = "Move Folder")
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
    folderService.moveFolderToBucket(folderId);
  }

  @Operation(summary = "Move Folder")
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
  @PutMapping("/{folderId}/move/{destinationFolderId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void moveFolderToFolder(
      @PathVariable UUID folderId, @PathVariable UUID destinationFolderId) {
    folderService.moveFolderToFolder(folderId, destinationFolderId);
  }

  @Operation(summary = "Get folder contents")
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
                            "{\"folders\":["
                                + "{\"id\": \"0b3f68e4-fc6e-11ec-8ee4-0242ac120003\","
                                + "  \"createdOn\": \"2022-07-05T14:23:30\","
                                + "  \"modifiedOn\": \"2022-07-05T14:23:30\","
                                + "  \"deletedOn\": null,"
                                + "  \"name\": \"folder1\","
                                + "  \"createdById\": \"55ff7452-5513-47f3-be82-59c34cb80140\","
                                + "  \"parentFolderId\": \"fe7b8a58-f6b4-4e7a-89aa-70a18e7469dc\","
                                + "  \"bucketId\": \"0b091ce5-8b42-4dfe-878a-7cb7382ebae6\"}],"
                                + "\"files\":["
                                + " {\"id\": \"6759ea31-ae80-4224-a4e2-9e24fdfeebcb\","
                                + "   \"createdOn\": \"2022-07-05T12:44:28\","
                                + "   \"modifiedOn\": \"2022-07-05T12:44:28\","
                                + "   \"deletedOn\": null,"
                                + "   \"name\": \"MicrosoftTeams-image.png\","
                                + "   \"createdById\": \"55ff7452-5513-47f3-be82-59c34cb80140\","
                                + "   \"parentFolderId\": \"fe7b8a58-f6b4-4e7a-89aa-70a18e7469dc\","
                                + "   \"bucketId\": \"0b091ce5-8b42-4dfe-878a-7cb7382ebae6\","
                                + "   \"type\": \"image/png\","
                                + "   \"size\": \"82786\"},"
                                + " {\"id\": \"be318599-df9b-42a9-95eb-6ec3e74a7a07\","
                                + "   \"createdOn\": \"2022-07-05T12:32:24\","
                                + "   \"modifiedOn\": \"2022-07-05T12:32:24\","
                                + "   \"deletedOn\": null,"
                                + "   \"name\": \"SkyNest Sprint 3.pptx\","
                                + "   \"createdById\": \"55ff7452-5513-47f3-be82-59c34cb80140\","
                                + "   \"parentFolderId\": \"fe7b8a58-f6b4-4e7a-89aa-70a18e7469dc\","
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
