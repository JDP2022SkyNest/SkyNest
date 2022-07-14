package com.htecgroup.skynest.annotation.actions;

import com.htecgroup.skynest.model.entity.ActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RecordAction {

  public String objectId();

  public ActionType actionType();

}
