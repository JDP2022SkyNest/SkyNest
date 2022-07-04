package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.service.FolderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/folders")
@AllArgsConstructor
@Log4j2
@Tag(name = "Folder API", description = "Folder-related operations")
public class FolderController {

  private FolderService folderService;

  @GetMapping
  public ResponseEntity<FolderResponse> getFolder(@PathVariable UUID uuid) {
    FolderResponse folderResponse = folderService.getFolder(uuid);
    ResponseEntity<FolderResponse> folderResponseEntity =
        new ResponseEntity<>(folderResponse, HttpStatus.OK);
    return folderResponseEntity;
  }
}
