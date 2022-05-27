package com.htecgroup.SkyNest.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorMessage {
  private String message;
  private int status;
  private String timestamp;
}
