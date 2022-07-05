package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccessType {
  VIEW("view"),
  DOWNLOAD("download"),
  EDIT("edit"),
  OWNER("owner");
  final String text;
}
