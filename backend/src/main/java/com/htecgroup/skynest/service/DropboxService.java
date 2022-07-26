package com.htecgroup.skynest.service;

public interface DropboxService {

  String startAuthorizeUserDropbox();

  void finishAuthorizeUserDropbox(String code);
}
