package com.htecgroup.skynest.exception.tag;

import org.springframework.http.HttpStatus;

public class TagNotFromTheSameCompany extends TagException{

    private static final long serialVersionUID = -6841555932119207650L;

    public static final String MESSAGE = "Tag is not in the same company as selected object";

    public TagNotFromTheSameCompany() {
        super(MESSAGE, HttpStatus.CONFLICT);
    }
}
