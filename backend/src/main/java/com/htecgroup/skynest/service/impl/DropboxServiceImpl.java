package com.htecgroup.skynest.service.impl;

import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWebAuth;
import com.htecgroup.skynest.service.DropboxService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.DropboxUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class DropboxServiceImpl implements DropboxService {

  private UserService userService;

  @Override
  public String startAuthorizeUserDropbox() {
    DbxWebAuth.Request authRequest = DbxWebAuth.newRequestBuilder().withNoRedirect().build();
    return DropboxUtil.getAuth().authorize(authRequest);
  }

  @Override
  public void finishAuthorizeUserDropbox(String code) {
    DbxAuthFinish authFinish;
    try {
      authFinish = DropboxUtil.getAuth().finishFromCode(code);
    } catch (DbxException ex) {
      log.info("On /dropbox-auth-finish: Error getting token: " + ex.getMessage());
      return;
    }
    String accessToken = authFinish.getAccessToken();
    userService.saveDropboxAccessToken(accessToken);
  }
}
