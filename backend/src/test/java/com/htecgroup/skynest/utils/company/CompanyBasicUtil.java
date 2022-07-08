package com.htecgroup.skynest.utils.company;

import com.htecgroup.skynest.model.entity.TierEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class CompanyBasicUtil {
  protected static final UUID id = UUID.fromString("959c005c-4d6b-4dc8-a395-a77ae4e4de5c");
  protected static final LocalDateTime createdOn = null;
  protected static final LocalDateTime modifiedOn = null;
  protected static final LocalDateTime deletedOn = null;
  protected static final String pib = "1234567";
  protected static final String name = "HTEC";
  protected static final String address = "HTECAddress";
  protected static final String phoneNumber = "9212412";
  protected static final String email = "htec@htecgroup.com";
  protected static final String tierName = "basic";
  protected static final TierEntity tierEntity = new TierEntity(UUID.randomUUID(), "basic");
  protected static final String editedName = "HTEC Group";
  protected static final String editedAddress = "Milutina Milankovica 11b";
  protected static final String editedPhoneNumber = "0634545454";
}
