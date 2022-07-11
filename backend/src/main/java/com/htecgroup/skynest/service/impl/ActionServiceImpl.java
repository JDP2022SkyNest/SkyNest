package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.annotation.actions.RecordAction;
import com.htecgroup.skynest.exception.UserNotFoundException;
import com.htecgroup.skynest.exception.action.ActionNotFoundException;
import com.htecgroup.skynest.exception.action.ActionTypeNotFoundException;
import com.htecgroup.skynest.exception.object.ObjectNotFoundException;
import com.htecgroup.skynest.model.entity.ActionEntity;
import com.htecgroup.skynest.model.entity.ActionType;
import com.htecgroup.skynest.model.entity.ActionTypeEntity;
import com.htecgroup.skynest.model.entity.ObjectEntity;
import com.htecgroup.skynest.repository.ActionRepository;
import com.htecgroup.skynest.repository.ActionTypeRepository;
import com.htecgroup.skynest.repository.ObjectRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.ActionService;
import com.htecgroup.skynest.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Aspect
@EnableAspectJAutoProxy
public class ActionServiceImpl implements ActionService {

  private final ActionRepository actionRepository;
  private final ActionTypeRepository actionTypeRepository;
  private final UserRepository userRepository;
  private final CurrentUserService currentUserService;
  private final ObjectRepository objectRepository;

  @Override
  public ActionEntity recordAction(Set<ObjectEntity> objects, ActionType actionType) {

    ActionEntity actionEntity = new ActionEntity();

    actionEntity.setActionType(findActionTypeByName(actionType.text));

    actionEntity.setUser(
        userRepository
            .findById(currentUserService.getLoggedUser().getUuid())
            .orElseThrow(UserNotFoundException::new));

    actionEntity.setRevokedBy(null);

    actionEntity.setObjects(objects);

    ActionEntity savedEntity = actionRepository.save(actionEntity);

    log.info(
        "User {} ({}) has performed action {} on objects {}",
        savedEntity.getUser().getEmail(),
        savedEntity.getUser().getId(),
        savedEntity.getActionType().getName(),
        savedEntity.getObjects().stream().map(ObjectEntity::getId).collect(Collectors.toList()));

    return savedEntity;
  }

  private ActionTypeEntity findActionTypeByName(String name) {
    return actionTypeRepository.findByName(name).orElseThrow(ActionTypeNotFoundException::new);
  }

  @Override
  public ActionEntity recordAction(
      Set<ObjectEntity> objects, ActionType actionType, UUID revokedActionId) {

    ActionEntity revokedAction =
        actionRepository.findById(revokedActionId).orElseThrow(ActionNotFoundException::new);

    ActionEntity savedAction = recordAction(objects, actionType);

    revokedAction.setRevokedBy(savedAction);
    actionRepository.save(revokedAction);

    return savedAction;
  }

  @Override
  public List<ActionEntity> getActionsForUser(UUID userId) {
    return actionRepository.findAllByUserIdOrderByPerformedOnDesc(userId);
  }

  @Override
  public List<ActionEntity> getActionsForCurrentUser() {
    return getActionsForUser(currentUserService.getLoggedUser().getUuid());
  }

  @Override
  public List<ActionEntity> getActionsForObject(UUID objectId) {
    return actionRepository.findAllByObjectIdOrderByPerformedOnDesc(objectId);
  }

  @Override
  public List<ActionEntity> getActionsWithTypeForObject(
      ActionType actionType, UUID objectId, boolean includeRevoked) {

    ActionTypeEntity actionTypeEntity = findActionTypeByName(actionType.text);

    if (includeRevoked)
      return actionRepository.findAllByObjectIdAndTypeOrderByPerformedOnDesc(
          objectId, actionTypeEntity);
    else
      return actionRepository.findAllByObjectIdAndTypeAndNotRevokedOrderByPerformedOnDesc(
          objectId, actionTypeEntity);
  }

  @AfterReturning("@annotation(recordAction)")
  public void recordAction(JoinPoint joinPoint, RecordAction recordAction) {
    UUID objectId = parseAnnotationObjectId(joinPoint, recordAction);
    ActionType actionType = recordAction.actionType();

    ObjectEntity objectEntity =
        objectRepository.findById(objectId).orElseThrow(ObjectNotFoundException::new);
    recordAction(Collections.singleton(objectEntity), actionType);
  }

  private UUID parseAnnotationObjectId(JoinPoint joinPoint, RecordAction recordAction) {
    Object[] args = joinPoint.getArgs();
    ExpressionParser elParser = new SpelExpressionParser();
    Expression expression = elParser.parseExpression(recordAction.objectId());
    return UUID.fromString((String) expression.getValue(args));
  }
}
