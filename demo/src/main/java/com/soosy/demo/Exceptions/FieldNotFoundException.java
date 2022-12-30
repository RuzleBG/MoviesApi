package com.soosy.demo.Exceptions;

public class FieldNotFoundException extends RuntimeException{
    public FieldNotFoundException(String message){
        super(message);
    }
    public FieldNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}
