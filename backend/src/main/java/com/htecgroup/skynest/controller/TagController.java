package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.request.TagCreateRequest;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.model.response.TagResponse;
import com.htecgroup.skynest.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
@Tag(name = "Tag API", description = "Tag-related operations")
public class TagController {

  private TagService tagService;

  @Operation(summary = "Create new tag")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Tag successfully created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = TagResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"id\": \"fb5722b5-19ef-4a4d-8e43-dc98237f77ac\","
                                + "\"name\": \"TagName\","
                                + "\"rgb\": \"1502DE\"}")
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
                                + " \"rgb cannot be null or empty\"],"
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
            responseCode = "409",
            description = "Tag already exists",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Tag with the same name already exists\"],"
                                + " \"status\": \"409\","
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
  public ResponseEntity<TagResponse> createTag(
      @Valid @RequestBody TagCreateRequest tagCreateRequest) {

    ResponseEntity<TagResponse> tagResponseEntity =
        new ResponseEntity<>(tagService.createTag(tagCreateRequest), HttpStatus.OK);

    return tagResponseEntity;
  }

  @Operation(summary = "Get all tags")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Tags returned",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = TagResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "[{\"id\": \"ff52209c-f913-11ec-b939-0242ac120002\","
                                + "\"name\": \"TagName\","
                                + "\"rgb\": \"1502DE\"},"
                                + "{\"id\": \"79362ab6-f914-11ec-b939-0242ac120002\","
                                + "\"name\": \"TagName2\","
                                + "\"rgb\": \"510FFF\"}]")
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
  public List<TagResponse> getAllTags() {
    List<TagResponse> tags = tagService.listAllTags();
    return tags;
  }

  @PutMapping("/{tagId}/object/{objectId}")
  public void tagObject(@PathVariable UUID tagId, @PathVariable UUID objectId) {}
}
