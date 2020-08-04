package com.finalapp.booksstore.exception;

import org.springframework.core.NestedRuntimeException;

public class DiscountNotFoundException extends NestedRuntimeException {
    public DiscountNotFoundException(String s) {
        super(s);
    }
}
