package com.htecgroup.skynest.util;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DropboxUtil {
  private static DbxWebAuth auth;

  private static DbxRequestConfig requestConfig;

  private static final String redirectUrl = "http://localhost:8080/public/dropbox-auth-finish";

  @PostConstruct
  public static void setAuth() {
    DropboxUtil.requestConfig = new DbxRequestConfig("skynest/0.1");
    DbxAppInfo appInfo = new DbxAppInfo("7y0fr9h4lw3lsgz", "r0gid8mvvzoaq15");
    DropboxUtil.auth = new DbxWebAuth(requestConfig, appInfo);
  }

  public static DbxWebAuth getAuth() {
    return auth;
  }

  public static String getRedirectUrl() {
    return redirectUrl;
  }

  public static DbxRequestConfig getRequestConfig() {
    return requestConfig;
  }
}
