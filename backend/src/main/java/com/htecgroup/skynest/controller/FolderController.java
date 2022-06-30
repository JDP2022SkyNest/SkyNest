package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.request.FolderCreateRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
                            "{\"createdById\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
                                + "  \"name\": \"Name\","
                                + "  \"companyId\": \"a6fd6d95-0a60-43ff-961f-2b9b2ff72f95\","
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
  public ResponseEntity<FolderResponse> createFolder(
      @Valid @RequestBody FolderCreateRequest folderCreateRequest) {

    ResponseEntity<FolderResponse> folderResponseEntity =
        new ResponseEntity<>(folderService.createFolder(folderCreateRequest), HttpStatus.OK);

    log.info("Folder {} was successfully created", folderCreateRequest.getName());
    return folderResponseEntity;
  }
}
