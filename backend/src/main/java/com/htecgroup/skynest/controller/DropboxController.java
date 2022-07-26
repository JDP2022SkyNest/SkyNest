package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.service.DropboxService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/public")
public class DropboxController {
  private DropboxService dropboxService;

  @GetMapping("/dropbox-auth-start")
  public ResponseEntity<String> startAuthorizeUserDropbox() {
    // The redirect_uri is optional with the code flow - if unspecified, the authorization code is
    // displayed on dropbox.com for the user to copy and paste to your app.
    // This extra step is less convenient for end users, but appropriate for apps that cannot
    // support a redirect.
    String authorizePageUrl = dropboxService.startAuthorizeUserDropbox();
    return ResponseEntity.ok(authorizePageUrl);
  }

  @GetMapping("/dropbox-auth-finish")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void authorizeUserDropboxFinish(@RequestParam String code) {
    dropboxService.finishAuthorizeUserDropbox(code);
  }
}
