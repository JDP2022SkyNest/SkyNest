package com.htecgroup.skynest.controller;

import com.dropbox.core.*;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.DropboxUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/public")
public class DropboxController {

  private UserService userService;

  @GetMapping("/dropbox-auth-start")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void authorizeUserDropbox(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    // Select a spot in the session for DbxWebAuth to store the CSRF token.
    HttpSession session = request.getSession(true);
    String sessionKey = "dropbox-auth-csrf-token";
    DbxSessionStore csrfTokenStore = new DbxStandardSessionStore(session, sessionKey);
    // Build an auth request
    DbxWebAuth.Request authRequest =
        DbxWebAuth.newRequestBuilder()
            .withRedirectUri(DropboxUtil.getRedirectUrl(), csrfTokenStore)
            .build();
    // Start authorization.
    String authorizePageUrl = DropboxUtil.getAuth().authorize(authRequest);
    // Redirect the user to the Dropbox website so they can approve our application.
    // The Dropbox website will send them back to "http://my-server.com/dropbox-auth-finish"
    // when they're done.
    response.sendRedirect(authorizePageUrl);
  }

  @GetMapping("/dropbox-auth-finish")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void authorizeUserDropboxFinish(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    // Fetch the session to verify our CSRF token
    HttpSession session = request.getSession(true);
    String sessionKey = "dropbox-auth-csrf-token";
    DbxSessionStore csrfTokenStore = new DbxStandardSessionStore(session, sessionKey);
    String redirectUri = DropboxUtil.getRedirectUrl();
    DbxAuthFinish authFinish;
    try {
      authFinish =
          DropboxUtil.getAuth()
              .finishFromRedirect(redirectUri, csrfTokenStore, request.getParameterMap());
    } catch (DbxWebAuth.BadRequestException ex) {
      log.info("On /dropbox-auth-finish: Bad request: {}", ex.getMessage());
      response.sendError(400);
      return;
    } catch (DbxWebAuth.BadStateException ex) {
      // Send them back to the start of the auth flow.
      response.sendRedirect("http://localhost:8080/public/dropbox-auth-start");
      return;
    } catch (DbxWebAuth.CsrfException ex) {
      log.info("On /dropbox-auth-finish: CSRF mismatch: " + ex.getMessage());
      response.sendError(403, "Forbidden.");
      return;
    } catch (DbxWebAuth.NotApprovedException ex) {
      // When Dropbox asked "Do you want to allow this app to access your
      // Dropbox account?", the user clicked "No".
      return;
    } catch (DbxWebAuth.ProviderException ex) {
      log.info("On /dropbox-auth-finish: Auth failed: " + ex.getMessage());
      response.sendError(503, "Error communicating with Dropbox.");
      return;
    } catch (DbxException ex) {
      log.info("On /dropbox-auth-finish: Error getting token: " + ex.getMessage());
      response.sendError(503, "Error communicating with Dropbox.");
      return;
    }
    String accessToken = authFinish.getAccessToken();
    // log.info("Successfully got dropbox access token for user {}",
    // currentUserService.getLoggedUser().getUuid());
    // Save the access token somewhere (probably in your database) so you
    // don't need to send the user through the authorization process again.
    userService.saveDropboxAccessToken(accessToken);

    response.sendRedirect("http://localhost:3000/");
  }
}
