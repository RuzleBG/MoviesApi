package com.soosy.demo;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.soosy.demo.Exceptions.ActorNotFoundException;
import com.soosy.demo.Exceptions.ExceptionResponse;
import com.soosy.demo.Exceptions.MovieNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(ActorNotFoundException.class)
    public ResponseEntity<ExceptionResponse> ActorNotFoundResponse(ActorNotFoundException ex){
        return new ResponseEntity<ExceptionResponse>(
            new ExceptionResponse(
                ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDate.now()), 
                HttpStatus.BAD_GATEWAY);
    }
    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ExceptionResponse> MovieNotFoundResponse(MovieNotFoundException ex){
        return new ResponseEntity<ExceptionResponse>(
            new ExceptionResponse(
                ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDate.now()), 
                HttpStatus.BAD_GATEWAY);
    }
}
