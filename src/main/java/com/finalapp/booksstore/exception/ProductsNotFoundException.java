package com.finalapp.booksstore.exception;

import org.springframework.core.NestedRuntimeException;

public class ProductsNotFoundException extends NestedRuntimeException {
    public ProductsNotFoundException(String s) {
        super(s);
    }
}
