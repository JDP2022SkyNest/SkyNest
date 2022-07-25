package com.htecgroup.skynest.exception.tag;

import org.springframework.http.HttpStatus;

public class TagOnObjectAlreadyExists extends TagException {

    private static final long serialVersionUID = -6841555932119207650L;

    public static final String MESSAGE = "Tag already exists on selected object";

    public TagOnObjectAlreadyExists() {
        super(MESSAGE, HttpStatus.CONFLICT);
    }
}
