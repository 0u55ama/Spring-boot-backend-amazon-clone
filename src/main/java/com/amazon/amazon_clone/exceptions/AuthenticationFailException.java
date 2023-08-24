package com.amazon.amazon_clone.exceptions;

public class AuthenticationFailException extends IllegalArgumentException{

    public AuthenticationFailException(String msg){
        super(msg);
    }
}
