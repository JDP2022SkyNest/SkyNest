package com.htecgroup.skynest.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RefreshTokenService {
  void refreshToken(HttpServletRequest request, HttpServletResponse response);
}
