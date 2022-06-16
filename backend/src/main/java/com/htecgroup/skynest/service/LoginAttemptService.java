package com.htecgroup.skynest.service;

public interface LoginAttemptService {
  boolean hasTooManyAttempts(String email);

  void saveUnsuccessfulAttempt(String email);
}
