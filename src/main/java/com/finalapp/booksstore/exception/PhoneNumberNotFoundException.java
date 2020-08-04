package com.finalapp.booksstore.exception;

import org.springframework.core.NestedRuntimeException;

public class PhoneNumberNotFoundException extends NestedRuntimeException {
    public PhoneNumberNotFoundException(String message) {
        super(message);
    }
}
