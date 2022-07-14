package com.htecgroup.skynest.aspects;

import com.htecgroup.skynest.annotation.actions.RecordAction;
import com.htecgroup.skynest.model.entity.ActionType;
import com.htecgroup.skynest.model.entity.ObjectEntity;
import com.htecgroup.skynest.repository.ObjectRepository;
import com.htecgroup.skynest.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Aspect
@Component
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class ActionsAop {

  private final ObjectRepository objectRepository;
  private final ActionService actionService;

  @AfterReturning("@annotation(recordAction)")
  public void recordAction(JoinPoint joinPoint, RecordAction recordAction) {
    UUID objectId = parseAnnotationObjectId(joinPoint, recordAction);
    ActionType actionType = recordAction.actionType();

    ObjectEntity objectEntity = objectRepository.findById(objectId).get();
    actionService.recordAction(Collections.singleton(objectEntity), actionType);
  }

  private UUID parseAnnotationObjectId(JoinPoint joinPoint, RecordAction recordAction) {
    Object[] args = joinPoint.getArgs();
    ExpressionParser elParser = new SpelExpressionParser();
    Expression expression = elParser.parseExpression(recordAction.objectId());
    return UUID.fromString((String) expression.getValue(args));
  }
}
