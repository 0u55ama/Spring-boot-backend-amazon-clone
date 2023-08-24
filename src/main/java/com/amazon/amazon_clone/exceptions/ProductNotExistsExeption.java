package com.amazon.amazon_clone.exceptions;

public class ProductNotExistsExeption extends IllegalArgumentException {
    public ProductNotExistsExeption(String msg) {
        super(msg);
    }
}
