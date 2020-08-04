package com.finalapp.booksstore.exception;

import org.springframework.core.NestedRuntimeException;

public class BookNotFoundException extends NestedRuntimeException {
    public BookNotFoundException(String s) {
        super(s);
    }
}
