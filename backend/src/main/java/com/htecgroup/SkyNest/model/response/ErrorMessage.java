package com.htecgroup.SkyNest.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {
  private String message;
  private int status;
  private String timestamp;
}
