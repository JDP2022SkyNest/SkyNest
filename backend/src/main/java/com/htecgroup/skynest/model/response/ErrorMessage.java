package com.htecgroup.skynest.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorMessage implements Serializable {

  private static final long serialVersionUID = -4622422473461763651L;
  private List<String> messages;
  private int status;
  private String timestamp;

  public ErrorMessage(String message, int status, String timestamp) {
    this.messages = new ArrayList<>();
    this.messages.add(message);
    this.status = status;
    this.timestamp = timestamp;
  }
}
