package com.htecgroup.skynest.exception.tag;

import org.springframework.http.HttpStatus;

public class TagOnObjectNotFound extends TagException {

    private static final long serialVersionUID = -4284721576854147103L;

    public static final String MESSAGE = "Tag not found for selected object";

    public TagOnObjectNotFound() {
        super(MESSAGE, HttpStatus.CONFLICT);
    }
}
