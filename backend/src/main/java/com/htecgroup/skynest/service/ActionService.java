package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.entity.ActionEntity;
import com.htecgroup.skynest.model.entity.ActionType;
import com.htecgroup.skynest.model.entity.ObjectEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ActionService {
  ActionEntity recordAction(Set<ObjectEntity> objects, ActionType actionType);

  ActionEntity recordAction(Set<ObjectEntity> objects, ActionType actionType, UUID revokedActionId);

  List<ActionEntity> getActionsForUser(UUID userId);

  List<ActionEntity> getActionsForCurrentUser();

  List<ActionEntity> getActionsForObject(UUID objectId);

  List<ActionEntity> getActionsWithTypeForObject(
      ActionType actionType, UUID objectId, boolean includeRevoked);
}
