package com.finalapp.booksstore.exception;

import org.springframework.core.NestedRuntimeException;

public class UsersNotFoundException extends NestedRuntimeException {
    public UsersNotFoundException(String s) {
        super(s);
    }
}
