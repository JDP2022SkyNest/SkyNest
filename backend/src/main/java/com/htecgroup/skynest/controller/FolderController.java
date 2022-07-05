package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.model.response.FolderResponse;
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

import java.util.UUID;

@RestController
@RequestMapping("/folders")
@AllArgsConstructor
@Log4j2
@Tag(name = "Folder API", description = "Folder-related operations")
public class FolderController {
  private FolderService folderService;

  @Operation(summary = "Get folder with given uuid")
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
            description = "Bucket not found",
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
  @GetMapping("/{uuid}/info")
  public ResponseEntity<FolderResponse> getFolderDetails(@PathVariable UUID uuid) {
    FolderResponse folderResponse = folderService.getFolderDetails(uuid);
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
  @PutMapping("delete/{uuid}")
  public ResponseEntity<Boolean> removeFolder(@PathVariable UUID uuid) {
    folderService.removeFolder(uuid);
    return ResponseEntity.ok(true);
  }
}