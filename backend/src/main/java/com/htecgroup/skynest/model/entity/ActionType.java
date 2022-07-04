package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ActionType {
  CREATE("create"),
  VIEW("view"),
  DOWNLOAD("download"),
  EDIT("edit"),
  MOVE("move"),
  DELETE("delete"),
  RESTORE("restore");
  public final String text;
}
