package com.htecgroup.skynest.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FileStatsEmailResponse {
  private String fileName;
  private Integer downloads;
  private Integer edits;
  private Integer views;
  private Integer moves;
  private String size;
}
