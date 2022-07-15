package com.htecgroup.skynest.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class TagResponse {

  private UUID id;
  private String name;
  private String rgb;
}
