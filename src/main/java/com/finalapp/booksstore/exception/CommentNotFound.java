package com.finalapp.booksstore.exception;

import org.springframework.core.NestedRuntimeException;

public class CommentNotFound extends NestedRuntimeException {
    public CommentNotFound(String s) {
        super(s);
    }
}
