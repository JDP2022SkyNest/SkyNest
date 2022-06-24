package com.htecgroup.skynest.model.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TierType {
  TIER_BASIC("basic"),
  TIER_GOLD("gold");
  final String name;
}
