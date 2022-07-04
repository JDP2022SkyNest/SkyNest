package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TierType {
  BASIC("basic", 1024L * 1024L * 1024L),
  GOLD("gold", 1024L * 1024L * 1024L * 1024L);
  final String text;
  final long maxSize;
}
