package com.finalapp.booksstore.exception;

import org.springframework.core.NestedRuntimeException;

public class OrdersNotFoundException extends NestedRuntimeException {
    public OrdersNotFoundException(String s) {
        super(s);
    }
}
