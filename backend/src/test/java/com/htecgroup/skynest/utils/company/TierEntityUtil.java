package com.htecgroup.skynest.utils.company;

import com.htecgroup.skynest.model.entity.TierEntity;
import com.htecgroup.skynest.model.entity.TierType;
import com.htecgroup.skynest.utils.BasicUtil;

import java.util.UUID;

public class TierEntityUtil extends BasicUtil {

  public static TierEntity getBasicTier() {
    return new TierEntity(
        UUID.fromString("55ff7452-1113-47f3-be82-59c34cb80140"), TierType.BASIC.getText());
  }

  public static TierEntity getGoldTier() {
    return new TierEntity(
        UUID.fromString("11ff7452-1113-47f3-be82-59c34cb80140"), TierType.GOLD.getText());
  }
}
