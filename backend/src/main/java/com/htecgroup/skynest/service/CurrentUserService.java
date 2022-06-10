package com.htecgroup.skynest.service;

import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface CurrentUserService {

  Authentication getAuthentication();

  String getEmail();

  String getRole();

  UUID getId();
}
