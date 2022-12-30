package com.soosy.demo.Exceptions;

public class DirectorNotFoundException extends RuntimeException {
    public DirectorNotFoundException(String message){
        super(message);
    }
    public DirectorNotFoundException(String message, Throwable throwable){
        super(message,throwable);
    }
}
