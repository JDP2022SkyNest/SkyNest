package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.request.PermissionGrantRequest;
import com.htecgroup.skynest.model.response.PermissionResponse;
import com.htecgroup.skynest.service.PermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Permissions API", description = "permissions")
public class PermissionController {

  private final PermissionService permissionService;

  @PostMapping("/grant/bucket")
  public ResponseEntity<PermissionResponse> grantPermissions(
      @Valid @RequestBody PermissionGrantRequest permissionGrantRequest) {

    ResponseEntity<PermissionResponse> response =
        new ResponseEntity<>(
            permissionService.grantPermissionForBucket(permissionGrantRequest), HttpStatus.OK);

    return response;
  }
}
