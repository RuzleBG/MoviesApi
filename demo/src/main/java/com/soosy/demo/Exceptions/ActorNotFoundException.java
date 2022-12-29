package com.soosy.demo.Exceptions;

public class ActorNotFoundException extends RuntimeException{
    public ActorNotFoundException(String message){
        super(message);
    }
    ActorNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}
