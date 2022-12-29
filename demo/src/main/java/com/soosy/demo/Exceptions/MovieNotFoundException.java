package com.soosy.demo.Exceptions;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String message){
        super(message);
    }
    public MovieNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}
