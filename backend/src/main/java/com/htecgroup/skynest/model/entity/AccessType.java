package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AccessType {
  VIEW("view"),
  DOWNLOAD("download"),
  EDIT("edit"),
  OWNER("owner");
  public final String text;
}
