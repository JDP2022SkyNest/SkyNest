package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.request.FileInfoEditRequest;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.model.response.FileDownloadResponse;
import com.htecgroup.skynest.model.response.FileResponse;
import com.htecgroup.skynest.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
@Log4j2
@Tag(name = "File API", description = "File operations")
public class FileController {

  private final FileService fileService;

  @Operation(summary = "Upload file to a bucket")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully uploaded a new file",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = FileResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"id\": \"1d132ffe-51c0-43fb-aaed-290d4501b8dd\",\n"
                                + "    \"createdOn\": null,\n"
                                + "    \"modifiedOn\": null,\n"
                                + "    \"deletedOn\": null,\n"
                                + "    \"name\": \"change user role.sql\",\n"
                                + "    \"createdById\": \"67898b3b-4d5f-4a51-95e2-3808b4dfc903\",\n"
                                + "    \"parentFolderId\": null,\n"
                                + "    \"bucketId\": \"1ebdec68-f6d7-11ec-8822-0242ac160002\",\n"
                                + "    \"type\": \"application/x-sql\",\n"
                                + "    \"size\": \"499\"\n"
                                + "}")
                  })
            }),
        @ApiResponse(
            responseCode = "400",
            description = "File IO error/Failed to write file contents",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Failed to write file contents\"],"
                                + " \"status\": \"400\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
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
            description = "Bucket already deleted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Bucket already deleted\"],"
                                + " \"status\": \"409\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
                  })
            }),
        @ApiResponse(
            responseCode = "419",
            description = "Company/User buckets are full",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Company/User buckets don't have enough space\"],"
                                + " \"status\": \"419\","
                                + " \"timestamp\": \"2022-06-03 16:18:12\"}")
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
  @PostMapping(path = "/bucket/{bucketId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<FileResponse> uploadFileToBucket(
      @PathVariable UUID bucketId, @RequestPart("file") MultipartFile file) {

    FileResponse fileResponse = fileService.uploadFile(file, bucketId);

    ResponseEntity<FileResponse> responseEntity = new ResponseEntity<>(fileResponse, HttpStatus.OK);
    return responseEntity;
  }

  @Operation(summary = "Download file")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully downloaded file",
            headers = {
              @Header(
                  name = HttpHeaders.CONTENT_DISPOSITION,
                  description =
                      "contains file name, for example: attachment; filename=\"some file.txt\""),
              @Header(
                  name = HttpHeaders.CONTENT_TYPE,
                  description = "contains file type, for example: text/plain")
            }),
        @ApiResponse(
            responseCode = "400",
            description = "File IO error/Failed to write file contents",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Failed to write file contents\"],"
                                + " \"status\": \"400\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}")
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
            description = "File not found",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"File not found\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-03 16:18:12\"}")
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
  @GetMapping(path = "/{fileId}")
  public ResponseEntity<Resource> downloadFile(@PathVariable UUID fileId) {

    FileDownloadResponse fileDownloadResponse = fileService.downloadFile(fileId);

    ResponseEntity<Resource> responseEntity =
        ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(fileDownloadResponse.getType()))
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileDownloadResponse.getName() + "\"")
            .body(fileDownloadResponse.getFileContent());

    return responseEntity;
  }

  @Operation(summary = "Get file details")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully fetched file info",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = FileResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"id\": \"1d132ffe-51c0-43fb-aaed-290d4501b8dd\",\n"
                                + "    \"createdOn\": \"2022-06-03 16:18:12\",\n"
                                + "    \"modifiedOn\": \"2022-06-03 16:18:12\",\n"
                                + "    \"deletedOn\": null,\n"
                                + "    \"name\": \"change user role.sql\",\n"
                                + "    \"createdById\": \"67898b3b-4d5f-4a51-95e2-3808b4dfc903\",\n"
                                + "    \"parentFolderId\": null,\n"
                                + "    \"bucketId\": \"1ebdec68-f6d7-11ec-8822-0242ac160002\",\n"
                                + "    \"type\": \"application/x-sql\",\n"
                                + "    \"size\": \"499\"\n"
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
            description = "File not found",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"File not found\"],"
                                + " \"status\": \"404\","
                                + " \"timestamp\": \"2022-06-03 16:18:12\"}"),
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
  @GetMapping("/{fileId}/info")
  public ResponseEntity<FileResponse> getFileDetails(@PathVariable UUID fileId) {

    FileResponse fileResponse = fileService.getFileMetadata(fileId);
    ResponseEntity<FileResponse> responseEntity = new ResponseEntity<>(fileResponse, HttpStatus.OK);

    return responseEntity;
  }

  @Operation(summary = "Edit file info")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "file info successfully edited",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = FileResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"id\": \"1d132ffe-51c0-43fb-aaed-290d4501b8dd\",\n"
                                + "    \"createdOn\": \"2022-06-03 16:18:12\",\n"
                                + "    \"modifiedOn\": \"2022-06-03 16:18:12\",\n"
                                + "    \"deletedOn\": null,\n"
                                + "    \"name\": \"change user role.sql\",\n"
                                + "    \"createdById\": \"67898b3b-4d5f-4a51-95e2-3808b4dfc903\",\n"
                                + "    \"parentFolderId\": null,\n"
                                + "    \"bucketId\": \"1ebdec68-f6d7-11ec-8822-0242ac160002\",\n"
                                + "    \"type\": \"application/x-sql\",\n"
                                + "    \"size\": \"499\"\n"
                                + "}")
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
            description = "File not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"file with id ff52209c-f913-11ec-b939-0242ac120002 doesn't exist\"],"
                                + " \"status\": \"404\","
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
  @PutMapping("/{fileId}")
  public ResponseEntity<FileResponse> editFileInfo(
      @Valid @RequestBody FileInfoEditRequest fileInfoEditRequest, @PathVariable UUID fileId) {
    ResponseEntity<FileResponse> fileResponseEntity =
        new ResponseEntity<>(fileService.editFileInfo(fileInfoEditRequest, fileId), HttpStatus.OK);
    return fileResponseEntity;
  }
}
