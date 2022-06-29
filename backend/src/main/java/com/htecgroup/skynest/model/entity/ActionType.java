package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ActionType {
  ACTION_CREATE("create"),
  ACTION_VIEW("view"),
  ACTION_DOWNLOAD("download"),
  ACTION_EDIT("edit"),
  ACTION_MOVE("move"),
  ACTION_DELETE("delete"),
  ACTION_RESTORE("restore");
  public final String text;
}
