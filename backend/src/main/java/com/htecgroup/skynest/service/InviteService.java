package com.htecgroup.skynest.service;

import com.htecgroup.skynest.annotation.EmailNotInUse;

public interface InviteService {
  void sendRegistrationInvite(@EmailNotInUse String newUserEmail);
}
