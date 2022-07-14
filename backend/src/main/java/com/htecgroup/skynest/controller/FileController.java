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

    FileResponse fileResponse = fileService.uploadFileToBucket(file, bucketId);

    ResponseEntity<FileResponse> responseEntity = new ResponseEntity<>(fileResponse, HttpStatus.OK);
    return responseEntity;
  }

  @Operation(summary = "Upload file to a folder")
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
            description = "Bucket/Folder/User not found",
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
                        name = "folder",
                        value =
                            "{\"messages\":[\"Folder not found\"],"
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
            description = "Folder already deleted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Folder already deleted\"],"
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
  @PostMapping(path = "/folder/{folderId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<FileResponse> uploadFileToFolder(
      @PathVariable UUID folderId, @RequestPart("file") MultipartFile file) {

    FileResponse fileResponse = fileService.uploadFileToFolder(file, folderId);

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

  @Operation(summary = "Update file content")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully uploaded new file content",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = FileResponse.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\n"
                                + "    \"id\": \"948d0e41-0629-40c4-a589-b8d02de1e7c0\",\n"
                                + "    \"createdOn\": \"2022-07-08T12:38:55\",\n"
                                + "    \"modifiedOn\": \"2022-07-13T14:24:49\",\n"
                                + "    \"deletedOn\": null,\n"
                                + "    \"name\": \"list-users.sql\",\n"
                                + "    \"createdById\": \"67898b3b-4d5f-4a51-95e2-3808b4dfc903\",\n"
                                + "    \"parentFolderId\": null,\n"
                                + "    \"bucketId\": \"e0d31981-0526-48c6-bc89-5a1abeb30096\",\n"
                                + "    \"type\": \"application/x-sql\",\n"
                                + "    \"size\": \"268\"\n"
                                + "}")
                  })
            }),
        @ApiResponse(
            responseCode = "400",
            description = "File IO error/Failed to write file content",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Failed to write file contents\"],"
                                + " \"status\": \"400\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}"),
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"Cannot change file type, please upload content as a new file.\"],"
                                + " \"status\": \"400\","
                                + " \"timestamp\": \"2022-06-07 16:18:12\"}",
                        name = "different file type")
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
  @PutMapping(path = "/{fileId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<FileResponse> updateFileContent(
      @PathVariable UUID fileId, @RequestPart("file") MultipartFile file) {

    FileResponse fileResponse = fileService.updateFileContent(file, fileId);

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
  @PutMapping("/{fileId}/info")
  public ResponseEntity<FileResponse> editFileInfo(
      @Valid @RequestBody FileInfoEditRequest fileInfoEditRequest, @PathVariable UUID fileId) {
    ResponseEntity<FileResponse> fileResponseEntity =
        new ResponseEntity<>(fileService.editFileInfo(fileInfoEditRequest, fileId), HttpStatus.OK);
    return fileResponseEntity;
  }

  @Operation(summary = "Delete a file")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Successfully deleted a file",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Boolean.class),
                  examples = {@ExampleObject(value = "true")})
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
            responseCode = "409",
            description = "File already deleted",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorMessage.class),
                  examples = {
                    @ExampleObject(
                        value =
                            "{\"messages\":[\"File already deleted\"],"
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
  @DeleteMapping("/{fileId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteFile(@PathVariable UUID fileId) {
    fileService.deleteFile(fileId);
  }
}
